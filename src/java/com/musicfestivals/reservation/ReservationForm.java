package com.musicfestivals.reservation;

import com.musicfestivals.app.AuthorizationBean;
import com.musicfestivals.app.JSFParamGetter;
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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

@ManagedBean(name = "reservationForm")
@ViewScoped
public class ReservationForm {

    private Festival festival;
    private final DataQuery query = new DataQuery();
    private int durationFlag;
    private String selectedItem;

    @PostConstruct
    public void init() {
        try {

            FacesContext fc = FacesContext.getCurrentInstance();
            JSFParamGetter paramGetter = new JSFParamGetter(fc);
            long dataId = paramGetter.getLongParametar("dataId");
            if (dataId > 0) {
                setFestival(query.getEntityManager().createNamedQuery("Festival.findById", Festival.class).setParameter("id", dataId).getSingleResult());
            } else {
                setFestival(new Festival());
            }
            System.out.println("Data id=" + dataId);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Reservation reservation = new Reservation();
        long up = AuthorizationBean.getLoggedInUser().getId();
        reservation.setUserId(up);
        reservation.setFestivalId(getFestival().getId());
        reservation.setBought(false);
        if ("full".equals(getSelectedItem())) {
            reservation.setDurationTime(duration());
        } else {
            reservation.setDurationTime(1);
        }

        query.getEntityManager().persist(reservation);
        query.getEntityManager().getTransaction().commit();
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public int getDurationFlag() {
        return durationFlag;
    }

    public void setDurationFlag(int durationFlag) {
        this.durationFlag = durationFlag;
    }

    public SelectItem[] getSelectedItems() {
        SelectItem[] ret = new SelectItem[2];
        ret[0] = new SelectItem("one", "One");
        ret[1] = new SelectItem("full", "Full");
        return ret;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }
}
