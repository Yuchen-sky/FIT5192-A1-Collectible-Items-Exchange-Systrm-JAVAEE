/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository;

import fit5192.stu29184517.repository.entities.BuyOrder;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import fit5192.stu29184517.repository.entities.Commodity;
import fit5192.stu29184517.repository.entities.SaleOrder;
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
public class SaleOrderJpaController implements SaleOrderControl {
    private static final String PERSISTENCE_UNIT = "CTX-EJBPUv4";
    public SaleOrderJpaController() {
       this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }
   
    private EntityManagerFactory emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(SaleOrder saleOrder) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em=getEntityManager();
        //  EntityTransaction utx = em.getTransaction();
        try {
          //  utx.begin();
            em = getEntityManager();
           
            em.persist(saleOrder);
         
          //  utx.commit();
        } catch (Exception ex) {
            try {
         //       utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSaleOrder(saleOrder.getSaleOrderId()) != null) {
                throw new PreexistingEntityException("SaleOrder " + saleOrder + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(SaleOrder saleOrder) throws NonexistentEntityException, RollbackFailureException, Exception {
       EntityManager em=getEntityManager();
        //  EntityTransaction utx = em.getTransaction();
        try {
           // utx.begin();
            em = getEntityManager();
           
            saleOrder = em.merge(saleOrder);
           
           // utx.commit();
        } catch (Exception ex) {
            try {
            //    utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = saleOrder.getSaleOrderId();
                if (findSaleOrder(id) == null) {
                    throw new NonexistentEntityException("The saleOrder with id " + id + " no longer exists.");
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
           // utx.begin();
            em = getEntityManager();
            SaleOrder saleOrder;
            try {
                saleOrder = em.getReference(SaleOrder.class, id);
                saleOrder.getSaleOrderId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saleOrder with id " + id + " no longer exists.", enfe);
            }
            
            em.remove(saleOrder);
          //  utx.commit();
        } catch (Exception ex) {
            try {
            //    utx.rollback();
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
    public List<SaleOrder> findSaleOrderEntities() {
        return findSaleOrderEntities(true, -1, -1);
    }

    @Override
    public List<SaleOrder> findSaleOrderEntities(int maxResults, int firstResult) {
        return findSaleOrderEntities(false, maxResults, firstResult);
    }

    private List<SaleOrder> findSaleOrderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SaleOrder.class));
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
    public SaleOrder findSaleOrder(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SaleOrder.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getSaleOrderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SaleOrder> rt = cq.from(SaleOrder.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    
     @Override
    public List<SaleOrder> findSaleOrderByUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
          // TypedQuery<BuyOrder> ("SELECT l FROM Location l, Student s WHERE l.studentid = s AND s.id = :studentid ORDER BY l.locationid DESC",
           // TypedQuery<BuyOrder> q = em.createQuery("SELECT b FROM BuyOrder b, Commodity c WHERE b.commodity_id = c AND c.commodity_id = :id ORDER BY b.per_price  DESC", BuyOrder.class);
            TypedQuery<SaleOrder> q = em.createQuery("SELECT b FROM SaleOrder b , Commodity c WHERE  b.commodityId = c AND c.commodityId = :id ORDER BY b.perPrice ", SaleOrder.class);
            q.setParameter("id", id);
            return q.getResultList();
        } finally {
            em.close();
        }
    }    
    
}
