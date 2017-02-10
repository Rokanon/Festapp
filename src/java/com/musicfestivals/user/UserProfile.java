/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicfestivals.user;

import com.musicfestivals.query.DataQuery;
import com.musicfestivals.reservation.Reservation;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "user_profile")
@NamedQueries({
    @NamedQuery(name = "UserProfile.findAll", query = "SELECT u FROM UserProfile u order by u.verified ASC")
    , @NamedQuery(name = "UserProfile.control", query = "SELECT u FROM UserProfile u WHERE u.username = :username AND u.password=:password AND u.verified=1")
    , @NamedQuery(name = "UserProfile.findById", query = "SELECT u FROM UserProfile u WHERE u.id = :id")
    , @NamedQuery(name = "UserProfile.findByFirstName", query = "SELECT u FROM UserProfile u WHERE u.firstName = :firstName")
    , @NamedQuery(name = "UserProfile.findByLastName", query = "SELECT u FROM UserProfile u WHERE u.lastName = :lastName")
    , @NamedQuery(name = "UserProfile.findByUsername", query = "SELECT u FROM UserProfile u WHERE u.username = :username")
    , @NamedQuery(name = "UserProfile.findByPassword", query = "SELECT u FROM UserProfile u WHERE u.password = :password")
    , @NamedQuery(name = "UserProfile.findByPhone", query = "SELECT u FROM UserProfile u WHERE u.phone = :phone")
    , @NamedQuery(name = "UserProfile.findByEmail", query = "SELECT u FROM UserProfile u WHERE u.email = :email")
    , @NamedQuery(name = "UserProfile.findByKind", query = "SELECT u FROM UserProfile u WHERE u.kind = :kind")
    , @NamedQuery(name = "UserProfile.findByVerified", query = "SELECT u FROM UserProfile u WHERE u.verified = :verified")})
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "kind")
    private Short kind;
    @Column(name = "verified")
    private Short verified;
    @Column(name = "blocked")
    private Short blocked;
    @Column(name = "times_not_bought_reserved_ticket")
    private Short timesNotBoughtReservedTicket;

    public UserProfile() {
        firstName = "";
        lastName = "";
        username = "";
        password = "";
        phone = "";
        email = "";
        id = 0l;
        kind = -1;
        verified = 0;
        blocked=0;
        timesNotBoughtReservedTicket = 0;
    }

    public UserProfile(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Short getKind() {
        return kind;
    }

    public void setKind(Short kind) {
        this.kind = kind;
    }

    public Short getVerified() {
        return verified;
    }

    public void setVerified(Short verified) {
        this.verified = verified;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserProfile)) {
            return false;
        }
        UserProfile other = (UserProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.musicfestivals.user.UserProfile[ id=" + id + " ]";
    }

    public String getKindToString() {
        if (null == getKind()) {
            return "Unknown";
        } else {
            switch (getKind()) {
                case 1:
                    return "Admin";
                case 0:
                    return "Member";
                default:
                    return "Unknown";
            }
        }
    }

    public Short getBlocked() {
        return blocked;
    }

    public void setBlocked(Short blocked) {
        this.blocked = blocked;
    }

    public Short getTimesNotBoughtReservedTicket() {
        return timesNotBoughtReservedTicket;
    }

    public void setTimesNotBoughtReservedTicket(Short timesNotBoughtReservedTicket) {
        this.timesNotBoughtReservedTicket = timesNotBoughtReservedTicket;
    }
}
