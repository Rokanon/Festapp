package com.musicfestivals.reservation;

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
@Table(name = "reservation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r")
    , @NamedQuery(name = "Reservation.findById", query = "SELECT r FROM Reservation r WHERE r.id = :id")
    , @NamedQuery(name = "Reservation.findByFestivalId", query = "SELECT r FROM Reservation r WHERE r.festivalId = :festivalId")
    , @NamedQuery(name = "Reservation.findByUserId", query = "SELECT r FROM Reservation r WHERE r.userId = :userId")
    , @NamedQuery(name = "Reservation.findByTimeOfReservation", query = "SELECT r FROM Reservation r WHERE r.timeOfReservation = :timeOfReservation")
    , @NamedQuery(name = "Reservation.findByDurationTime", query = "SELECT r FROM Reservation r WHERE r.durationTime = :durationTime")
    , @NamedQuery(name = "Reservation.findByBought", query = "SELECT r FROM Reservation r WHERE r.bought = :bought")})
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "festival_id")
    private BigInteger festivalId;
    @Column(name = "user_id")
    private BigInteger userId;
    @Basic(optional = false)
    @Column(name = "time_of_reservation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOfReservation;
    @Column(name = "duration_time")
    private Integer durationTime;
    @Column(name = "bought")
    private Boolean bought;

    public Reservation() {
    }

    public Reservation(Long id) {
        this.id = id;
    }

    public Reservation(Long id, Date timeOfReservation) {
        this.id = id;
        this.timeOfReservation = timeOfReservation;
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

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public Date getTimeOfReservation() {
        return timeOfReservation;
    }

    public void setTimeOfReservation(Date timeOfReservation) {
        this.timeOfReservation = timeOfReservation;
    }

    public Integer getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Integer durationTime) {
        this.durationTime = durationTime;
    }

    public Boolean getBought() {
        return bought;
    }

    public void setBought(Boolean bought) {
        this.bought = bought;
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
        if (!(object instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.musicfestivals.reservation.Reservation[ id=" + id + " ]";
    }
    
}
