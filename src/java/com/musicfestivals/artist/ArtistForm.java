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
public class ArtistForm  implements Serializable {
    private Artist artist;
    long dataId;
    private final DataQuery query = new DataQuery();
    
    @PostConstruct
    public void init() {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            JSFParamGetter paramGeter = new JSFParamGetter(fc);
            dataId = paramGeter.getLongParametar("dataId");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void add() {
        Artist a = new Artist();
        a.setArtistName(artist.getArtistName());
        a.setFestivalId(dataId);
        a.setPerformanceDate(artist.getPerformanceDate());
        a.setPerformanceTimeStart(artist.getPerformanceTimeStart());
        a.setPerformanceTimeEnd(artist.getPerformanceTimeEnd());

        query.getEntityManager().persist(a);
        query.getEntityManager().getTransaction().commit();
    }
    
    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
