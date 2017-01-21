package com.musicfestivals.img;

import com.musicfestivals.festival.Festival;
import com.musicfestivals.query.DataQuery;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

@ManagedBean(name="uploadImage")
@ViewScoped
public class ImageForm implements Serializable {

    private static final long serialVersionUID = 1L;
    private UploadedFile file;
    private final DataQuery query = new DataQuery();
    private Festival festival;

    public UploadedFile getFile() {
        return file;
    }
    
     @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        long dataId = Long.parseLong(fc.getExternalContext().getRequestParameterMap().get("dataId"));
        if (dataId > 0) {
            setFestival(query.getEntityManager().createNamedQuery("Festival.findById", Festival.class).setParameter("id", dataId).getSingleResult());
        }

    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() throws IOException {
        if (file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            Image img = new Image();
            img.setFestivalId(getFestival().getId());
            byte[] imgBytes = IOUtils.toByteArray(file.getInputstream());
            img.setImg(imgBytes);
            query.getEntityManager().getTransaction().commit();
            System.out.println("Upload to db done");
        }
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }
}
