/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Book;
import entity.Reader;
import entity.Role;
import entity.User;
import entity.UserRoles;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.BookFacade;
import session.ReaderFacade;
import session.UserFacade;
import session.UserRolesFacade;
import utils.EncryptPass;

/**
 *
 * @author user
 */
@WebServlet(name = "LoginController", urlPatterns = {
    "/showLogin",
    "/login",
    "/logout",
    "/newReader",
    "/addReader",
    "/listBooks",
})
public class LoginController extends HttpServlet {
@EJB ReaderFacade readerFacade;
@EJB UserFacade userFacade;
@EJB BookFacade bookFacade;
@EJB UserRolesFacade userRolesFacade;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        switch (path) {
            case "/showLogin":
                request.getRequestDispatcher("/WEB-INF/showLogin.jsp")
                        .forward(request, response);
                break;
            case "/login":
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                if(login == null || password == null){
                    request.setAttribute("info", "Неправильный логин или пароль!");
                    request.getRequestDispatcher("/WEB-INF/showLogin.jsp")
                        .forward(request, response);
                    break;
                }
                User user = userFacade.findByLogin(login);
                if(user == null){
                    request.setAttribute("info", "Неправильный логин или пароль!");
                    request.getRequestDispatcher("/WEB-INF/showLogin.jsp")
                        .forward(request, response);
                    break;
                }
                EncryptPass encryptPass = new EncryptPass();
                password = encryptPass.getEncryptPass(password, user.getSalts());
                if(!password.equals(user.getPassword())){
                   request.setAttribute("info", "Неправильный логин или пароль!");
                   request.getRequestDispatcher("/WEB-INF/showLogin.jsp")
                        .forward(request, response);
                   break; 
                }
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                request.setAttribute("info", "Привет, "+login+"!");
                request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
                break;
            case "/logout":
                session = request.getSession(false);
                if(session != null){
                    session.invalidate();
                }
                request.setAttribute("info", "Вы вышли");
                request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
                break;
            case "/newReader":
                request.getRequestDispatcher("/WEB-INF/newReader.jsp")
                        .forward(request, response);
                break;
            case "/addReader":
                String name = request.getParameter("name");
                String lastname = request.getParameter("lastname");
                String email = request.getParameter("email");
                login = request.getParameter("login");
                password = request.getParameter("password");
                if(name == null || lastname == null
                        || email == null || login == null
                        || password == null){
                    request.setAttribute("info", "Заполните все поля!");
                    request.setAttribute("lastname",lastname);
                    request.setAttribute("email",email);
                    request.setAttribute("login",login);
                    request.getRequestDispatcher("/newReader")
                        .forward(request, response);
                }
                Reader reader = null;
                user = null;
                try {
                    reader = new Reader(name, lastname, email);
                    readerFacade.create(reader);
                    encryptPass = new EncryptPass();
                    String salts = encryptPass.getSalts();
                    password = encryptPass.getEncryptPass(password,salts);
                    user = new User(login, password, salts, reader);
                    userFacade.create(user);
                    UserRoles userRoles = new UserRoles();
                    userRoles.setUser(user);
                    Role roleUser = userRolesFacade.findByRoleName("USER");
                    userRoles.setRole(roleUser);
                    userRolesFacade.create(userRoles);
                } catch (Exception e) {
                    if(reader != null){
                        readerFacade.remove(reader);
                    }
                    if(user != null){
                        userFacade.remove(user);
                    }
                    request.setAttribute("info", "Пользователя создать не удалось");
                    request.getRequestDispatcher("/newReader")
                        .forward(request, response);
                }
                
                request.setAttribute("info", "Пользователь создан");
                request.getRequestDispatcher("/index.jsp")
                        .forward(request, response);
                break;
            case "/listBooks":
                List<Book> listBooks = bookFacade.findAll();
                request.setAttribute("listBooks", listBooks);
                request.getRequestDispatcher("/listBooks.jsp")
                        .forward(request, response);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
