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
    
    public List<Reservation> loadList() {
        List<Reservation> list = null;
        list = query.getEntityManager().createNamedQuery("Reservation.findByBought", Reservation.class).setParameter("bought", false).getResultList();
        return list;
    }
}
