/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository;

import fit5192.stu29184517.repository.entities.BuyOrder;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import fit5192.stu29184517.repository.entities.Commodity;
import fit5192.stu29184517.repository.entities.Users;
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
public class BuyOrderJpaController implements BuyOrderControl {
    private static final String PERSISTENCE_UNIT = "CTX-EJBPUv4";
    public BuyOrderJpaController() {
     this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
     
    }
   
    private EntityManagerFactory emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(BuyOrder buyOrder) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em=getEntityManager();
         // EntityTransaction utx = em.getTransaction();
        try {
           // utx.begin();
            em = getEntityManager();
            
            em.persist(buyOrder);
            
        //    utx.commit();
        } catch (Exception ex) {
            try {
          //      utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findBuyOrder(buyOrder.getBuyOrderId()) != null) {
                throw new PreexistingEntityException("BuyOrder " + buyOrder + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(BuyOrder buyOrder) throws NonexistentEntityException, RollbackFailureException, Exception {
         EntityManager em=getEntityManager();
         // EntityTransaction utx = em.getTransaction();
        try {
           // utx.begin();
            em = getEntityManager();
           
            buyOrder = em.merge(buyOrder);
            
         //   utx.commit();
        } catch (Exception ex) {
            try {
           //     utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = buyOrder.getBuyOrderId();
                if (findBuyOrder(id) == null) {
                    throw new NonexistentEntityException("The buyOrder with id " + id + " no longer exists.");
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
         // EntityTransaction utx = em.getTransaction();
        try {
          //  utx.begin();
            em = getEntityManager();
            BuyOrder buyOrder;
            try {
                buyOrder = em.getReference(BuyOrder.class, id);
                buyOrder.getBuyOrderId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The buyOrder with id " + id + " no longer exists.", enfe);
            }
          
            em.remove(buyOrder);
          //  utx.commit();
        } catch (Exception ex) {
            try {
           //     utx.rollback();
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
    public List<BuyOrder> findBuyOrderEntities() {
        return findBuyOrderEntities(true, -1, -1);
    }

    @Override
    public List<BuyOrder> findBuyOrderEntities(int maxResults, int firstResult) {
        return findBuyOrderEntities(false, maxResults, firstResult);
    }

    private List<BuyOrder> findBuyOrderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BuyOrder.class));
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

  //   Query q = em.createQuery("select s.favouriteunit, count(s.id) as stunum from Student s group by s.favouriteunit");
   //  SELECT * FROM FIT5192.BUY_ORDER where FIT5192.BUY_ORDER.COMMODITY_ID=1 ORDER BY FIT5192.BUY_ORDER.per_price  desc;
    @Override
    public List<BuyOrder> findBuyOrderByUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
          // TypedQuery<BuyOrder> ("SELECT l FROM Location l, Student s WHERE l.studentid = s AND s.id = :studentid ORDER BY l.locationid DESC",
           // TypedQuery<BuyOrder> q = em.createQuery("SELECT b FROM BuyOrder b, Commodity c WHERE b.commodity_id = c AND c.commodity_id = :id ORDER BY b.per_price  DESC", BuyOrder.class);
            TypedQuery<BuyOrder> q = em.createQuery("SELECT b FROM BuyOrder b , Commodity c WHERE  b.commodityId = c AND c.commodityId = :id ORDER BY b.perPrice  DESC", BuyOrder.class);
            q.setParameter("id", id);
            return q.getResultList();
        } finally {
            em.close();
        }
    }    
        
    
    @Override
    public BuyOrder findBuyOrder(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BuyOrder.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getBuyOrderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BuyOrder> rt = cq.from(BuyOrder.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
