/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicfestivals.social;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author magic
 */
@Entity
@Table(name = "social_networks")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SocialNetworks.findAll", query = "SELECT s FROM SocialNetworks s")
    , @NamedQuery(name = "SocialNetworks.findByFestivalId", query = "SELECT s FROM SocialNetworks s WHERE s.festivalId = :festivalId")
    , @NamedQuery(name = "SocialNetworks.findByFacebook", query = "SELECT s FROM SocialNetworks s WHERE s.facebook = :facebook")
    , @NamedQuery(name = "SocialNetworks.findByTwitter", query = "SELECT s FROM SocialNetworks s WHERE s.twitter = :twitter")
    , @NamedQuery(name = "SocialNetworks.findByYoutube", query = "SELECT s FROM SocialNetworks s WHERE s.youtube = :youtube")})
public class SocialNetworks implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "festival_id")
    private Long festivalId;
    @Column(name = "facebook")
    private String facebook;
    @Column(name = "twitter")
    private String twitter;
    @Column(name = "youtube")
    private String youtube;

    public SocialNetworks() {
    }

    public SocialNetworks(Long festivalId) {
        this.festivalId = festivalId;
    }

    public Long getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(Long festivalId) {
        this.festivalId = festivalId;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (festivalId != null ? festivalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SocialNetworks)) {
            return false;
        }
        SocialNetworks other = (SocialNetworks) object;
        if ((this.festivalId == null && other.festivalId != null) || (this.festivalId != null && !this.festivalId.equals(other.festivalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.musicfestivals.social.SocialNetworks[ festivalId=" + festivalId + " ]";
    }
    
}
