/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517;
import fit5192.stu29184517.repository.UsersControl;
import fit5192.stu29184517.repository.TypesControl;
import fit5192.stu29184517.repository.CommodityControl;
import fit5192.stu29184517.repository.BuyOrderControl;
import fit5192.stu29184517.repository.ExchangeControl;
import fit5192.stu29184517.repository.SaleOrderControl;
import fit5192.stu29184517.gui.GUIimpl;
import fit5192.stu29184517.repository.entities.Users;
import fit5192.stu29184517.repository.entities.BuyOrder;
import fit5192.stu29184517.repository.entities.SaleOrder;
import fit5192.stu29184517.repository.entities.Exchange;
import fit5192.stu29184517.repository.entities.CommodityType;
import fit5192.stu29184517.repository.entities.Types;
import fit5192.stu29184517.repository.entities.Commodity;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 *
 * @author luzhe
 */
public class application implements ActionListener, ListSelectionListener{
   
   @EJB
   private static UsersControl user_control;
   @EJB
   private static TypesControl type_control;
  @EJB
   private static CommodityControl commodity_control;  
    @EJB
   private static  BuyOrderControl buy_control;
    @EJB
    private static  SaleOrderControl sale_control;
 
    /**
     * @param args the command line arguments
     */
   
   private GUIimpl gui;
   
   
    @Override
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == gui.getjButtonViewAll()) {
           this.displayAllCommodities();
        } else if (event.getSource() == gui.getjButtonId()) {
            this.searchCommodityById();
        } else if (event.getSource() == gui.getjButtonTitle()) {
            this.searchCommodityByTitle();
        } else if (event.getSource() == gui.getjButtonType()) {
            this.searchCommodityByType();
        } else {
            
            System.exit(0);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
  /*      if ((event.getSource() == this.gui.getPropertyTable().getSelectionModel())
            && (! event.getValueIsAdjusting()))
        {
            try
            {
                if (this.gui.isPropertySelected()) {
                    int propertyId = this.gui.getSelectedPropertyId();
                
                    Property property = PropertyRepositoryFactory.getInstance().searchPropertyById(propertyId);
                    this.gui.displaySelectedPropertyDetails(property);
                }               
            }
            catch (Exception e)
            {
                gui.displayMessageInDialog(e.getMessage());
            }
        }*/
    }
   
   
     
   public void initView() {     
      gui = new GUIimpl(this,this);            
      gui.setVisible(true);  
      displayAllCommodities();
      displayAllTypes();
       // this.displayAllProperties();
       // this.displayAllContactPeople();
    }

    private void displayAllCommodities() {
        try {
            List<Commodity> properties = this.commodity_control.findCommodityEntities();
            
            if (properties != null) {
                this.gui.displayItemDetails(properties);
            }
            
        } catch (Exception ex) {
            this.gui.displayMessageInDialog("Failed to retrieve properties: " + ex.getMessage());
        }
    }
   
     private void displayAllTypes() {
        try {
            List<Types> properties = this.type_control.findTypesEntities();
            for (Types type  : properties) {
            System.out.println(type.getTypeName()); }
            if (properties != null) {
                this.gui.displayTypes(properties);
            }
            
        } catch (Exception ex) {
            this.gui.displayMessageInDialog("Failed to retrieve types: " + ex.getMessage());
        }
    }
   
     
    private void searchCommodityById() {
       try {
            List<Commodity> properties = new ArrayList();
             
            int id = this.gui.getCommodityId();
            System.out.println(id);
            properties.add(this.commodity_control.findCommodity(id));
             List<BuyOrder> buyorders=this.buy_control.findBuyOrderByUser(id);
            List<SaleOrder> Saleorders=this.sale_control.findSaleOrderByUser(id);
            if (properties != null) {
                String name=properties.get(0).getTitle();
                this.gui.displayItemDetails(properties);
                this.gui.displayBuy(buyorders,name);
                this.gui.displaySale(Saleorders,name);
            }
            
        } catch (Exception ex) {
            this.gui.displayMessageInDialog("Failed to retrieve properties: " + ex.getMessage());
        }
    }
     
    private void  searchCommodityByTitle(){
    try {
            String title=this.gui.getCommodityTitle();
            List<Commodity> properties = this.commodity_control.findCommodityTitle(title);
            //int id = this.gui.getCommodityId();
            //properties.add(this.commodity_control.findCommodity(id));
            
            if (properties != null&&(properties.size()!=0)) {
                String name=properties.get(0).getTitle();
                int id=properties.get(0).getCommodityId();
            List<BuyOrder> buyorders=this.buy_control.findBuyOrderByUser(id);
            List<SaleOrder> Saleorders=this.sale_control.findSaleOrderByUser(id);
                this.gui.displayItemDetails(properties);
                this.gui.displayBuy(buyorders,name);
                this.gui.displaySale(Saleorders,name); 
            }else if(properties.size()==0){
            this.gui.displayMessageInDialog("The commodity don't exist. ");
            }
            
        } catch (Exception ex) {
            this.gui.displayMessageInDialog("Failed to retrieve properties: " + ex.getMessage());
        }
    
    }
    
    //findCommodityType(String typename)
    private void  searchCommodityByType(){
         try {
        String type=this.gui.getCommodityType();
        List<Commodity> properties = this.commodity_control.findCommodityType(type);
          System.out.println(properties.size()); 
        if (properties != null) {
                 System.out.println(properties.size()); 
                this.gui.displayItemDetails(properties);
               
            }
        
        } catch (Exception ex) {
            this.gui.displayMessageInDialog("Failed to retrieve properties: " + ex.getMessage());
        }
    
    }
   
    public static void main(String[] args) {
        // TODO code application logic here
       try {
            final application agency = new application();        
            agency.initView();      

        } catch (Exception ex) {
            System.out.println("Failed to run application: " + ex.getMessage());
        }

       
       
       
       
       
       
       
       
       
       
       
       /*
                  
      Types ty=new Types(5,"ffffadfdfssddssdfds");      
       try {
           type_control.create(ty);
       } catch (Exception ex) {
           Logger.getLogger(application.class.getName()).log(Level.SEVERE, null, ex);
       }
      try{
       Types type=type_control.findTypes(1);
       
      System.out.println(type.getTypeName());
      }
      catch (Exception re) {
                System.out.println(re);
            }
      
      List<Types> typelist=type_control.findTypesEntities();
      for(Types usertemp : typelist) {  
        System.out.println(usertemp.getTypeName());
         }
        System.out.println("Hello World.I am Black King Trading System");
       
      //  Users b=new Users(5,"gggffffffff",(short)0,1000,(short)0,0);
      // b.setEmail("dsafddsdf");
       List<Users> userlist=user_control.findUsersEntities();
       for(Users usertemp : userlist) {  
        System.out.println(usertemp.getPassword());
         }
        System.out.println(type_control.getTypesCount());
       
        */
    }
    
}
