/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository;

import fit5192.stu29184517.repository.entities.Exchange;
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
public interface ExchangeControl extends Serializable {

    void create(Exchange exchange) throws  Exception;

    void destroy(Integer id) throws  Exception;

    void edit(Exchange exchange) throws  Exception;

    Exchange findExchange(Integer id);

    List<Exchange> findExchangeEntities();

    List<Exchange> findExchangeEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getExchangeCount();
    
   List<Exchange> findCommodityType(int exid,String title,Users ex);
}
