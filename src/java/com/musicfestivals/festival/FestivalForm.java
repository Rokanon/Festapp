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
import com.musicfestivals.comment.Comment;
import com.musicfestivals.festival.day.FestivalDay;
import com.musicfestivals.reservation.Reservation;
import java.math.BigDecimal;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "festivalForm")
@ViewScoped
public class FestivalForm implements Serializable {

    private Comment comment;
    private List<Comment> comments;
    private List<Artist> artists;
    private List<FestivalDay> days;
    private List<Image> images;
    private Festival festival;
    private final DataQuery query = new DataQuery();
    private String back;
    private boolean newData = false;
    private Integer vote;
    private String commentContent;
    private boolean ratingSetProperly = false;

    @PostConstruct
    public void init() {
        vote = 0;
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            JSFParamGetter paramGeter = new JSFParamGetter(fc);
            long dataId = paramGeter.getLongParametar("dataId");
            int updateViews = paramGeter.getIntParametar("viewUser");
            if (dataId > 0) {
                setFestival(query.getEntityManager().createNamedQuery("Festival.findById", Festival.class).setParameter("id", dataId).getSingleResult());
                if (updateViews == 1 && dataId > 0) {
                    updateNumberOfViews();
                }
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
        getComment().setRating(getVote());
        query.getEntityManager().getTransaction().commit();
        ratingSetProperly = true;
    }

    public void comment() {
        transactionCheck();
        comment = new Comment();
        comment.setFestivalId(getFestival().getId());
        comment.setFestivalTitle(getFestival().getTitle());
        comment.setUserId(AuthorizationBean.getLoggedInUser().getId());
        comment.setText(getCommentContent());
        if (ratingSetProperly) {
            comment.setRating(getVote());
        } else {
            comment.setRating(-1);
        }
        query.getEntityManager().persist(comment);
        query.getEntityManager().getTransaction().commit();

    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public boolean isHasBoughtTicket() {
        List<Reservation> list;
        long userId = AuthorizationBean.getLoggedInUser().getId();
        if (getFestival() == null) {
            return false;
        }
        list = query.getEntityManager().createNamedQuery("Reservation.findIfUserBoughtForFestival", Reservation.class).setParameter("userId", userId).setParameter("festival", getFestival().getId()).getResultList();

        // if list is null, then reservation doesn't exist, so he didn't bought it
        return list != null;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public boolean isHasUserCommented() {
        long userId = AuthorizationBean.getLoggedInUser().getId();

        List<Comment> userComment = query.getEntityManager().createNamedQuery("Comment.findByFestIdAndUser", Comment.class).setParameter("userId", userId).setParameter("festivalId", getFestival().getId()).setMaxResults(1).getResultList();
        if (userComment == null || userComment.isEmpty()) {
            System.out.println("Ne postoji komentar, nije komentarisao");
            return false;
        } else {
            System.out.println("Korisnik je vec komentarisao");
            return true;
        }
    }

    public List<Comment> getComments() {
        long userId = AuthorizationBean.getLoggedInUser().getId();
        comments = query.getEntityManager().createNamedQuery("Comment.findByNameAndUser", Comment.class).setParameter("userId", userId).setParameter("festivalTitle", getFestival().getTitle()).getResultList();
        return comments != null ? comments : new ArrayList<>();
    }

    public boolean alreadyRated() {
        if (getComment().getRating() == -1 || getComment().getRating() == 0.0) {
            return false;
        } else {
            return true;
        }
    }

    public Comment getComment() {
        List<Comment> commentsList = query.getEntityManager().createNamedQuery("Comment.findByFestIdAndUser", Comment.class).setParameter("userId", AuthorizationBean.getLoggedInUser().getId()).setParameter("festivalId", getFestival().getId()).getResultList();
        if (commentsList == null || commentsList.isEmpty()) {
            return new Comment();
        }
        System.out.println("Comment list size = " + commentsList.size());
        comment = commentsList.get(0);
        return comment != null ? comment : new Comment();
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    private void updateNumberOfViews() {
        transactionCheck();
        long numberOfViews = getFestival().getTimesSeen() + 1;
        getFestival().setTimesSeen(numberOfViews);
        query.getEntityManager().getTransaction().commit();
    }
}
