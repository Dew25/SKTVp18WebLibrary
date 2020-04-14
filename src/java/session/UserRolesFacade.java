/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Role;
import entity.User;
import entity.UserRoles;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Irina
 */
@Stateless
public class UserRolesFacade extends AbstractFacade<UserRoles> {

    @PersistenceContext(unitName = "SKTVp18WebLibraryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserRolesFacade() {
        super(UserRoles.class);
    }

    public Role findByRoleName(String roleName) {
        try {
            UserRoles userRoles = (UserRoles) em.createQuery("SELECT ur FROM UserRoles ur WHERE ur.role.roleName = :roleName")
                    .setParameter("roleName", roleName)
                    .getSingleResult();
            return userRoles.getRole();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isRole(String roleName, User user) {
        List<UserRoles> listUserRoles = em.createQuery("SELECT ur FROM UserRoles ur WHERE ur.user = :user")
                .setParameter("user", user)
                .getResultList();
        List<Role> listRoles = null;
        for (UserRoles userRole : listUserRoles) {
            if(roleName.equals(userRole.getRole().getRoleName())){
                return true;
            }
        }
        return false;
    }
    
}
