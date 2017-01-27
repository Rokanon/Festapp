package com.musicfestivals.reservation;

import com.musicfestivals.app.AuthorizationBean;
import com.musicfestivals.festival.Festival;
import com.musicfestivals.query.DataQuery;
import com.musicfestivals.user.UserProfile;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "reservationList")
@ViewScoped
public class ReservationList implements Serializable {
    
    private final DataQuery query = new DataQuery();
    
    private List<Reservation> reservationList;
    private List<Reservation> reservationListUser;
    
    long userId = AuthorizationBean.getLoggedInUser().getId();
    
    public List<Reservation> loadList() {
        List<Reservation> list = null;
        list = query.getEntityManager().createNamedQuery("Reservation.findByBought", Reservation.class).setParameter("bought", false).getResultList();
        return list;
    }
    
    public List<Reservation> loadListUser() {
        List<Reservation> list = null;
        list = query.getEntityManager().createNamedQuery("Reservation.findByUserId", Reservation.class).setParameter("userId", userId).getResultList();
        return list;
    }
    
    public void accept(long id) {
        transactionCheck();
        Reservation res = query.getEntityManager().createNamedQuery("Reservation.findById", Reservation.class).setParameter("id", id).getSingleResult();
        if (res != null) {
            res.setBought(true);
            query.getEntityManager().getTransaction().commit();
        }
    }

    public void reject(long id) {
        transactionCheck(); 
        Reservation res = query.getEntityManager().createNamedQuery("Reservation.findById", Reservation.class).setParameter("id", id).getSingleResult();
        if (res != null) {
            query.getEntityManager().remove(res);
            query.getEntityManager().getTransaction().commit();
            getReservationList().remove(res);
        }
    }
    
    public void rejectTicket(long id) {
        transactionCheck(); 
        Reservation res = query.getEntityManager().createNamedQuery("Reservation.findById", Reservation.class).setParameter("id", id).getSingleResult();
        if (res != null) {
            query.getEntityManager().remove(res);
            query.getEntityManager().getTransaction().commit();
            getReservationListUser().remove(res);
        }
    }
    
    private void transactionCheck() {
        if (!query.getEntityManager().getTransaction().isActive()) {
            query.getEntityManager().getTransaction().begin();
        }
    }

    public List<Reservation> getReservationList() {
        if (reservationList == null) {
            reservationList = loadList();
        }
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public List<Reservation> getReservationListUser() {
        if (reservationListUser == null) {
            reservationListUser = loadListUser();
        }
        return reservationListUser;
    }

    public void setReservationListUser(List<Reservation> reservationListUser) {
        this.reservationListUser = reservationListUser;
    }
    
    public String festName(long id) {
        Festival f = query.getEntityManager().createNamedQuery("Festival.findById", Festival.class).setParameter("id", id).getSingleResult();
        return f.getTitle();
    }
    
    public String userName(long id) {
        UserProfile up = query.getEntityManager().createNamedQuery("UserProfile.findById", UserProfile.class).setParameter("id", id).getSingleResult();
        return up.getFirstName() + " " + up.getLastName();
    }
}
