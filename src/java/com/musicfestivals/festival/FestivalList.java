package com.musicfestivals.festival;

import com.musicfestivals.query.DataQuery;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

@ManagedBean(name = "festivalList")
@RequestScoped
public class FestivalList implements Serializable {

    private List<Festival> festList;
    private final DataQuery query = new DataQuery();
    private boolean filterSet = false;
    private FestivalFilter filter;

    public List<Festival> loadList() {
        List<Festival> list = null;
        if (!filterSet) {
            list = query.getEntityManager().createNamedQuery("Festival.findAll").getResultList();
        } else {
            CriteriaBuilder cb = query.getEntityManager().getCriteriaBuilder();
            CriteriaQuery q = cb.createQuery(Festival.class);
            Root<Festival> c = q.from(Festival.class);
            q.select(c);
            ParameterExpression<String> title = cb.parameter(String.class);
            ParameterExpression<Date> begin_date = cb.parameter(Date.class);
            ParameterExpression<Date> end_date = cb.parameter(Date.class);
            ParameterExpression<String> place = cb.parameter(String.class);
//            ParameterExpression<String> artist = cb.parameter(String.class);

            q.where(
//                    cb.and(
                            cb.like(c.get("title"), "%" + filter.getTitle() + "%")
//                            cb.like(c.get("place"), "%" + filter.getPlace() + "%")
//                    )
            );
            list = query.getEntityManager().createQuery(q).getResultList();
            for (int i = 0; i < list.size(); i++) {
                System.out.println("List Size: " + list.size() + " List title: " + list.get(i).getTitle());
            }
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

    public List<Festival> getTopFiveSeenAdmin() {
        List<Festival> list;
        System.out.println("hello from load top 5");
        list = query.getEntityManager().createNamedQuery("Festival.topFiveByTimesSeen", Festival.class).setMaxResults(5).getResultList();
        return list;
    }

    public List<Festival> getTopFiveTicketSoldAdmin() {
        List<Festival> list;
        list = query.getEntityManager().createNamedQuery("Festival.topFiveBySoldTickets", Festival.class).setMaxResults(5).getResultList();
        return list;
    }

    public List<Festival> getLastFiveUpcoming() {
        List<Festival> list;
        list = query.getEntityManager().createNamedQuery("Festival.upcoming", Festival.class).setMaxResults(5).getResultList();
        return list;
    }
}
