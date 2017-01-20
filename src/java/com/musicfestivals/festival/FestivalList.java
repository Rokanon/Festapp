package com.musicfestivals.festival;

import com.musicfestivals.query.DataQuery;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@ManagedBean(name = "festivalList")
@ViewScoped
public class FestivalList {

    private List<Festival> festList;
    private final DataQuery query = new DataQuery();
    private boolean filterSet = false;
    private FestivalFilter filter;

    public List<Festival> loadList() {
        List<Festival> list = null;
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager entitymanager = emfactory.createEntityManager();
        if (!filterSet) {
            list = query.getEntityManager().createNamedQuery("Festival.findAll").getResultList();
        } else {
            StringBuilder sb = new StringBuilder("Select f from Festival f where true ");
            
            if (filter.getTitle() != null) {
                sb.append(" and f.title like '%").append(filter.getTitle()).append("%'");
            }
//            if (filter.getBegin_date() ){
//                sb.append("and begin_date like '").append(filter.getBegin_date()).append("'");
//            }
//            if (filter.getEnd_date()!= null){
//                sb.append("and end_date like '").append(filter.getEnd_date()).append("'");
//            }
//            if (filter.getPlace()!= null){
//                sb.append("and place like '").append(filter.getPlace()).append("'");
//            }
//            if (filter.getArtist()!= null){
//                sb.append("and place like '").append(filter.getArtist()).append("'");
//            }
            Query query = entitymanager.createQuery(sb.toString());
            list = query.getResultList();
//            list = query.getEntityManager().crea("Festival.doFilter" + sb.toString(), Festival.class).getResultList();
        }
        return list;
    }

    public List<Festival> getFest() {
        if (festList == null) {
            festList = loadList();
        }
        return festList;
    }

    public void setFest(List<Festival> festList) {
        this.festList = festList;
    }

    public boolean isFilterSet() {
        return filterSet;
    }

    public void setFilterSet(boolean filterSet) {
        this.filterSet = filterSet;
        loadList();
    }

    public FestivalFilter getFilter() {
        if (filter == null) {
            filter = new FestivalFilter();
        }
        return filter;
    }

    public void setFilter(FestivalFilter filter) {
        this.filter = filter;
    }
}
