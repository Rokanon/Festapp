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
    private String selectedItem;
    private Map<String, String> availableItems; 
    
//    public SelectItem[] dur() {
////        availableItems = new LinkedHashMap<int, String>();
////        getAvailableItems().put("0", "One day");
////        getAvailableItems().put("1", "Whole festival");
//        SelectItem[] select = new SelectItem[2];
//            select[0] = new SelectItem("0", "One day");
//            select[1] = new SelectItem("1", "Whole festival");
//        
//        return select;
//    }
    public ReservationForm() {
        availableItems = new LinkedHashMap<String, String>();
        getAvailableItems().put("one", "One day");
        getAvailableItems().put("full", "Whole festival");
    }

    
    
    @PostConstruct
    public void init() {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            dataId = Long.parseLong(fc.getExternalContext().getRequestParameterMap().get("dataId"));
            if (dataId > 0) {
                setFestival(query.getEntityManager().createNamedQuery("Festival.findById", Festival.class).setParameter("id", dataId).getSingleResult());
            }
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
//        Calendar cal1 = new GregorianCalendar();
//        Calendar cal2 = new GregorianCalendar();
//
//        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
//
//        cal1.setTime(begin);
//        cal2.setTime(end);

        return daysBetween(begin, end);
    }

    public void reserveTicket() {
        String selectedLabel = getAvailableItems().get(getSelectedItem());
        long up = AuthorizationBean.getLoggedInUser().getId();
        System.out.println("id usera" + up);
        reservation.setUserId(up);
        reservation.setFestivalId(pera);
        reservation.setBought(false);
        if ("full".equals(selectedLabel)) {
            reservation.setDurationTime(duration());
        } else {
            reservation.setDurationTime(1);
        }

        query.getEntityManager().persist(reservation);
        query.getEntityManager().getTransaction().commit();
    }

    public Festival getFestival() {
//        festival = query.getEntityManager().createNamedQuery("Festival.findById", Festival.class).setParameter("id", dataId).getSingleResult();
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

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public Map<String, String> getAvailableItems() {
        return availableItems;
    }
}
