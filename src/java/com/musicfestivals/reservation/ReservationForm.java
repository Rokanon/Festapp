package com.musicfestivals.reservation;

import com.musicfestivals.app.AuthorizationBean;
import com.musicfestivals.festival.Festival;
import com.musicfestivals.query.DataQuery;
import com.musicfestivals.user.UserProfile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

@ManagedBean(name = "reservationForm")
@RequestScoped
public class ReservationForm {

    private Festival festival;
    private DataQuery query = new DataQuery();
    private long dataId;
    private Reservation reservation;
    private int durationFlag;
    long pera = 4;
    private SelectItem selectedItem;
    
    @PostConstruct
    public void init() {
//        try {
//            FacesContext fc = FacesContext.getCurrentInstance();
//            dataId = Long.parseLong(fc.getExternalContext().getRequestParameterMap().get("dataId"));
//            if (dataId > 0) {
//                setFestival(query.getEntityManager().createNamedQuery("Festival.findById", Festival.class).setParameter("id", dataId).getSingleResult());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public int duration() {
        Date end = festival.getEndDate();
        Date begin = festival.getBeginDate();
        return daysBetween(begin, end);
    }

    public void reserveTicket() {
        reservation = new Reservation();
        long up = AuthorizationBean.getLoggedInUser().getId();
        reservation.setUserId(up);
        System.out.println("res usr id " + reservation.getUserId());
        reservation.setFestivalId(festival.getId());
        System.out.println("res fes id " + reservation.getFestivalId());
        reservation.setBought(false);
        System.out.println("res bou " + reservation.getBought());
        if ("full".equals(selectedItem.getValue().toString())) {
            reservation.setDurationTime(duration());
        } else {
            reservation.setDurationTime(1);
        }
        System.out.println("res dur time" + reservation.getDurationTime());

        query.getEntityManager().persist(reservation);
        query.getEntityManager().getTransaction().commit();
    }

    public Festival getFestival() {
        FacesContext fc = FacesContext.getCurrentInstance();
        dataId = Long.parseLong(fc.getExternalContext().getRequestParameterMap().get("dataId"));
        festival = query.getEntityManager().createNamedQuery("Festival.findById", Festival.class).setParameter("id", dataId).getSingleResult();
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public long getDataId() {
        return dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public int getDurationFlag() {
        return durationFlag;
    }

    public void setDurationFlag(int durationFlag) {
        this.durationFlag = durationFlag;
    }

    public SelectItem getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(SelectItem selectedItem) {
        this.selectedItem = selectedItem;
    }
}
