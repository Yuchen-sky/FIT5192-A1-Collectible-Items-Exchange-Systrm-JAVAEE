/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository;

import fit5192.stu29184517.repository.entities.Types;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.EntityManager;

/**
 *
 * @author luzhe
 */
@Remote
public interface TypesControl extends Serializable {

    void create(Types types) throws  Exception;

    void destroy(Integer id) throws  Exception;

    void edit(Types types) throws  Exception;

    Types findTypes(Integer id) ;

    List<Types> findTypesEntities();

    List<Types> findTypesEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();
    
    
    List<Types> findTypeByItem(Integer id);

    int getTypesCount();
    
}
