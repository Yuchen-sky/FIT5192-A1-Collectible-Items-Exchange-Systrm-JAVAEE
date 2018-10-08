/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luzhe
 */
@Entity
@Table(name = "COMMODITY_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CommodityType.findAll", query = "SELECT c FROM CommodityType c")
    , @NamedQuery(name = "CommodityType.findByCommodityTypeId", query = "SELECT c FROM CommodityType c WHERE c.commodityTypeId = :commodityTypeId")})
public class CommodityType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COMMODITY_TYPE_ID")
    private Integer commodityTypeId;
    @JoinColumn(name = "COMMODITY_ID", referencedColumnName = "COMMODITY_ID")
    @ManyToOne
    private Commodity commodityId;
    @JoinColumn(name = "TYPE_ID", referencedColumnName = "TYPE_ID")
    @ManyToOne
    private Types typeId;

    public CommodityType() {
    }

    public CommodityType(Integer commodityTypeId) {
        this.commodityTypeId = commodityTypeId;
    }

    public Integer getCommodityTypeId() {
        return commodityTypeId;
    }

    public void setCommodityTypeId(Integer commodityTypeId) {
        this.commodityTypeId = commodityTypeId;
    }

    public Commodity getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Commodity commodityId) {
        this.commodityId = commodityId;
    }

    public Types getTypeId() {
        return typeId;
    }

    public void setTypeId(Types typeId) {
        this.typeId = typeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commodityTypeId != null ? commodityTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommodityType)) {
            return false;
        }
        CommodityType other = (CommodityType) object;
        if ((this.commodityTypeId == null && other.commodityTypeId != null) || (this.commodityTypeId != null && !this.commodityTypeId.equals(other.commodityTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fit5192.stu29184517.repository.entities.CommodityType[ commodityTypeId=" + commodityTypeId + " ]";
    }
    
}
