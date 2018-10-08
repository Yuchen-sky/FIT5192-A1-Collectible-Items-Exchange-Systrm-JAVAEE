/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5192.stu29184517.repository.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "USERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")
    , @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId")
    , @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email")
    , @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password")
    , @NamedQuery(name = "Users.findByMembershipLevel", query = "SELECT u FROM Users u WHERE u.membershipLevel = :membershipLevel")
    , @NamedQuery(name = "Users.findByLastName", query = "SELECT u FROM Users u WHERE u.lastName = :lastName")
    , @NamedQuery(name = "Users.findByFirstName", query = "SELECT u FROM Users u WHERE u.firstName = :firstName")
    , @NamedQuery(name = "Users.findByAddress", query = "SELECT u FROM Users u WHERE u.address = :address")
    , @NamedQuery(name = "Users.findByPhoneNumber", query = "SELECT u FROM Users u WHERE u.phoneNumber = :phoneNumber")
    , @NamedQuery(name = "Users.findByCredits", query = "SELECT u FROM Users u WHERE u.credits = :credits")
    , @NamedQuery(name = "Users.findByTradingStatus", query = "SELECT u FROM Users u WHERE u.tradingStatus = :tradingStatus")
    , @NamedQuery(name = "Users.findByOrderNum", query = "SELECT u FROM Users u WHERE u.orderNum = :orderNum")})
public class Users implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerId")
    private Collection<Possession> possessionCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USER_ID")
    private Integer userId;
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @Column(name = "MEMBERSHIP_LEVEL")
    private short membershipLevel;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "PHONE_NUMBER")
    private Integer phoneNumber;
    @Basic(optional = false)
    @Column(name = "CREDITS")
    private double credits;
    @Basic(optional = false)
    @Column(name = "TRADING_STATUS")
    private short tradingStatus;
    @Basic(optional = false)
    @Column(name = "ORDER_NUM")
    private int orderNum;
    @OneToMany(mappedBy = "ownerId")
    private Collection<SaleOrder> saleOrderCollection;
    @OneToMany(mappedBy = "ownerId")
    private Collection<BuyOrder> buyOrderCollection;
    @OneToMany(mappedBy = "executeUser")
    private Collection<Exchange> exchangeCollection;

    public Users() {
    }

    public Users(Integer userId) {
        this.userId = userId;
    }

    public Users(Integer userId, String password, short membershipLevel, double credits, short tradingStatus, int orderNum) {
        this.userId = userId;
        this.password = password;
        this.membershipLevel = membershipLevel;
        this.credits = credits;
        this.tradingStatus = tradingStatus;
        this.orderNum = orderNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public short getMembershipLevel() {
        return membershipLevel;
    }

    public void setMembershipLevel(short membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public short getTradingStatus() {
        return tradingStatus;
    }

    public void setTradingStatus(short tradingStatus) {
        this.tradingStatus = tradingStatus;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
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
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fit5192.stu29184517.repository.entities.Users[ userId=" + userId + " ]";
    }

    @XmlTransient
    public Collection<Possession> getPossessionCollection() {
        return possessionCollection;
    }

    public void setPossessionCollection(Collection<Possession> possessionCollection) {
        this.possessionCollection = possessionCollection;
    }
    
}
