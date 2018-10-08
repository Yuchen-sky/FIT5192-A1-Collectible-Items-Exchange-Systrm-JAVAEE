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
import fit5192.stu29184517.repository.entities.Possession;
import fit5192.stu29184517.repository.entities.Users;
import fit5192.stu29184517.repository.exceptions.NonexistentEntityException;
import fit5192.stu29184517.repository.exceptions.PreexistingEntityException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author luzhe
 */
@Stateless
public class PossessionJpaController implements PossessionControl {
    private static final String PERSISTENCE_UNIT = "CTX-EJBPUv4";
    public PossessionJpaController() {
        emf=Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }
    private EntityManagerFactory emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Possession possession) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
           
         
            em.persist(possession);
         
            
        } catch (Exception ex) {
            if (findPossession(possession.getPossessionId()) != null) {
                throw new PreexistingEntityException("Possession " + possession + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Possession possession) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
           
            Possession persistentPossession = em.find(Possession.class, possession.getPossessionId());
            Commodity commodityIdOld = persistentPossession.getCommodityId();
            Commodity commodityIdNew = possession.getCommodityId();
            Users ownerIdOld = persistentPossession.getOwnerId();
            Users ownerIdNew = possession.getOwnerId();
            if (commodityIdNew != null) {
                commodityIdNew = em.getReference(commodityIdNew.getClass(), commodityIdNew.getCommodityId());
                possession.setCommodityId(commodityIdNew);
            }
            if (ownerIdNew != null) {
                ownerIdNew = em.getReference(ownerIdNew.getClass(), ownerIdNew.getUserId());
                possession.setOwnerId(ownerIdNew);
            }
            possession = em.merge(possession);
            if (commodityIdOld != null && !commodityIdOld.equals(commodityIdNew)) {
                commodityIdOld.getPossessionCollection().remove(possession);
                commodityIdOld = em.merge(commodityIdOld);
            }
            if (commodityIdNew != null && !commodityIdNew.equals(commodityIdOld)) {
                commodityIdNew.getPossessionCollection().add(possession);
                commodityIdNew = em.merge(commodityIdNew);
            }
            if (ownerIdOld != null && !ownerIdOld.equals(ownerIdNew)) {
                ownerIdOld.getPossessionCollection().remove(possession);
                ownerIdOld = em.merge(ownerIdOld);
            }
            if (ownerIdNew != null && !ownerIdNew.equals(ownerIdOld)) {
                ownerIdNew.getPossessionCollection().add(possession);
                ownerIdNew = em.merge(ownerIdNew);
            }
          
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = possession.getPossessionId();
                if (findPossession(id) == null) {
                    throw new NonexistentEntityException("The possession with id " + id + " no longer exists.");
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
    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
          
            Possession possession;
            try {
                possession = em.getReference(Possession.class, id);
                possession.getPossessionId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The possession with id " + id + " no longer exists.", enfe);
            }
            Commodity commodityId = possession.getCommodityId();
            if (commodityId != null) {
                commodityId.getPossessionCollection().remove(possession);
                commodityId = em.merge(commodityId);
            }
            Users ownerId = possession.getOwnerId();
            if (ownerId != null) {
                ownerId.getPossessionCollection().remove(possession);
                ownerId = em.merge(ownerId);
            }
            em.remove(possession);
          
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Possession> findPossessionEntities() {
        return findPossessionEntities(true, -1, -1);
    }

    @Override
    public List<Possession> findPossessionEntities(int maxResults, int firstResult) {
        return findPossessionEntities(false, maxResults, firstResult);
    }

    private List<Possession> findPossessionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Possession.class));
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
    public Possession findPossession(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Possession.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getPossessionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Possession> rt = cq.from(Possession.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
