/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.User;
import entity.UserRoles;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jvm
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

  public boolean isRole(String roleName, User user) {
    List<UserRoles> listUserRoles = em.createQuery("SELECT ur FROM UserRoles ur WHERE ur.user=:user")
            .setParameter("user", user)
            .getResultList();
    for(UserRoles userRole : listUserRoles){
      if(roleName.equals(userRole.getRole().getRoleName())){
        return true;
      }
    }
    return false;

  }

    public List<UserRoles> findByUser(User userChangeRole) {
        try {
            return em.createQuery("SELECT ur FROM UserRoles ur WHERE ur.user = :user")
                    .setParameter("user", userChangeRole)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public String getTopRole(User u) {
        List<UserRoles> listUserRoles = this.findByUser(u);
        for(UserRoles ur : listUserRoles){
            if("ADMIN".equals(ur.getRole().getRoleName())){
                return "ADMIN";
            }
        }
        for(UserRoles ur : listUserRoles){
            if("MANAGER".equals(ur.getRole().getRoleName())){
                return "MANAGER";
            }
        }
        for(UserRoles ur : listUserRoles){
            if("USER".equals(ur.getRole().getRoleName())){
                return "USER";
            }
        }
        return "NoRole";
    }
  
}
