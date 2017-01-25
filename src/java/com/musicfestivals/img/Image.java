/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicfestivals.img;

import java.io.Serializable;
import java.math.BigInteger;
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

/**
 *
 * @author johnnybre
 */
@Entity
@Table(name = "image")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i")
    , @NamedQuery(name = "Image.findById", query = "SELECT i FROM Image i WHERE i.id = :id")
    , @NamedQuery(name = "Image.findByFestivalId", query = "SELECT i FROM Image i WHERE i.festivalId = :festivalId")
    , @NamedQuery(name = "Image.findByApproved", query = "SELECT i FROM Image i WHERE i.approved :approved")
    , @NamedQuery(name = "Image.findByFileName", query = "SELECT i FROM Image i WHERE i.fileName = :fileName")
    , @NamedQuery(name = "Image.findByFileSize", query = "SELECT i FROM Image i WHERE i.fileSize = :fileSize")})
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "festival_id")
    private long festivalId;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_size")
    private long fileSize;
    @Column(name = "approved")
    private long approved;

    public Image() {
    }

    public Image(Long id) {
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

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
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
        if (!(object instanceof Image)) {
            return false;
        }
        Image other = (Image) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.musicfestivals.img.Image[ id=" + id + " ]";
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getApproved() {
        return approved;
    }

    public void setApproved(long approved) {
        this.approved = approved;
    }
    
}
