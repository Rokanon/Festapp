/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicfestivals.festival;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "festival")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Festival.findAll", query = "SELECT f FROM Festival f WHERE f.endDate >= CURRENT_TIMESTAMP")
    , @NamedQuery(name = "Festival.findById", query = "SELECT f FROM Festival f WHERE f.id = :id")
    , @NamedQuery(name = "Festival.findByTimesSeen", query = "SELECT f FROM Festival f WHERE f.timesSeen= :timesSeen and f.endDate >= CURRENT_TIMESTAMP")
    , @NamedQuery(name = "Festival.findByTicketsSold", query = "SELECT f FROM Festival f WHERE f.ticketsSold = :ticketsSold and f.endDate >= CURRENT_TIMESTAMP")
    , @NamedQuery(name = "Festival.topFiveBySoldTickets", query = "SELECT f FROM Festival f where f.endDate >= CURRENT_TIMESTAMP ORDER BY f.ticketsSold desc")
    , @NamedQuery(name = "Festival.upcoming", query = "SELECT f FROM Festival f where f.endDate >= CURRENT_TIMESTAMP ORDER BY f.endDate ASC")
    , @NamedQuery(name = "Festival.topFiveByTimesSeen", query = "SELECT f FROM Festival f where f.endDate >= CURRENT_TIMESTAMP ORDER BY f.timesSeen desc")
    , @NamedQuery(name = "Festival.findByTitle", query = "SELECT f FROM Festival f WHERE f.title = :title and f.endDate >= CURRENT_TIMESTAMP")
    , @NamedQuery(name = "Festival.findByGenre", query = "SELECT f FROM Festival f WHERE f.genre = :genre and f.endDate >= CURRENT_TIMESTAMP")
    , @NamedQuery(name = "Festival.findByBeginDate", query = "SELECT f FROM Festival f WHERE f.beginDate = :beginDate and f.endDate >= CURRENT_TIMESTAMP")
    , @NamedQuery(name = "Festival.findByEndDate", query = "SELECT f FROM Festival f WHERE f.endDate = :endDate and f.endDate >= CURRENT_TIMESTAMP")})
public class Festival implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "tickets_sold")
    private Long ticketsSold;
    @Column(name = "times_seen")
    private Long timesSeen;
    @Column(name = "title")
    private String title;
    @Column(name = "genre")
    private String genre;
    @Basic(optional = false)
    @Column(name = "begin_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginDate;
    @Basic(optional = false)
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    public Festival() {
    }

    public Festival(Long id) {
        this.id = id;
    }

    public Festival(Long id, Date beginDate, Date endDate) {
        this.id = id;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
        if (!(object instanceof Festival)) {
            return false;
        }
        Festival other = (Festival) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.musicfestivals.festival.Festival[ id=" + id + " ]";
    }

    public Long getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(Long ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public Long getTimesSeen() {
        return timesSeen;
    }

    public void setTimesSeen(Long timesSeen) {
        this.timesSeen = timesSeen;
    }
    
}
