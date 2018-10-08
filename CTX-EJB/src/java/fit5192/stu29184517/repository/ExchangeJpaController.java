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
import fit5192.stu29184517.repository.entities.Commodity;
import fit5192.stu29184517.repository.entities.Exchange;
import fit5192.stu29184517.repository.entities.Users;
import fit5192.stu29184517.repository.exceptions.NonexistentEntityException;
import fit5192.stu29184517.repository.exceptions.PreexistingEntityException;
import fit5192.stu29184517.repository.exceptions.RollbackFailureException;
import java.util.ArrayList;
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
public class ExchangeJpaController implements ExchangeControl {
    private static final String PERSISTENCE_UNIT = "CTX-EJBPUv4";
    public ExchangeJpaController() {
      this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }
  
    private EntityManagerFactory emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Exchange exchange) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em=getEntityManager();
        //  EntityTransaction utx = em.getTransaction();
        try {
          //  utx.begin();
            em = getEntityManager();
        
            em.persist(exchange);
           
         //   utx.commit();
        } catch (Exception ex) {
            try {
          //      utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findExchange(exchange.getExchangeId()) != null) {
                throw new PreexistingEntityException("Exchange " + exchange + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Exchange exchange) throws NonexistentEntityException, RollbackFailureException, Exception {
         EntityManager em=getEntityManager();
          EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            em = getEntityManager();
            Exchange persistentExchange = em.find(Exchange.class, exchange.getExchangeId());
            Commodity commodityIdOld = persistentExchange.getCommodityId();
            Commodity commodityIdNew = exchange.getCommodityId();
            Users executeUserOld = persistentExchange.getExecuteUser();
            Users executeUserNew = exchange.getExecuteUser();
            if (commodityIdNew != null) {
                commodityIdNew = em.getReference(commodityIdNew.getClass(), commodityIdNew.getCommodityId());
                exchange.setCommodityId(commodityIdNew);
            }
            if (executeUserNew != null) {
                executeUserNew = em.getReference(executeUserNew.getClass(), executeUserNew.getUserId());
                exchange.setExecuteUser(executeUserNew);
            }
            exchange = em.merge(exchange);
            if (commodityIdOld != null && !commodityIdOld.equals(commodityIdNew)) {
                commodityIdOld.getExchangeCollection().remove(exchange);
                commodityIdOld = em.merge(commodityIdOld);
            }
            if (commodityIdNew != null && !commodityIdNew.equals(commodityIdOld)) {
                commodityIdNew.getExchangeCollection().add(exchange);
                commodityIdNew = em.merge(commodityIdNew);
            }
            if (executeUserOld != null && !executeUserOld.equals(executeUserNew)) {
                executeUserOld.getExchangeCollection().remove(exchange);
                executeUserOld = em.merge(executeUserOld);
            }
            if (executeUserNew != null && !executeUserNew.equals(executeUserOld)) {
                executeUserNew.getExchangeCollection().add(exchange);
                executeUserNew = em.merge(executeUserNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = exchange.getExchangeId();
                if (findExchange(id) == null) {
                    throw new NonexistentEntityException("The exchange with id " + id + " no longer exists.");
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
            Exchange exchange;
            try {
                exchange = em.getReference(Exchange.class, id);
                exchange.getExchangeId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The exchange with id " + id + " no longer exists.", enfe);
            }
            Commodity commodityId = exchange.getCommodityId();
            if (commodityId != null) {
                commodityId.getExchangeCollection().remove(exchange);
                commodityId = em.merge(commodityId);
            }
            Users executeUser = exchange.getExecuteUser();
            if (executeUser != null) {
                executeUser.getExchangeCollection().remove(exchange);
                executeUser = em.merge(executeUser);
            }
            em.remove(exchange);
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
    public List<Exchange> findExchangeEntities() {
        return findExchangeEntities(true, -1, -1);
    }

    @Override
    public List<Exchange> findExchangeEntities(int maxResults, int firstResult) {
        return findExchangeEntities(false, maxResults, firstResult);
    }

    private List<Exchange> findExchangeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Exchange.class));
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
    public Exchange findExchange(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Exchange.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getExchangeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Exchange> rt = cq.from(Exchange.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    @Override
    public List<Exchange> findCommodityType(int exid,String title,Users ex) {
        EntityManager em = getEntityManager();
              
        try {
          //  String[] s = ae.split("  ");
          // String featrues[] = {"exchangeId","commodityId","executeUserId"};
          //  String front = "SELECT c FROM  Exchange c";
          //  String where=" Where ";
          //  String and=" AND ";
           List<Exchange> list=new ArrayList();
           if(title.length()>0){         
           if(exid==0){
               if(ex!=null){
           TypedQuery<Exchange> q = em.createQuery("SELECT c FROM  Exchange c,Commodity b WHERE c.commodityId = b AND b.title =:title AND c.executeUser=:ex", Exchange.class);
            q.setParameter("ex", ex);
             q.setParameter("title", title);
            return q.getResultList();
               }
             TypedQuery<Exchange> q = em.createQuery("SELECT c FROM  Exchange c,Commodity b WHERE c.commodityId = b AND b.title =:title ", Exchange.class);           
             q.setParameter("title", title);
             return q.getResultList(); 
           }        
          if(ex==null){
          
          TypedQuery<Exchange> q = em.createQuery("SELECT c FROM  Exchange c,Commodity b WHERE c.commodityId = b AND b.title =:title AND c.exchangeId=:exid", Exchange.class);
            q.setParameter("exid", exid);
             q.setParameter("title", title);
            return q.getResultList();
          }
          TypedQuery<Exchange> q = em.createQuery("SELECT c FROM  Exchange c,Commodity b WHERE c.commodityId = b AND b.title =:title AND c.executeUser=:ex  AND c.exchangeId=:exid ", Exchange.class);
            q.setParameter("ex", ex);
             q.setParameter("title", title);
             q.setParameter("exid", exid);
            return q.getResultList();
           } 
            if(exid==0){
               if(ex!=null){
           TypedQuery<Exchange> q = em.createQuery("SELECT c FROM  Exchange c WHERE  c.executeUser=:ex", Exchange.class);
            q.setParameter("ex", ex);
             
            return q.getResultList();
               }
             TypedQuery<Exchange> q = em.createQuery("SELECT c FROM  Exchange c", Exchange.class);           
            
             return q.getResultList(); 
           }        
         if(ex==null){
          
          TypedQuery<Exchange> q = em.createQuery("SELECT c FROM  Exchange c WHERE c.exchangeId=:exid", Exchange.class);
            q.setParameter("exid", exid);
                       
            return q.getResultList();
          }
          TypedQuery<Exchange> q = em.createQuery("SELECT c FROM  Exchange c WHERE  c.exchangeId=:exid AND c.executeUser=:ex", Exchange.class);
            q.setParameter("ex", ex);
 //asfsd
             q.setParameter("exid", exid);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    private boolean ignoreCaseEquals(String string, String featrue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
