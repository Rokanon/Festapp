package com.musicfestivals.comment;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "comment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c")
    , @NamedQuery(name = "Comment.findById", query = "SELECT c FROM Comment c WHERE c.id = :id")
    , @NamedQuery(name = "Comment.findByNameAndUser", query = "SELECT c FROM Comment c WHERE c.userId = :userId AND c.festivalTitle = :festivalTitle ORDER BY c.id desc")
    , @NamedQuery(name = "Comment.findByFestIdAndUser", query = "SELECT c FROM Comment c WHERE c.userId = :userId AND c.festivalId =:festivalId")
    , @NamedQuery(name = "Comment.findByFestivalId", query = "SELECT c FROM Comment c WHERE c.festivalId = :festivalId")
    , @NamedQuery(name = "Comment.findByFestivalTitle", query = "SELECT c FROM Comment c WHERE c.festivalTitle = :festivalTitle")
    , @NamedQuery(name = "Comment.findByUserId", query = "SELECT c FROM Comment c WHERE c.userId = :userId")
    , @NamedQuery(name = "Comment.findByText", query = "SELECT c FROM Comment c WHERE c.text = :text")
    , @NamedQuery(name = "Comment.findByRating", query = "SELECT c FROM Comment c WHERE c.rating = :rating")})
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "festival_id")
    private long festivalId;
    @Column(name = "festival_title")
    private String festivalTitle;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "text")
    private String text;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private double rating;

    public Comment() {
    }

    public Comment(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(long festivalId) {
        this.festivalId = festivalId;
    }

    public String getFestivalTitle() {
        return festivalTitle;
    }

    public void setFestivalTitle(String festivalTitle) {
        this.festivalTitle = festivalTitle;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.musicfestivals.comment.Comment[ id=" + id + " ]";
    }
    
}
