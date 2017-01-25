package com.musicfestivals.festival;

import com.musicfestivals.app.AuthorizationBean;
import com.musicfestivals.app.PageNavigation;
import com.musicfestivals.img.Image;
import com.musicfestivals.query.DataQuery;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import com.musicfestivals.app.JSFParamGetter;
import com.musicfestivals.artist.Artist;
import com.musicfestivals.festival.day.FestivalDay;
import com.musicfestivals.reservation.Reservation;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "festivalForm")
@ViewScoped
public class FestivalForm implements Serializable {

    private List<Artist> artists;
    private List<FestivalDay> days;
    private List<Image> images;
    private Festival festival;
    private final DataQuery query = new DataQuery();
    private String back;
    private boolean newData = false;
    private Integer vote;

    @PostConstruct
    public void init() {
        vote = 0;
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            JSFParamGetter paramGeter = new JSFParamGetter(fc);
            long dataId = paramGeter.getLongParametar("dataId");
            if (dataId > 0) {
                setFestival(query.getEntityManager().createNamedQuery("Festival.findById", Festival.class).setParameter("id", dataId).getSingleResult());
            } else {
                setFestival(new Festival());
                newData = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void save() {
        if (newData) {
            getFestival().setTimesSeen(0l);
            getFestival().setTicketsSold(0l);
            query.getEntityManager().persist(getFestival());
        }
        transactionCheck();
        query.getEntityManager().getTransaction().commit();
//        goBack();
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public List<Image> getImages() {
        images = query.getEntityManager().createNamedQuery("Image.findByFestivalId", Image.class).setParameter("festivalId", getFestival().getId()).getResultList();
        return images == null ? new ArrayList<>() : images;
    }

    public List<Artist> getArtists() {
        artists = query.getEntityManager().createNamedQuery("Artist.findByFestivalId", Artist.class).setParameter("festivalId", getFestival().getId()).getResultList();
        return artists != null ? artists : new ArrayList<>();
    }

    public List<FestivalDay> getDays() {
        days = query.getEntityManager().createNamedQuery("FestivalDay.findByFestivalId", FestivalDay.class).setParameter("festivalId", getFestival().getId()).getResultList();
        return days != null ? days : new ArrayList<>();
    }

    private void transactionCheck() {
        if (!query.getEntityManager().getTransaction().isActive()) {
            query.getEntityManager().getTransaction().begin();
        }
    }

    public void rate() {
        transactionCheck();
        double currentRate = getFestival().getRating();
        int timesRated = getFestival().getUsersRated();
        double totalRating = currentRate * timesRated;
        timesRated++;
        totalRating += getVote();
        double finalRating = totalRating / timesRated;
        getFestival().setUsersRated(timesRated);
        getFestival().setRating((double) Math.round(finalRating * 100d) / 100d);
        query.getEntityManager().getTransaction().commit();
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        System.out.println("Vote set to " + vote);
        this.vote = vote;
    }

    public boolean isHasBoughtTicket() {
        List<Reservation> list;        
        long userId = AuthorizationBean.getLoggedInUser().getId();
        long festivalId = getFestival().getId();
        list = query.getEntityManager().createNamedQuery("Reservation.findIfUserBoughtForFestival", Reservation.class).setParameter("userId", userId).setParameter("festivalId", festivalId).getResultList();
        return list == null;       
    }
}
