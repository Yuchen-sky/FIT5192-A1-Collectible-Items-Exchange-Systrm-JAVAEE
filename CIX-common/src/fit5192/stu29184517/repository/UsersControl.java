/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository;

import fit5192.stu29184517.repository.entities.Users;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.EntityManager;

/**
 *
 * @author luzhe
 */
@Remote
public interface UsersControl extends Serializable {

    void create(Users users) throws  Exception;

    void destroy(Integer id) throws  Exception;

    void edit(Users users) throws  Exception;

    Users findUsers(Integer id);

    List<Users> findUsersEntities();

    List<Users> findUsersEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();
    
    Users findUsersByEmail(String email, String password);

    int getUsersCount();
    
    List<Users> findUsersEmail(String first);
    
     List<Users> findUsersLast(String first);
     
     List<Users> findUsersPhone(Integer phone);
     
     List<Users> findUsersFirst(String first);
     
     List<Users> multifind(int id,String firstname,String lastname,int phone,String email);
    
}
