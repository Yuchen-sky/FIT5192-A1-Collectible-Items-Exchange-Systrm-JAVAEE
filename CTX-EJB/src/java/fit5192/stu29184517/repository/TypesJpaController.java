/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository;

import fit5192.stu29184517.repository.entities.Commodity;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import fit5192.stu29184517.repository.entities.CommodityType;
import fit5192.stu29184517.repository.entities.Types;
import fit5192.stu29184517.repository.exceptions.NonexistentEntityException;
import fit5192.stu29184517.repository.exceptions.PreexistingEntityException;
import fit5192.stu29184517.repository.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


/**
 *
 * @author luzhe
 */
@Stateless
public class TypesJpaController implements TypesControl {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void create(Types types) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (types.getCommodityTypeCollection() == null) {
            types.setCommodityTypeCollection(new ArrayList<CommodityType>());
        }
        
        try {
            
          
           
            em.persist(types);
           
           
        } catch (Exception ex) {
            try {
               
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTypes(types.getTypeId()) != null) {
                throw new PreexistingEntityException("Types " + types + " already exists.", ex);
            }
            throw ex;
        } finally {
           
        }
    }

        
   
    
    
    public TypesJpaController() {
       
        
    }
     
    

   @Override
   public EntityManager getEntityManager() {
       return this.em;
  }
    
    
    @Override
    public void edit(Types types) throws NonexistentEntityException, RollbackFailureException, Exception {
       
        try {
           
            
            Types persistentTypes = em.find(Types.class, types.getTypeId());
            Collection<CommodityType> commodityTypeCollectionOld = persistentTypes.getCommodityTypeCollection();
            Collection<CommodityType> commodityTypeCollectionNew = types.getCommodityTypeCollection();
            Collection<CommodityType> attachedCommodityTypeCollectionNew = new ArrayList<CommodityType>();
            for (CommodityType commodityTypeCollectionNewCommodityTypeToAttach : commodityTypeCollectionNew) {
                commodityTypeCollectionNewCommodityTypeToAttach = em.getReference(commodityTypeCollectionNewCommodityTypeToAttach.getClass(), commodityTypeCollectionNewCommodityTypeToAttach.getCommodityTypeId());
                attachedCommodityTypeCollectionNew.add(commodityTypeCollectionNewCommodityTypeToAttach);
            }
            commodityTypeCollectionNew = attachedCommodityTypeCollectionNew;
            types.setCommodityTypeCollection(commodityTypeCollectionNew);
            types = em.merge(types);
            for (CommodityType commodityTypeCollectionOldCommodityType : commodityTypeCollectionOld) {
                if (!commodityTypeCollectionNew.contains(commodityTypeCollectionOldCommodityType)) {
                    commodityTypeCollectionOldCommodityType.setTypeId(null);
                    commodityTypeCollectionOldCommodityType = em.merge(commodityTypeCollectionOldCommodityType);
                }
            }
            for (CommodityType commodityTypeCollectionNewCommodityType : commodityTypeCollectionNew) {
                if (!commodityTypeCollectionOld.contains(commodityTypeCollectionNewCommodityType)) {
                    Types oldTypeIdOfCommodityTypeCollectionNewCommodityType = commodityTypeCollectionNewCommodityType.getTypeId();
                    commodityTypeCollectionNewCommodityType.setTypeId(types);
                    commodityTypeCollectionNewCommodityType = em.merge(commodityTypeCollectionNewCommodityType);
                    if (oldTypeIdOfCommodityTypeCollectionNewCommodityType != null && !oldTypeIdOfCommodityTypeCollectionNewCommodityType.equals(types)) {
                        oldTypeIdOfCommodityTypeCollectionNewCommodityType.getCommodityTypeCollection().remove(commodityTypeCollectionNewCommodityType);
                        oldTypeIdOfCommodityTypeCollectionNewCommodityType = em.merge(oldTypeIdOfCommodityTypeCollectionNewCommodityType);
                    }
                }
            }
           
        } catch (Exception ex) {
            try {
               
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = types.getTypeId();
                if (findTypes(id) == null) {
                    throw new NonexistentEntityException("The types with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
           
        }
    }

    @Override
    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
       
        try {
            
          
            Types types;
            try {
                types = em.getReference(Types.class, id);
                types.getTypeId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The types with id " + id + " no longer exists.", enfe);
            }
            Collection<CommodityType> commodityTypeCollection = types.getCommodityTypeCollection();
            for (CommodityType commodityTypeCollectionCommodityType : commodityTypeCollection) {
                commodityTypeCollectionCommodityType.setTypeId(null);
                commodityTypeCollectionCommodityType = em.merge(commodityTypeCollectionCommodityType);
            }
            em.remove(types);
            
        } catch (Exception ex) {
            try {
                
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            
        }
    }

    @Override
    public List<Types> findTypesEntities() {
        return findTypesEntities(true, -1, -1);
    }

    @Override
    public List<Types> findTypesEntities(int maxResults, int firstResult) {
        return findTypesEntities(false, maxResults, firstResult);
    }

    private List<Types> findTypesEntities(boolean all, int maxResults, int firstResult) {
        
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Types.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
          
        }
    }

    @Override
    public Types findTypes(Integer id) {
        
        try {
            return em.find(Types.class, id);
        } finally {
          
        }
    }

    @Override
    public List<Types> findTypeByItem(Integer id) {
        
              
        
           TypedQuery<Types> q = em.createQuery("SELECT d FROM  Commodity c,CommodityType b, Types d WHERE c.commodityId=:id AND b.commodityId =c AND b.typeId =d ", Types.class);
            q.setParameter("id", id);
            return  q.getResultList();
        
    }
    
    
    
    @Override
    public int getTypesCount() {
        
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Types> rt = cq.from(Types.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }
    
}
