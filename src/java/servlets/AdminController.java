/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Book;
import entity.History;
import entity.Reader;
import entity.Role;
import entity.User;
import entity.UserRoles;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.BookFacade;
import session.HistoryFacade;
import session.ReaderFacade;
import session.RoleFacade;
import session.UserFacade;
import session.UserRolesFacade;
import utils.EncryptPass;

/**
 *
 * @author user
 */
@WebServlet(name = "AdminController",loadOnStartup = 1, urlPatterns = {
    
    "/listReaders",
    "/showUserManager",
    "/changeRole",
    
    
})
public class AdminController extends HttpServlet {
@EJB BookFacade bookFacade;
@EJB ReaderFacade readerFacade;
@EJB HistoryFacade historyFacade;
@EJB UserFacade userFacade;
@EJB UserRolesFacade userRolesFacade;
@EJB RoleFacade roleFacade;

  @Override
  public void init() throws ServletException {
    List<User> listUsers = userFacade.findAll();
    if(!listUsers.isEmpty()) return;
    Reader reader = new Reader("Juri", "Melnikov", "juri.melnikov@ivkhk.ee");
    readerFacade.create(reader);
    EncryptPass ep = new EncryptPass();
    String password = "123123";
    String salts = ep.getSalts();
    password = ep.getEncryptPass(password, salts);
    User user = new User("admin", password, salts, reader);
    userFacade.create(user);
    UserRoles userRoles = new UserRoles();
    userRoles.setUser(user);
    Role role = new Role();
    role.setRoleName("ADMIN");
    roleFacade.create(role);
    userRoles.setRole(role);
    userRolesFacade.create(userRoles);
    role.setRoleName("MANAGER");
    roleFacade.create(role);
    userRoles.setRole(role);
    userRolesFacade.create(userRoles);
    role.setRoleName("USER");
    roleFacade.create(role);
    userRoles.setRole(role);
    userRolesFacade.create(userRoles);
    
  }
  



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
        //Защита ресурсов
        HttpSession session = request.getSession(false);
        if(session == null){
            request.setAttribute("info", "У вас нет прав, войдите");
            request.getRequestDispatcher("/WEB-INF/showLogin.jsp")
                        .forward(request, response);
            return;    
        }
        User user = (User) session.getAttribute("user");
        if(user == null){
            request.setAttribute("info", "У вас нет прав, войдите");
            request.getRequestDispatcher("/WEB-INF/showLogin.jsp")
                        .forward(request, response);
            return;   
        }
        boolean isRole = userRolesFacade.isRole("ADMIN",user);
        if(!isRole){
            request.setAttribute("info", "У вас нет прав, войдите");
            request.getRequestDispatcher("/WEB-INF/showLogin.jsp")
                        .forward(request, response);
            return;   
        }
        String path = request.getServletPath();
        switch (path) {
            case "/listReaders":    
                List<Reader> listReaders = readerFacade.findAll();
                request.setAttribute("listReaders", listReaders);
                request.getRequestDispatcher("/listReaders.jsp")
                        .forward(request, response);
                break;
            case "/showUserManager":
                List<User> listUsers = userFacade.findWithoutAdmin();
                Map<User,String> mapUsers = new HashMap<>();
                for(User u : listUsers){
                    mapUsers.put(u, userRolesFacade.getTopRole(u));
                }
                List<Role> listRoles = roleFacade.findAll();
                request.setAttribute("mapUsers", mapUsers);
                request.setAttribute("listRoles", listRoles);
                request.getRequestDispatcher("/WEB-INF/showUserManager.jsp")
                        .forward(request, response);
                break;
            case "/changeRole":
                String userId = request.getParameter("userId");
                String roleId = request.getParameter("roleId");
                User userChangeRole = userFacade.find(Long.parseLong(userId));
                Role newRole = roleFacade.find(Long.parseLong(roleId));
                List<UserRoles> listUserRoles = userRolesFacade.findByUser(userChangeRole);
                for(UserRoles ur : listUserRoles){
                    userRolesFacade.remove(ur);
                }
                UserRoles userRoles = new UserRoles();
                userRoles.setUser(userChangeRole);
                Role role = null;
                if("ADMIN".equals(newRole.getRoleName())){
                    role = roleFacade.findByRoleName("ADMIN");
                    userRoles.setRole(role);
                    userRolesFacade.create(userRoles);
                    role = roleFacade.findByRoleName("MANAGER");
                    userRoles.setRole(role);
                    userRolesFacade.create(userRoles);
                    role = roleFacade.findByRoleName("USER");
                    userRoles.setRole(role);
                    userRolesFacade.create(userRoles);
                }else if("MANAGER".equals(newRole.getRoleName())){
                    role = roleFacade.findByRoleName("MANAGER");
                    userRoles.setRole(role);
                    userRolesFacade.create(userRoles);
                    role = roleFacade.findByRoleName("USER");
                    userRoles.setRole(role);
                    userRolesFacade.create(userRoles);
                }else if("USER".equals(newRole.getRoleName())){
                    role = roleFacade.findByRoleName("USER");
                    userRoles.setRole(role);
                    userRolesFacade.create(userRoles);
                }
                request.getRequestDispatcher("/showUserManager")
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

