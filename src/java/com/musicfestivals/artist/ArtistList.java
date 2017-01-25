package com.musicfestivals.artist;

import com.musicfestivals.app.JSFParamGetter;
import com.musicfestivals.query.DataQuery;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "artistList")
@RequestScoped
public class ArtistList implements Serializable {

    private List<Artist> artists;
    private final DataQuery query = new DataQuery();

    public List<Artist> loadList() {
        FacesContext fc = FacesContext.getCurrentInstance();
        JSFParamGetter paramGeter = new JSFParamGetter(fc);
        long dataId = paramGeter.getLongParametar("dataId");
        List<Artist> list = null;
        list = query.getEntityManager().createNamedQuery("Artist.findByFestivalId").setParameter("festivalId", dataId).getResultList();
        return list;
    }
    
    public List<Artist> getArtists() {
        if (artists == null) {
            artists = loadList();
        }
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
