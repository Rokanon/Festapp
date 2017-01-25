package com.musicfestivals.festival;

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
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "festivalForm")
@ViewScoped
public class FestivalForm implements Serializable {

    private List<Image> images;
    private Festival festival;
    private final DataQuery query = new DataQuery();
    private String back;

    @PostConstruct
    public void init() {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            JSFParamGetter paramGeter = new JSFParamGetter(fc);
            long dataId = paramGeter.getLongParametar("dataId");
            if (dataId > 0) {
                setFestival(query.getEntityManager().createNamedQuery("Festival.findById", Festival.class).setParameter("id", dataId).getSingleResult());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void save() {
        query.getEntityManager().getTransaction().commit();
        goBack();
    }

    public void cancel() {
        goBack();
    }

    public Festival getFestival() {
        if (festival == null) {
            festival = new Festival();
        }
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public List<Image> getImages() {
        images = query.getEntityManager().createNamedQuery("Image.findByFestivalId", Image.class).setParameter("festivalId", getFestival().getId()).getResultList();
        return images == null ? new ArrayList<>() : images;
    }

    private void goBack() {
        try {
            System.out.println("back: " + back);
            PageNavigation.goTo("");
        } catch (IOException ex) {
            Logger.getLogger(FestivalForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
