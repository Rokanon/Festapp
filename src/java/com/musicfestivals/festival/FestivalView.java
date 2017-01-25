/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicfestivals.festival;

import com.musicfestivals.app.JSFParamGetter;
import com.musicfestivals.query.DataQuery;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean (name = "festivalForm")
@ViewScoped
public class FestivalView {
    private Festival festival;
    private final DataQuery query = new DataQuery();
    
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
    
    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }
}
