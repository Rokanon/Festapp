package com.musicfestivals.reservation;

import com.musicfestivals.festival.Festival;
import com.musicfestivals.query.DataQuery;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "reservationForm")
@RequestScoped
public class ReservationForm {

    private Festival festival;
    private DataQuery query = new DataQuery();
    private long dataId;
    
    
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
    
    public Festival getFestival() {
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
}
