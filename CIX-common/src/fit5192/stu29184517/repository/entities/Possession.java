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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luzhe
 */
@Entity
@Table(name = "POSSESSION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Possession.findAll", query = "SELECT p FROM Possession p")
    , @NamedQuery(name = "Possession.findByPossessionId", query = "SELECT p FROM Possession p WHERE p.possessionId = :possessionId")
    , @NamedQuery(name = "Possession.findByProviderId", query = "SELECT p FROM Possession p WHERE p.providerId = :providerId")
    , @NamedQuery(name = "Possession.findByQuantity", query = "SELECT p FROM Possession p WHERE p.quantity = :quantity")
    , @NamedQuery(name = "Possession.findByExecuteStatus", query = "SELECT p FROM Possession p WHERE p.executeStatus = :executeStatus")})
public class Possession implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "POSSESSION_ID")
    private Integer possessionId;
    @Column(name = "PROVIDER_ID")
    private Integer providerId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXECUTE_STATUS")
    private int executeStatus;
    @JoinColumn(name = "COMMODITY_ID", referencedColumnName = "COMMODITY_ID")
    @ManyToOne(optional = false)
    private Commodity commodityId;
    @JoinColumn(name = "OWNER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private Users ownerId;

    public Possession() {
    }

    public Possession(Integer possessionId) {
        this.possessionId = possessionId;
    }

    public Possession(Integer possessionId, int quantity, int executeStatus) {
        this.possessionId = possessionId;
        this.quantity = quantity;
        this.executeStatus = executeStatus;
    }

    public Integer getPossessionId() {
        return possessionId;
    }

    public void setPossessionId(Integer possessionId) {
        this.possessionId = possessionId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(int executeStatus) {
        this.executeStatus = executeStatus;
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
        hash += (possessionId != null ? possessionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Possession)) {
            return false;
        }
        Possession other = (Possession) object;
        if ((this.possessionId == null && other.possessionId != null) || (this.possessionId != null && !this.possessionId.equals(other.possessionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fit5192.stu29184517.repository.entities.Possession[ possessionId=" + possessionId + " ]";
    }
    
}
