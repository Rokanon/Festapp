package com.musicfestivals.artist;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Time;
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
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "artist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artist.findAll", query = "SELECT a FROM Artist a")
    , @NamedQuery(name = "Artist.findById", query = "SELECT a FROM Artist a WHERE a.id = :id")
    , @NamedQuery(name = "Artist.findByFestivalId", query = "SELECT a FROM Artist a WHERE a.festivalId = :festivalId")
    , @NamedQuery(name = "Artist.findByArtistName", query = "SELECT a FROM Artist a WHERE a.artistName = :artistName")
    , @NamedQuery(name = "Artist.findByPerformanceDate", query = "SELECT a FROM Artist a WHERE a.performanceDate = :performanceDate")
    , @NamedQuery(name = "Artist.findByPerformanceTimeStart", query = "SELECT a FROM Artist a WHERE a.performanceTimeStart = :performanceTimeStart")
    , @NamedQuery(name = "Artist.findByPerformanceTimeEnd", query = "SELECT a FROM Artist a WHERE a.performanceTimeEnd = :performanceTimeEnd")})
public class Artist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "festival_id")
    private Long festivalId;
    @Column(name = "artist_name")
    private String artistName;
    @Basic(optional = false)
    @Column(name = "performance_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date performanceDate;
    @Column(name = "performance_time_start")
    private Time performanceTimeStart;
    @Column(name = "performance_time_end")
    private Time performanceTimeEnd;

    public Artist() {
    }

    public Artist(Long id) {
        this.id = id;
    }

    public Artist(Long id, Date performanceDate) {
        this.id = id;
        this.performanceDate = performanceDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(Long festivalId) {
        this.festivalId = festivalId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Date getPerformanceDate() {
        return performanceDate;
    }

    public void setPerformanceDate(Date performanceDate) {
        this.performanceDate = performanceDate;
    }

    public Time getPerformanceTimeStart() {
        return performanceTimeStart;
    }

    public void setPerformanceTimeStart(Time performanceTimeStart) {
        this.performanceTimeStart = performanceTimeStart;
    }

    public Time getPerformanceTimeEnd() {
        return performanceTimeEnd;
    }

    public void setPerformanceTimeEnd(Time performanceTimeEnd) {
        this.performanceTimeEnd = performanceTimeEnd;
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
        if (!(object instanceof Artist)) {
            return false;
        }
        Artist other = (Artist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.musicfestivals.artist.Artist[ id=" + id + " ]";
    }
    
}
