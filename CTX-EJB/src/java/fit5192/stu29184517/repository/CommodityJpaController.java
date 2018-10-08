/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import fit5192.stu29184517.repository.entities.CommodityType;
import java.util.ArrayList;
import java.util.Collection;
import fit5192.stu29184517.repository.entities.SaleOrder;
import fit5192.stu29184517.repository.entities.BuyOrder;
import fit5192.stu29184517.repository.entities.Commodity;
import fit5192.stu29184517.repository.entities.Exchange;
import fit5192.stu29184517.repository.exceptions.NonexistentEntityException;
import fit5192.stu29184517.repository.exceptions.PreexistingEntityException;
import fit5192.stu29184517.repository.exceptions.RollbackFailureException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author luzhe
 */

@Stateless
public class CommodityJpaController implements CommodityControl {
   
    private EntityManagerFactory emf = null;
    private static final String PERSISTENCE_UNIT = "CTX-EJBPUv4";
    public CommodityJpaController() {
  this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Commodity commodity) throws PreexistingEntityException, RollbackFailureException, Exception {
        
        EntityManager em=getEntityManager();
          //EntityTransaction utx = em.getTransaction();
        try {
           // utx.begin();
          

            em.persist(commodity);
 
           // utx.commit();
        } catch (Exception ex) {
            try {
            //    utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCommodity(commodity.getCommodityId()) != null) {
                throw new PreexistingEntityException("Commodity " + commodity + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Commodity commodity) throws NonexistentEntityException, RollbackFailureException, Exception {
         EntityManager em=getEntityManager();
         // EntityTransaction utx = em.getTransaction();
        try {
          //  utx.begin();
            em = getEntityManager();

            commodity = em.merge(commodity);
           
          //  utx.commit();
        } catch (Exception ex) {
            try {
          //      utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = commodity.getCommodityId();
                if (findCommodity(id) == null) {
                    throw new NonexistentEntityException("The commodity with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
         EntityManager em=getEntityManager();
          EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            em = getEntityManager();
            Commodity commodity;
            try {
                commodity = em.getReference(Commodity.class, id);
                commodity.getCommodityId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The commodity with id " + id + " no longer exists.", enfe);
            }
            Collection<CommodityType> commodityTypeCollection = commodity.getCommodityTypeCollection();
            for (CommodityType commodityTypeCollectionCommodityType : commodityTypeCollection) {
                commodityTypeCollectionCommodityType.setCommodityId(null);
                commodityTypeCollectionCommodityType = em.merge(commodityTypeCollectionCommodityType);
            }
            Collection<SaleOrder> saleOrderCollection = commodity.getSaleOrderCollection();
            for (SaleOrder saleOrderCollectionSaleOrder : saleOrderCollection) {
                saleOrderCollectionSaleOrder.setCommodityId(null);
                saleOrderCollectionSaleOrder = em.merge(saleOrderCollectionSaleOrder);
            }
            Collection<BuyOrder> buyOrderCollection = commodity.getBuyOrderCollection();
            for (BuyOrder buyOrderCollectionBuyOrder : buyOrderCollection) {
                buyOrderCollectionBuyOrder.setCommodityId(null);
                buyOrderCollectionBuyOrder = em.merge(buyOrderCollectionBuyOrder);
            }
            Collection<Exchange> exchangeCollection = commodity.getExchangeCollection();
            for (Exchange exchangeCollectionExchange : exchangeCollection) {
                exchangeCollectionExchange.setCommodityId(null);
                exchangeCollectionExchange = em.merge(exchangeCollectionExchange);
            }
            em.remove(commodity);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Commodity> findCommodityEntities() {
        return findCommodityEntities(true, -1, -1);
    }

    @Override
    public List<Commodity> findCommodityEntities(int maxResults, int firstResult) {
        return findCommodityEntities(false, maxResults, firstResult);
    }

    private List<Commodity> findCommodityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Commodity.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Commodity findCommodity(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Commodity.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Commodity> findCommodityTitle(String title) {
        EntityManager em = getEntityManager();
              
        try {
           Query query = em.createNamedQuery("Commodity.findByTitle");
            query.setParameter("title", title);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    
     @Override
    public List<Commodity> findCommodityType(String typename) {
        EntityManager em = getEntityManager();
              
        try {
           TypedQuery<Commodity> q = em.createQuery("SELECT c FROM  Commodity c,CommodityType b, Types d WHERE b.commodityId = c AND b.typeId =d AND d.typeName=:typename", Commodity.class);
            q.setParameter("typename", typename);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    
    @Override
    public int getCommodityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Commodity> rt = cq.from(Commodity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
