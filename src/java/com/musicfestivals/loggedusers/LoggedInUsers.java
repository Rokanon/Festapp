/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicfestivals.loggedusers;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "logged_in_users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LoggedInUsers.findAll", query = "SELECT l FROM LoggedInUsers l ORDER BY l.id desc")
    , @NamedQuery(name = "LoggedInUsers.findById", query = "SELECT l FROM LoggedInUsers l WHERE l.id = :id")
    , @NamedQuery(name = "LoggedInUsers.findByUserName", query = "SELECT l FROM LoggedInUsers l WHERE l.userName = :userName")
    , @NamedQuery(name = "LoggedInUsers.findByUserId", query = "SELECT l FROM LoggedInUsers l WHERE l.userId = :userId")})
public class LoggedInUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_id")
    private long userId;

    public LoggedInUsers() {
    }

    public LoggedInUsers(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoggedInUsers)) {
            return false;
        }
        LoggedInUsers other = (LoggedInUsers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.musicfestivals.loggedusers.LoggedInUsers[ id=" + id + " ]";
    }
    
}
