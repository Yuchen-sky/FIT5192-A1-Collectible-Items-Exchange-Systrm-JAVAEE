package fit5192;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fit5192.stu29184517.repository.UsersJpaController;
import fit5192.stu29184517.repository.TypesJpaController;
import fit5192.stu29184517.repository.CommodityJpaController;
import fit5192.stu29184517.repository.entities.Users;
import fit5192.stu29184517.repository.entities.Types;
import fit5192.stu29184517.repository.entities.Commodity;
import fit5192.stu29184517.repository.exceptions.RollbackFailureException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
/**
 *
 * @author luzhe
 */
public class test {
    
    
    
    public static void main(String[] args)  {
        UsersJpaController a=new UsersJpaController();
      //  TypesJpaController c=new TypesJpaController();
     // CommodityJpaController c=new CommodityJpaController() ;
        Users user=a.findUsers(2);
       // Types type=c.findTypes(1);
        System.out.println("Hello World.123");
      //  System.out.println(type.getTypeName());
        Users b=new Users(3,"gggffffffff",(short)0,1000,(short)0,0);
        b.setEmail("dsafddsdf");
        List<Users> userlist=a.findUsersEntities();
        for(Users usertemp : userlist) {  
         System.out.println(usertemp.getPassword());
         }
        System.out.println(a.getUsersCount());
      //  System.out.println(c.getCommodityCount());
        try {
            a.edit(b);
        } catch (Exception ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
