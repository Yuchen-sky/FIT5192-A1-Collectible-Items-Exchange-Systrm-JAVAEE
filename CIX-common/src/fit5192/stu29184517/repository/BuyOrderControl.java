/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository;

import fit5192.stu29184517.repository.entities.BuyOrder;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.EntityManager;

/**
 *
 * @author luzhe
 */
@Remote
public interface BuyOrderControl extends Serializable {

    void create(BuyOrder buyOrder) throws  Exception;

    void destroy(Integer id) throws  Exception;

    void edit(BuyOrder buyOrder) throws  Exception;

    BuyOrder findBuyOrder(Integer id);

    List<BuyOrder> findBuyOrderEntities();

    List<BuyOrder> findBuyOrderEntities(int maxResults, int firstResult);

    int getBuyOrderCount();

    EntityManager getEntityManager();
    
     List<BuyOrder> findBuyOrderByUser(Integer id);
    
}
