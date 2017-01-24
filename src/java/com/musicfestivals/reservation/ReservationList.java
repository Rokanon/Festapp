package com.musicfestivals.reservation;

import com.musicfestivals.query.DataQuery;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "reservationList")
@RequestScoped
public class ReservationList implements Serializable {
    
    private final DataQuery query = new DataQuery();
    
    private List<Reservation> reservationList;
    
    public List<Reservation> loadList() {
        List<Reservation> list = null;
        list = query.getEntityManager().createNamedQuery("Reservation.findByBought", Reservation.class).setParameter("bought", false).getResultList();
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
}
