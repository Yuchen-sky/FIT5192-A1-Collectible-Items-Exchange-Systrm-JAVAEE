/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository;

import fit5192.stu29184517.repository.entities.Commodity;
import java.io.Serializable;

import java.util.List;
import javax.ejb.Remote;
import javax.persistence.EntityManager;

/**
 *
 * @author luzhe
 */
@Remote
public interface CommodityControl  extends Serializable  {

    void create(Commodity commodity) throws  Exception;

    void destroy(Integer id) throws  Exception;

    void edit(Commodity commodity) throws  Exception;

    Commodity findCommodity(Integer id);

    List<Commodity> findCommodityEntities();

    List<Commodity> findCommodityEntities(int maxResults, int firstResult);

    List<Commodity> findCommodityTitle(String title);
    
     List<Commodity> findCommodityType(String typename);

    int getCommodityCount();

    EntityManager getEntityManager();
    
}
