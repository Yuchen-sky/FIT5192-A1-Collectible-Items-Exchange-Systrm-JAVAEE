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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luzhe
 */
@Entity
@Table(name = "EXCHANGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Exchange.findAll", query = "SELECT e FROM Exchange e")
    , @NamedQuery(name = "Exchange.findByExchangeId", query = "SELECT e FROM Exchange e WHERE e.exchangeId = :exchangeId")
    , @NamedQuery(name = "Exchange.findByProvideUser", query = "SELECT e FROM Exchange e WHERE e.provideUser = :provideUser")
    , @NamedQuery(name = "Exchange.findByBuyOrSale", query = "SELECT e FROM Exchange e WHERE e.buyOrSale = :buyOrSale")
    , @NamedQuery(name = "Exchange.findByQuantity", query = "SELECT e FROM Exchange e WHERE e.quantity = :quantity")
    , @NamedQuery(name = "Exchange.findByPerPrice", query = "SELECT e FROM Exchange e WHERE e.perPrice = :perPrice")
    , @NamedQuery(name = "Exchange.findByPrice", query = "SELECT e FROM Exchange e WHERE e.price = :price")
    , @NamedQuery(name = "Exchange.findByNetProceed", query = "SELECT e FROM Exchange e WHERE e.netProceed = :netProceed")
    , @NamedQuery(name = "Exchange.findByPublishDate", query = "SELECT e FROM Exchange e WHERE e.publishDate = :publishDate")
    , @NamedQuery(name = "Exchange.findByPublishTime", query = "SELECT e FROM Exchange e WHERE e.publishTime = :publishTime")
    , @NamedQuery(name = "Exchange.findByDate", query = "SELECT e FROM Exchange e WHERE e.date = :date")
    , @NamedQuery(name = "Exchange.findByTime", query = "SELECT e FROM Exchange e WHERE e.time = :time")})
public class Exchange implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "EXCHANGE_ID")
    private Integer exchangeId;
    @Column(name = "PROVIDE_USER")
    private Integer provideUser;
    @Basic(optional = false)
    @Column(name = "BUY_OR_SALE")
    private short buyOrSale;
    @Basic(optional = false)
    @Column(name = "QUANTITY")
    private int quantity;
    @Basic(optional = false)
    @Column(name = "PER_PRICE")
    private double perPrice;
    @Basic(optional = false)
    @Column(name = "PRICE")
    private double price;
    @Basic(optional = false)
    @Column(name = "NET_PROCEED")
    private double netProceed;
    @Column(name = "PUBLISH_DATE")
    @Temporal(TemporalType.DATE)
    private Date publishDate;
    @Column(name = "PUBLISH_TIME")
    @Temporal(TemporalType.TIME)
    private Date publishTime;
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "TIME")
    @Temporal(TemporalType.TIME)
    private Date time;
    @JoinColumn(name = "COMMODITY_ID", referencedColumnName = "COMMODITY_ID")
    @ManyToOne
    private Commodity commodityId;
    @JoinColumn(name = "EXECUTE_USER", referencedColumnName = "USER_ID")
    @ManyToOne
    private Users executeUser;

    public Exchange() {
    }

    public Exchange(Integer exchangeId) {
        this.exchangeId = exchangeId;
    }

    public Exchange(Integer exchangeId, short buyOrSale, int quantity, double perPrice, double price, double netProceed) {
        this.exchangeId = exchangeId;
        this.buyOrSale = buyOrSale;
        this.quantity = quantity;
        this.perPrice = perPrice;
        this.price = price;
        this.netProceed = netProceed;
    }

    public Integer getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Integer exchangeId) {
        this.exchangeId = exchangeId;
    }

    public Integer getProvideUser() {
        return provideUser;
    }

    public void setProvideUser(Integer provideUser) {
        this.provideUser = provideUser;
    }

    public short getBuyOrSale() {
        return buyOrSale;
    }

    public void setBuyOrSale(short buyOrSale) {
        this.buyOrSale = buyOrSale;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public double getNetProceed() {
        return netProceed;
    }

    public void setNetProceed(double netProceed) {
        this.netProceed = netProceed;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
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

    public Commodity getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Commodity commodityId) {
        this.commodityId = commodityId;
    }

    public Users getExecuteUser() {
        return executeUser;
    }

    public void setExecuteUser(Users executeUser) {
        this.executeUser = executeUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exchangeId != null ? exchangeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exchange)) {
            return false;
        }
        Exchange other = (Exchange) object;
        if ((this.exchangeId == null && other.exchangeId != null) || (this.exchangeId != null && !this.exchangeId.equals(other.exchangeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fit5192.stu29184517.repository.entities.Exchange[ exchangeId=" + exchangeId + " ]";
    }
    
}
