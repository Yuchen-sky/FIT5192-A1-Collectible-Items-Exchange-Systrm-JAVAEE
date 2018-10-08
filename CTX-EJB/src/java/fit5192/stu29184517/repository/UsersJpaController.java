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
import fit5192.stu29184517.repository.entities.SaleOrder;
import java.util.ArrayList;
import java.util.Collection;
import fit5192.stu29184517.repository.entities.BuyOrder;
import fit5192.stu29184517.repository.entities.Exchange;
import fit5192.stu29184517.repository.entities.Users;
import fit5192.stu29184517.repository.exceptions.NonexistentEntityException;
import fit5192.stu29184517.repository.exceptions.PreexistingEntityException;
import fit5192.stu29184517.repository.exceptions.RollbackFailureException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author luzhe
 */
@Stateless
public class UsersJpaController implements UsersControl {
    private static final String PERSISTENCE_UNIT = "CTX-EJBPUv4";
    public UsersJpaController() {
        
        this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
       this.em = this.emf.createEntityManager();
    }
    
     public UsersJpaController(EntityManagerFactory emf) {   
        this.emf = emf;
      // this.em = this.emf.createEntityManager();
    }
    
    private EntityManager em;
    private EntityManagerFactory emf = null;
    
    @Override
    public EntityManager getEntityManager() {
       return emf.createEntityManager();
    }
    

    @Override
    public void create(Users users) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (users.getSaleOrderCollection() == null) {
            users.setSaleOrderCollection(new ArrayList<SaleOrder>());
        }
        if (users.getBuyOrderCollection() == null) {
            users.setBuyOrderCollection(new ArrayList<BuyOrder>());
        }
        if (users.getExchangeCollection() == null) {
            users.setExchangeCollection(new ArrayList<Exchange>());
        }
        EntityManager em=getEntityManager();
        //  EntityTransaction utx = em.getTransaction();
        try {
            
       //     utx.begin();
            
            
            em.persist(users);
           
        //    utx.commit();
        } catch (Exception ex) {
            try {
       //         utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsers(users.getUserId()) != null) {
                throw new PreexistingEntityException("Users " + users + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Users users) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em=getEntityManager();
        //EntityTransaction utx = em.getTransaction();
        try {
           // utx.begin();
            
           
           
            users = em.merge(users);
            
            
            
          //  utx.commit();
        } catch (Exception ex) {
            try {
         //       utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = users.getUserId();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
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
        //EntityTransaction utx = em.getTransaction();
        try {
           // utx.begin();
            
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
          
            em.remove(users);
           // utx.commit();
        } catch (Exception ex) {
            try {
          //      utx.rollback();
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
    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    @Override
    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em=getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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
    public Users findUsers(Integer id) {
        EntityManager em=getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
           em.close();
        }
    }
    
    @Override
    public Users findUsersByEmail(String email, String password) {
        //EntityManager em=getEntityManager();
          EntityManager em = getEntityManager();
              
          try {
           TypedQuery<Users> q = em.createQuery("SELECT c FROM  Users c WHERE c.email = :email AND c.password =:password ", Users.class);
            q.setParameter("email", email);
             q.setParameter("password", password);
            if(q.getResultList().size()==0)return null;
             return q.getSingleResult();
            
        } finally {
            em.close();
        }
        
    
    }
    
    @Override
    public List<Users> findUsersFirst(String first){
      EntityManager em = getEntityManager();
              
        try {
           Query query = em.createNamedQuery("Users.findByFirstName");
            query.setParameter("firstName", first);
            return query.getResultList();
        } finally {
            em.close();
        }
    
    
    }
    
    @Override
    public List<Users> findUsersLast(String first){
        
      EntityManager em = getEntityManager();
              
        try {
           Query query = em.createNamedQuery("Users.findByLastName");
            query.setParameter("lastName", first);
            return query.getResultList();
        } finally {
            em.close();
        }
    
    
    }
    @Override
    public List<Users> findUsersPhone(Integer phone){
      EntityManager em = getEntityManager();
              
        try {
           Query query = em.createNamedQuery("Users.findByPhoneNumber");
            query.setParameter("phoneNumber", phone);
            return query.getResultList();
        } finally {
            em.close();
        }
    
    
    }
    @Override
    public List<Users> findUsersEmail(String first){
      EntityManager em = getEntityManager();
              
        try {
           Query query = em.createNamedQuery("Users.findByEmail");
            query.setParameter("email", first);
            return query.getResultList();
        } finally {
            em.close();
        }
    
    
    }
    
    

    @Override
    public int getUsersCount() {
        EntityManager em=getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    
     @Override
    public List<Users> multifind(int id,String firstname,String lastname,int phone,String email) {
        EntityManager em = getEntityManager();
        String search=" SELECT u FROM  Users u ";
       boolean [] flag={false,false,false,false,false};
       
        try {
            if(id==0&&phone==0&&firstname.length()<=0&&lastname.length()<=0&&email.length()<=0){
            TypedQuery<Users> q = em.createQuery(search,Users.class);
             return q.getResultList();
            }
            search+="  WHERE ";
            if(id!=0){
                search+=" u.userId=:id AND ";
                flag[0]=true;                    
                    }  
             if(firstname.length()>0){
                search+=" uu.firstName=:firstname AND ";
                flag[1]=true;                    
                    } 
              if(lastname.length()>0){
                search+=" u.lastName=:lastName AND ";
                flag[2]=true;                    
                    } 
               if(phone!=0){
                search+=" u.phoneNumber=:phone AND ";
                flag[3]=true;                    
                    } 
                if(email.length()>0){
                search+=" u.email=:email AND ";
                flag[4]=true;                    
                    } 
                search = search.substring(0,search.length() - 4);
                
            TypedQuery<Users> q = em.createQuery(search, Users.class);
            if(flag[0])q.setParameter("id", id);
            if(flag[1])q.setParameter("firstname", firstname);
            if(flag[2])q.setParameter("lastname", lastname);
            if(flag[3])q.setParameter("phone", phone);
            if(flag[4])q.setParameter("email", email);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    
}
