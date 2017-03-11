/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicfestivals.message;

import com.musicfestivals.app.JSFParamGetter;
import com.musicfestivals.festival.Festival;
import com.musicfestivals.query.DataQuery;
import com.musicfestivals.reservation.Reservation;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "messageForm")
@ViewScoped
public class MessageForm implements Serializable {

    private List<Reservation> reservations;
    private final DataQuery query = new DataQuery();

    public void cancelFestivalAndSendMessages(long festivalId) {
        try {
            if (festivalId == 0) {
                return;
            } else {       
                System.out.println("Festival id " + festivalId) ;
                reservations = query.getEntityManager().createNamedQuery("Reservation.findByFestivalIdBought", Reservation.class).setParameter("festivalId", festivalId).getResultList();
                for (Reservation reservation : reservations) {
                    Message message = new Message();
                    message.setFestivalId(festivalId);
                    message.setUserId(reservation.getUserId());
                    transactionCheck();
                    query.getEntityManager().persist(message);
                    query.getEntityManager().getTransaction().commit();
                }
            }
            System.out.println("Festival id as data ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    private void transactionCheck() {
        if (!query.getEntityManager().getTransaction().isActive()) {
            query.getEntityManager().getTransaction().begin();
        }
    }
}
