/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entity.User;
import entity.UserRoles;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import session.UserRolesFacade;

/**
 *
 * @author Irina
 */
public class RoleManager {
    UserRolesFacade userRolesFacade;

    public RoleManager() {
         Context context;
        try {
            context = new InitialContext();
            this.userRolesFacade = (UserRolesFacade) context.lookup("java:module/UserRolesFacade");
        } catch (NamingException ex) {
            Logger.getLogger(RoleManager.class.getName()).log(Level.SEVERE, "Не удалось найти Бин", ex);
        }
    }
    
    
    public boolean isRole(String roleName, User user){
       return userRolesFacade.isRole(roleName,user);
    }
}
