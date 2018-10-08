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
@Table(name = "BUY_ORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BuyOrder.findAll", query = "SELECT b FROM BuyOrder b")
    , @NamedQuery(name = "BuyOrder.findByBuyOrderId", query = "SELECT b FROM BuyOrder b WHERE b.buyOrderId = :buyOrderId")
    , @NamedQuery(name = "BuyOrder.findByPerPrice", query = "SELECT b FROM BuyOrder b WHERE b.perPrice = :perPrice")
    , @NamedQuery(name = "BuyOrder.findByPrice", query = "SELECT b FROM BuyOrder b WHERE b.price = :price")
    , @NamedQuery(name = "BuyOrder.findByDate", query = "SELECT b FROM BuyOrder b WHERE b.date = :date")
    , @NamedQuery(name = "BuyOrder.findByTime", query = "SELECT b FROM BuyOrder b WHERE b.time = :time")
    , @NamedQuery(name = "BuyOrder.findByQuantity", query = "SELECT b FROM BuyOrder b WHERE b.quantity = :quantity")})
public class BuyOrder implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "LOCK")
    private Boolean lock;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "BUY_ORDER_ID")
    private Integer buyOrderId;
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

    public BuyOrder() {
    }

    public BuyOrder(Integer buyOrderId) {
        this.buyOrderId = buyOrderId;
    }

    public BuyOrder(Integer buyOrderId, double perPrice, double price, int quantity) {
        this.buyOrderId = buyOrderId;
        this.perPrice = perPrice;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getBuyOrderId() {
        return buyOrderId;
    }

    public void setBuyOrderId(Integer buyOrderId) {
        this.buyOrderId = buyOrderId;
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
        hash += (buyOrderId != null ? buyOrderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BuyOrder)) {
            return false;
        }
        BuyOrder other = (BuyOrder) object;
        if ((this.buyOrderId == null && other.buyOrderId != null) || (this.buyOrderId != null && !this.buyOrderId.equals(other.buyOrderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fit5192.stu29184517.repository.entities.BuyOrder[ buyOrderId=" + buyOrderId + " ]";
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }
    
}
