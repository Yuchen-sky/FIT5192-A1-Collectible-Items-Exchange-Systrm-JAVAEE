/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository;

import fit5192.stu29184517.repository.entities.SaleOrder;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.EntityManager;

/**
 *
 * @author luzhe
 */
@Remote
public interface SaleOrderControl extends Serializable {

    void create(SaleOrder saleOrder) throws  Exception;

    void destroy(Integer id) throws  Exception;

    void edit(SaleOrder saleOrder) throws  Exception;

    SaleOrder findSaleOrder(Integer id);

    List<SaleOrder> findSaleOrderEntities();

    List<SaleOrder> findSaleOrderEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getSaleOrderCount();
    
     List<SaleOrder> findSaleOrderByUser(Integer id) ;
    
}
