/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luzhe
 */
@Entity
@Table(name = "COMMODITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Commodity.findAll", query = "SELECT c FROM Commodity c")
    , @NamedQuery(name = "Commodity.findByCommodityId", query = "SELECT c FROM Commodity c WHERE c.commodityId = :commodityId")
    , @NamedQuery(name = "Commodity.findByTitle", query = "SELECT c FROM Commodity c WHERE c.title = :title")
    , @NamedQuery(name = "Commodity.findByImage", query = "SELECT c FROM Commodity c WHERE c.image = :image")
    , @NamedQuery(name = "Commodity.findByQuantity", query = "SELECT c FROM Commodity c WHERE c.quantity = :quantity")
    , @NamedQuery(name = "Commodity.findByQuantityCirculation", query = "SELECT c FROM Commodity c WHERE c.quantityCirculation = :quantityCirculation")
    , @NamedQuery(name = "Commodity.findBySaleOrderNum", query = "SELECT c FROM Commodity c WHERE c.saleOrderNum = :saleOrderNum")
    , @NamedQuery(name = "Commodity.findByBuyOrderNum", query = "SELECT c FROM Commodity c WHERE c.buyOrderNum = :buyOrderNum")
    , @NamedQuery(name = "Commodity.findByDescription", query = "SELECT c FROM Commodity c WHERE c.description = :description")
    , @NamedQuery(name = "Commodity.findByFeatures", query = "SELECT c FROM Commodity c WHERE c.features = :features")
    , @NamedQuery(name = "Commodity.findByQuality", query = "SELECT c FROM Commodity c WHERE c.quality = :quality")
    , @NamedQuery(name = "Commodity.findByReleaseDate", query = "SELECT c FROM Commodity c WHERE c.releaseDate = :releaseDate")
    , @NamedQuery(name = "Commodity.findByPublisher", query = "SELECT c FROM Commodity c WHERE c.publisher = :publisher")
    , @NamedQuery(name = "Commodity.findByStradingStatus", query = "SELECT c FROM Commodity c WHERE c.stradingStatus = :stradingStatus")
    , @NamedQuery(name = "Commodity.findByTaxrate", query = "SELECT c FROM Commodity c WHERE c.taxrate = :taxrate")})
public class Commodity implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commodityId")
    private Collection<Possession> possessionCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COMMODITY_ID")
    private Integer commodityId;
    @Basic(optional = false)
    @Column(name = "TITLE")
    private String title;
    @Column(name = "IMAGE")
    private String image;
    @Basic(optional = false)
    @Column(name = "QUANTITY")
    private int quantity;
    @Basic(optional = false)
    @Column(name = "QUANTITY_CIRCULATION")
    private int quantityCirculation;
    @Basic(optional = false)
    @Column(name = "SALE_ORDER_NUM")
    private int saleOrderNum;
    @Basic(optional = false)
    @Column(name = "BUY_ORDER_NUM")
    private int buyOrderNum;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "FEATURES")
    private String features;
    @Column(name = "QUALITY")
    private String quality;
    @Column(name = "RELEASE_DATE")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    @Column(name = "PUBLISHER")
    private String publisher;
    @Basic(optional = false)
    @Column(name = "STRADING_STATUS")
    private short stradingStatus;
    @Basic(optional = false)
    @Column(name = "TAXRATE")
    private double taxrate;
    @OneToMany(mappedBy = "commodityId")
    private Collection<CommodityType> commodityTypeCollection;
    @OneToMany(mappedBy = "commodityId")
    private Collection<SaleOrder> saleOrderCollection;
    @OneToMany(mappedBy = "commodityId")
    private Collection<BuyOrder> buyOrderCollection;
    @OneToMany(mappedBy = "commodityId")
    private Collection<Exchange> exchangeCollection;

    public Commodity() {
    }

    public Commodity(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Commodity(Integer commodityId, String title, int quantity, int quantityCirculation, int saleOrderNum, int buyOrderNum, short stradingStatus, double taxrate) {
        this.commodityId = commodityId;
        this.title = title;
        this.quantity = quantity;
        this.quantityCirculation = quantityCirculation;
        this.saleOrderNum = saleOrderNum;
        this.buyOrderNum = buyOrderNum;
        this.stradingStatus = stradingStatus;
        this.taxrate = taxrate;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityCirculation() {
        return quantityCirculation;
    }

    public void setQuantityCirculation(int quantityCirculation) {
        this.quantityCirculation = quantityCirculation;
    }

    public int getSaleOrderNum() {
        return saleOrderNum;
    }

    public void setSaleOrderNum(int saleOrderNum) {
        this.saleOrderNum = saleOrderNum;
    }

    public int getBuyOrderNum() {
        return buyOrderNum;
    }

    public void setBuyOrderNum(int buyOrderNum) {
        this.buyOrderNum = buyOrderNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public short getStradingStatus() {
        return stradingStatus;
    }

    public void setStradingStatus(short stradingStatus) {
        this.stradingStatus = stradingStatus;
    }

    public double getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(double taxrate) {
        this.taxrate = taxrate;
    }

    @XmlTransient
    public Collection<CommodityType> getCommodityTypeCollection() {
        return commodityTypeCollection;
    }

    public void setCommodityTypeCollection(Collection<CommodityType> commodityTypeCollection) {
        this.commodityTypeCollection = commodityTypeCollection;
    }

    @XmlTransient
    public Collection<SaleOrder> getSaleOrderCollection() {
        return saleOrderCollection;
    }

    public void setSaleOrderCollection(Collection<SaleOrder> saleOrderCollection) {
        this.saleOrderCollection = saleOrderCollection;
    }

    @XmlTransient
    public Collection<BuyOrder> getBuyOrderCollection() {
        return buyOrderCollection;
    }

    public void setBuyOrderCollection(Collection<BuyOrder> buyOrderCollection) {
        this.buyOrderCollection = buyOrderCollection;
    }

    @XmlTransient
    public Collection<Exchange> getExchangeCollection() {
        return exchangeCollection;
    }

    public void setExchangeCollection(Collection<Exchange> exchangeCollection) {
        this.exchangeCollection = exchangeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commodityId != null ? commodityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commodity)) {
            return false;
        }
        Commodity other = (Commodity) object;
        if ((this.commodityId == null && other.commodityId != null) || (this.commodityId != null && !this.commodityId.equals(other.commodityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fit5192.stu29184517.repository.entities.Commodity[ commodityId=" + commodityId + " ]";
    }

    @XmlTransient
    public Collection<Possession> getPossessionCollection() {
        return possessionCollection;
    }

    public void setPossessionCollection(Collection<Possession> possessionCollection) {
        this.possessionCollection = possessionCollection;
    }
    
}
