package com.musicfestivals.festival.day;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "festival_day")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FestivalDay.findAll", query = "SELECT f FROM FestivalDay f")
    , @NamedQuery(name = "FestivalDay.findById", query = "SELECT f FROM FestivalDay f WHERE f.id = :id")
    , @NamedQuery(name = "FestivalDay.findByFestivalId", query = "SELECT f FROM FestivalDay f WHERE f.festivalId = :festivalId")
    , @NamedQuery(name = "FestivalDay.findByDateOfADay", query = "SELECT f FROM FestivalDay f WHERE f.dateOfADay = :dateOfADay")
    , @NamedQuery(name = "FestivalDay.findByNumberOfTickets", query = "SELECT f FROM FestivalDay f WHERE f.numberOfTickets = :numberOfTickets")})
public class FestivalDay implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "festival_id")
    private BigInteger festivalId;
    @Basic(optional = false)
    @Column(name = "date_of_a_day")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfADay;
    @Column(name = "number_of_tickets")
    private Integer numberOfTickets;

    public FestivalDay() {
    }

    public FestivalDay(Long id) {
        this.id = id;
    }

    public FestivalDay(Long id, Date dateOfADay) {
        this.id = id;
        this.dateOfADay = dateOfADay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(BigInteger festivalId) {
        this.festivalId = festivalId;
    }

    public Date getDateOfADay() {
        return dateOfADay;
    }

    public void setDateOfADay(Date dateOfADay) {
        this.dateOfADay = dateOfADay;
    }

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
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
        if (!(object instanceof FestivalDay)) {
            return false;
        }
        FestivalDay other = (FestivalDay) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.musicfestivals.festival.day.FestivalDay[ id=" + id + " ]";
    }
    
}
