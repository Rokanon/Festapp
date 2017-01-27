package com.musicfestivals.festival;

import com.musicfestivals.artist.Artist;
import com.musicfestivals.query.DataQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@ManagedBean(name = "festivalList")
@RequestScoped
public class FestivalList implements Serializable {

    private List<Festival> festList;
    private final DataQuery query = new DataQuery();
    private final DataQuery query2 = new DataQuery();
    private boolean filterSet = false;
    private FestivalFilter filter;
    private List<Festival> unVerifiedList;

    public List<Festival> loadList() {
        List<Festival> list = null;
        if (!filterSet) {
            list = query.getEntityManager().createNamedQuery("Festival.findAllVerified").setParameter("verified", true).getResultList();
        } else {
            CriteriaBuilder cb = query.getEntityManager().getCriteriaBuilder();

            CriteriaQuery q = cb.createQuery(Festival.class);

            Root<Festival> c = q.from(Festival.class);
            String years;
            String months;
            String days;

            List<Predicate> predicates = new ArrayList<Predicate>();
            // search by fields, title, dates (both), place
            
            int indicator = 0;
            if (!"".equals(filter.getTitle())) {
                indicator++;
                predicates.add(
                        cb.like(c.get("title"), filter.getTitle() + "%"));
            }
            if (filter.getBeginDate() != null) {
                indicator++;
                Date datum = filter.getBeginDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(datum);
                int year = cal.get(Calendar.YEAR);
                years = "" + year;
                int month = cal.get(Calendar.MONTH);
                month = month + 1;
                if (month / 10 == 0) {
                    StringBuilder m = new StringBuilder("0");
                    m.append(month % 10);
                    months = m.toString();
                } else {
                    StringBuilder m = new StringBuilder();
                    m.append(month);
                    months = m.toString();
                }
                int day = cal.get(Calendar.DAY_OF_MONTH);
                day = day + 1;
                if (day / 10 == 0) {
                    StringBuilder m = new StringBuilder("0");
                    m.append(day % 10);
                    days = m.toString();
                } else {
                    StringBuilder m = new StringBuilder();
                    m.append(day);
                    days = m.toString();
                }
                predicates.add(
                        cb.like(c.get("beginDate"), years + "-" + months + "-" + days + "%"));
            }
            if (filter.getEndDate() != null) {
                indicator++;
                Date datum = filter.getEndDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(datum);
                int year = cal.get(Calendar.YEAR);
                years = "" + year;
                int month = cal.get(Calendar.MONTH);
                month = month + 1;
                if (month / 10 == 0) {
                    StringBuilder m = new StringBuilder("0");
                    m.append(month % 10);
                    months = m.toString();
                } else {
                    StringBuilder m = new StringBuilder();
                    m.append(month);
                    months = m.toString();
                }
                int day = cal.get(Calendar.DAY_OF_MONTH);
                day = day + 1;
                if (day / 10 == 0) {
                    StringBuilder m = new StringBuilder("0");
                    m.append(day % 10);
                    days = m.toString();
                } else {
                    StringBuilder m = new StringBuilder();
                    m.append(day);
                    days = m.toString();
                }
                predicates.add(
                        cb.like(c.get("endDate"), years + "-" + months + "-" + days + "%"));
            }
            if (!"".equals(filter.getPlace())) {
                indicator++;
                predicates.add(
                        cb.like(c.get("place"), filter.getPlace() + "%"));
            }
            if (!"".equals(filter.getArtist())) {
                indicator++;
                List<Long> list2 = null;
                list2 = query2.getEntityManager().createNamedQuery("Artist.findByArtistNameFestivalId").setParameter("artistName", filter.getArtist() + "%").getResultList();

                for (int i = 0; i < list2.size(); i++) {
                    System.out.println(i + " : " + list2.get(i));
                    predicates.add(
                            cb.or(cb.equal(c.get("id"), list2.get(i))));
                }
            }

            if (indicator > 0){
                predicates.add(
                        cb.equal(c.get("verified"), true));
                q.select(c).where(predicates.toArray(new Predicate[]{}));
                list = query.getEntityManager().createQuery(q).getResultList();
            } else {
                list = query.getEntityManager().createNamedQuery("Festival.findAllVerified").setParameter("verified", true).getResultList();
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

    public List<Festival> getLastTop5Rated() {
        List<Festival> list;
        list = query.getEntityManager().createNamedQuery("Festival.findByRating", Festival.class).setMaxResults(5).getResultList();
        return list;
    }
    
    public List<Festival> unVerified() {
        List<Festival> list;
        list = query.getEntityManager().createNamedQuery("Festival.findByVerified", Festival.class).setParameter("verified", false).getResultList();
        return list;
    }
    
    public void accept(long id) {
        transactionCheck();
        Festival fest = query.getEntityManager().createNamedQuery("Festival.findById", Festival.class).setParameter("id", id).getSingleResult();
        if (fest != null) {
            fest.setVerified(true);
            query.getEntityManager().getTransaction().commit();
        }
    }

    public void reject(long id) {
        transactionCheck(); 
        Festival fest = query.getEntityManager().createNamedQuery("Festival.findById", Festival.class).setParameter("id", id).getSingleResult();
        if (fest != null) {
            query.getEntityManager().remove(fest);
            query.getEntityManager().getTransaction().commit();
            getUnVerifiedList().remove(fest);
        }
    }
    
    private void transactionCheck() {
        if (!query.getEntityManager().getTransaction().isActive()) {
            query.getEntityManager().getTransaction().begin();
        }
    }

    public List<Festival> getUnVerifiedList() {
        if (unVerifiedList == null) {
            unVerifiedList = unVerified();
        }
        return unVerifiedList;
    }

    public void setUnVerifiedList(List<Festival> unVerifiedList) {
        this.unVerifiedList = unVerifiedList;
    }
}
