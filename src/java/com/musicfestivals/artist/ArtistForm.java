package com.musicfestivals.artist;

import com.musicfestivals.app.JSFParamGetter;
import com.musicfestivals.query.DataQuery;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "artistForm")
@ViewScoped
public class ArtistForm implements Serializable {

    private Artist artist;
    private long festivalId;
    private final DataQuery query = new DataQuery();

    @PostConstruct
    public void init() {
        artist = new Artist();
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            JSFParamGetter paramGeter = new JSFParamGetter(fc);
            setFestivalId(paramGeter.getLongParametar("dataId"));
            System.out.println("Festival id as data " + getFestivalId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add() {
        getArtist().setFestivalId(getFestivalId());
        transactionCheck();
        query.getEntityManager().persist(getArtist());
        query.getEntityManager().getTransaction().commit();
        setArtist(new Artist());
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public long getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(long festivalId) {
        this.festivalId = festivalId;
    }

    private void transactionCheck() {
        if (!query.getEntityManager().getTransaction().isActive()) {
            query.getEntityManager().getTransaction().begin();
        }
    }

}
