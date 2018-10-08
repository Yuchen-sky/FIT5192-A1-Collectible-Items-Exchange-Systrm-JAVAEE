/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luzhe
 */
@Entity
@Table(name = "SALE_ORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaleOrder.findAll", query = "SELECT s FROM SaleOrder s")
    , @NamedQuery(name = "SaleOrder.findBySaleOrderId", query = "SELECT s FROM SaleOrder s WHERE s.saleOrderId = :saleOrderId")
    , @NamedQuery(name = "SaleOrder.findByPerPrice", query = "SELECT s FROM SaleOrder s WHERE s.perPrice = :perPrice")
    , @NamedQuery(name = "SaleOrder.findByPrice", query = "SELECT s FROM SaleOrder s WHERE s.price = :price")
    , @NamedQuery(name = "SaleOrder.findByDate", query = "SELECT s FROM SaleOrder s WHERE s.date = :date")
    , @NamedQuery(name = "SaleOrder.findByTime", query = "SELECT s FROM SaleOrder s WHERE s.time = :time")
    , @NamedQuery(name = "SaleOrder.findByQuantity", query = "SELECT s FROM SaleOrder s WHERE s.quantity = :quantity")})
public class SaleOrder implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "LOCK")
    private Boolean lock;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SALE_ORDER_ID")
    private Integer saleOrderId;
    @Basic(optional = false)
    @Column(name = "PER_PRICE")
    private double perPrice;
    @Basic(optional = false)
    @Column(name = "PRICE")
    private double price;
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "TIME")
    @Temporal(TemporalType.TIME)
    private Date time;
    @Basic(optional = false)
    @Column(name = "QUANTITY")
    private int quantity;
    @JoinColumn(name = "COMMODITY_ID", referencedColumnName = "COMMODITY_ID")
    @ManyToOne
    private Commodity commodityId;
    @JoinColumn(name = "OWNER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users ownerId;

    public SaleOrder() {
    }

    public SaleOrder(Integer saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    public SaleOrder(Integer saleOrderId, double perPrice, double price, int quantity) {
        this.saleOrderId = saleOrderId;
        this.perPrice = perPrice;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleOrderId(Integer saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    public double getPerPrice() {
        return perPrice;
    }

    public void setPerPrice(double perPrice) {
        this.perPrice = perPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Commodity getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Commodity commodityId) {
        this.commodityId = commodityId;
    }

    public Users getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Users ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (saleOrderId != null ? saleOrderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaleOrder)) {
            return false;
        }
        SaleOrder other = (SaleOrder) object;
        if ((this.saleOrderId == null && other.saleOrderId != null) || (this.saleOrderId != null && !this.saleOrderId.equals(other.saleOrderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fit5192.stu29184517.repository.entities.SaleOrder[ saleOrderId=" + saleOrderId + " ]";
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }
    
}
