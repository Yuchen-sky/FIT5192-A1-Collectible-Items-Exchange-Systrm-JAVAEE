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
import fit5192.stu29184517.repository.entities.CommodityType;
import fit5192.stu29184517.repository.entities.Types;
import fit5192.stu29184517.repository.exceptions.NonexistentEntityException;
import fit5192.stu29184517.repository.exceptions.PreexistingEntityException;
import fit5192.stu29184517.repository.exceptions.RollbackFailureException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author luzhe
 */
@Stateless
public class CommodityTypeJpaController implements CommodityTypeControl {
    private static final String PERSISTENCE_UNIT = "CTX-EJBPUv4";
    public CommodityTypeJpaController() {
       this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }
  
    private EntityManagerFactory emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

   
    @Override
    public void create(CommodityType commodityType) throws PreexistingEntityException, RollbackFailureException, Exception {
         EntityManager em=getEntityManager();
          EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            em = getEntityManager();
            Commodity commodityId = commodityType.getCommodityId();
            if (commodityId != null) {
                commodityId = em.getReference(commodityId.getClass(), commodityId.getCommodityId());
                commodityType.setCommodityId(commodityId);
            }
            Types typeId = commodityType.getTypeId();
            if (typeId != null) {
                typeId = em.getReference(typeId.getClass(), typeId.getTypeId());
                commodityType.setTypeId(typeId);
            }
            em.persist(commodityType);
            if (commodityId != null) {
                commodityId.getCommodityTypeCollection().add(commodityType);
                commodityId = em.merge(commodityId);
            }
            if (typeId != null) {
                typeId.getCommodityTypeCollection().add(commodityType);
                typeId = em.merge(typeId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCommodityType(commodityType.getCommodityTypeId()) != null) {
                throw new PreexistingEntityException("CommodityType " + commodityType + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    @Override
    public void edit(CommodityType commodityType) throws NonexistentEntityException, RollbackFailureException, Exception {
         EntityManager em=getEntityManager();
          EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            em = getEntityManager();
            CommodityType persistentCommodityType = em.find(CommodityType.class, commodityType.getCommodityTypeId());
            Commodity commodityIdOld = persistentCommodityType.getCommodityId();
            Commodity commodityIdNew = commodityType.getCommodityId();
            Types typeIdOld = persistentCommodityType.getTypeId();
            Types typeIdNew = commodityType.getTypeId();
            if (commodityIdNew != null) {
                commodityIdNew = em.getReference(commodityIdNew.getClass(), commodityIdNew.getCommodityId());
                commodityType.setCommodityId(commodityIdNew);
            }
            if (typeIdNew != null) {
                typeIdNew = em.getReference(typeIdNew.getClass(), typeIdNew.getTypeId());
                commodityType.setTypeId(typeIdNew);
            }
            commodityType = em.merge(commodityType);
            if (commodityIdOld != null && !commodityIdOld.equals(commodityIdNew)) {
                commodityIdOld.getCommodityTypeCollection().remove(commodityType);
                commodityIdOld = em.merge(commodityIdOld);
            }
            if (commodityIdNew != null && !commodityIdNew.equals(commodityIdOld)) {
                commodityIdNew.getCommodityTypeCollection().add(commodityType);
                commodityIdNew = em.merge(commodityIdNew);
            }
            if (typeIdOld != null && !typeIdOld.equals(typeIdNew)) {
                typeIdOld.getCommodityTypeCollection().remove(commodityType);
                typeIdOld = em.merge(typeIdOld);
            }
            if (typeIdNew != null && !typeIdNew.equals(typeIdOld)) {
                typeIdNew.getCommodityTypeCollection().add(commodityType);
                typeIdNew = em.merge(typeIdNew);
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
                Integer id = commodityType.getCommodityTypeId();
                if (findCommodityType(id) == null) {
                    throw new NonexistentEntityException("The commodityType with id " + id + " no longer exists.");
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
            CommodityType commodityType;
            try {
                commodityType = em.getReference(CommodityType.class, id);
                commodityType.getCommodityTypeId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The commodityType with id " + id + " no longer exists.", enfe);
            }
            Commodity commodityId = commodityType.getCommodityId();
            if (commodityId != null) {
                commodityId.getCommodityTypeCollection().remove(commodityType);
                commodityId = em.merge(commodityId);
            }
            Types typeId = commodityType.getTypeId();
            if (typeId != null) {
                typeId.getCommodityTypeCollection().remove(commodityType);
                typeId = em.merge(typeId);
            }
            em.remove(commodityType);
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
    public List<CommodityType> findCommodityTypeEntities() {
        return findCommodityTypeEntities(true, -1, -1);
    }

   
    @Override
    public List<CommodityType> findCommodityTypeEntities(int maxResults, int firstResult) {
        return findCommodityTypeEntities(false, maxResults, firstResult);
    }

    private List<CommodityType> findCommodityTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CommodityType.class));
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
    public CommodityType findCommodityType(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CommodityType.class, id);
        } finally {
            em.close();
        }
    }
    


    
    @Override
    public int getCommodityTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CommodityType> rt = cq.from(CommodityType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
