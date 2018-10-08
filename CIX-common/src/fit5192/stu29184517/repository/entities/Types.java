/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luzhe
 */
@Entity
@Table(name = "TYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Types.findAll", query = "SELECT t FROM Types t")
    , @NamedQuery(name = "Types.findByTypeId", query = "SELECT t FROM Types t WHERE t.typeId = :typeId")
    , @NamedQuery(name = "Types.findByTypeName", query = "SELECT t FROM Types t WHERE t.typeName = :typeName")
    , @NamedQuery(name = "Types.findByDescription", query = "SELECT t FROM Types t WHERE t.description = :description")
    , @NamedQuery(name = "Types.findByFatherType", query = "SELECT t FROM Types t WHERE t.fatherType = :fatherType")})
public class Types implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TYPE_ID")
    private Integer typeId;
    @Basic(optional = false)
    @Column(name = "TYPE_NAME")
    private String typeName;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "FATHER_TYPE")
    private Integer fatherType;
    @OneToMany(mappedBy = "typeId")
    private Collection<CommodityType> commodityTypeCollection;

    public Types() {
    }

    public Types(Integer typeId) {
        this.typeId = typeId;
    }

    public Types(Integer typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFatherType() {
        return fatherType;
    }

    public void setFatherType(Integer fatherType) {
        this.fatherType = fatherType;
    }

    @XmlTransient
    public Collection<CommodityType> getCommodityTypeCollection() {
        return commodityTypeCollection;
    }

    public void setCommodityTypeCollection(Collection<CommodityType> commodityTypeCollection) {
        this.commodityTypeCollection = commodityTypeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typeId != null ? typeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Types)) {
            return false;
        }
        Types other = (Types) object;
        if ((this.typeId == null && other.typeId != null) || (this.typeId != null && !this.typeId.equals(other.typeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fit5192.stu29184517.repository.entities.Types[ typeId=" + typeId + " ]";
    }
    
}
