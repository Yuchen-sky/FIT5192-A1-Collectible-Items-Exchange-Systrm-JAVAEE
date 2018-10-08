/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository;

import fit5192.stu29184517.repository.entities.CommodityType;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.EntityManager;

/**
 *
 * @author luzhe
 */
@Remote
public interface CommodityTypeControl extends Serializable {

    void create(CommodityType commodityType) throws  Exception;

    void destroy(Integer id) throws  Exception;

    void edit(CommodityType commodityType) throws  Exception;

    CommodityType findCommodityType(Integer id);

    List<CommodityType> findCommodityTypeEntities();

    List<CommodityType> findCommodityTypeEntities(int maxResults, int firstResult);

    int getCommodityTypeCount();

    EntityManager getEntityManager();
    
}
