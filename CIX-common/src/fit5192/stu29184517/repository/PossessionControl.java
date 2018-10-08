/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository;

import fit5192.stu29184517.repository.entities.Possession;
import fit5192.stu29184517.repository.exceptions.NonexistentEntityException;
import fit5192.stu29184517.repository.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.EntityManager;

/**
 *
 * @author luzhe
 */
@Remote
public interface PossessionControl extends Serializable {

    void create(Possession possession) throws PreexistingEntityException, Exception;

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Possession possession) throws NonexistentEntityException, Exception;

    Possession findPossession(Integer id);

    List<Possession> findPossessionEntities();

    List<Possession> findPossessionEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getPossessionCount();
    
}
