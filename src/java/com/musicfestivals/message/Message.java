/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicfestivals.message;

import com.musicfestivals.festival.Festival;
import com.musicfestivals.query.DataQuery;
import com.musicfestivals.user.UserProfile;
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
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m")
    , @NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id")
    , @NamedQuery(name = "Message.findByUserId", query = "SELECT m FROM Message m WHERE m.userId = :userId")
    , @NamedQuery(name = "Message.findByFestivalId", query = "SELECT m FROM Message m WHERE m.festivalId = :festivalId")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "festival_id")
    private Long festivalId;

    public Message() {
    }

    public Message(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(Long festivalId) {
        this.festivalId = festivalId;
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
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.musicfestivals.message.Message[ id=" + id + " ]";
    }
    
    public String getUsername(){
        String username = "";
        if (userId > 0) {
             DataQuery query = new DataQuery();
             UserProfile up = query.getEntityManager().createNamedQuery("UserProfile.findById", UserProfile.class).setParameter("id", userId).getSingleResult();
             if (up != null) {
                 if (up.getUsername() != null){
                     username = up.getUsername();
                 }
             }
        }
        return username;
    }
    
    public String getFestivalName(){
        String festivalName = "";
        if (festivalId > 0) {
             DataQuery query = new DataQuery();
             Festival fest = query.getEntityManager().createNamedQuery("Festival.findById", Festival.class).setParameter("id", festivalId).getSingleResult();
             if (fest != null) {
                 if (fest.getTitle()!= null){
                     festivalName = fest.getTitle();
                 }
             }
        }
        return festivalName;
    }
}
