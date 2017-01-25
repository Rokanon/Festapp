package com.musicfestivals.img;

import com.musicfestivals.app.AuthorizationBean;
import com.musicfestivals.app.GlobalVars;
import com.musicfestivals.app.JSFParamGetter;
import com.musicfestivals.festival.Festival;
import com.musicfestivals.query.DataQuery;
import com.musicfestivals.video.Video;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

@ManagedBean(name = "uploadVideo")
@ViewScoped
public class VideoForm implements Serializable {

    private static final long serialVersionUID = 1L;
    private final DataQuery query = new DataQuery();
    private Festival festival;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        JSFParamGetter paramGetter = new JSFParamGetter(fc);
        long dataId = paramGetter.getLongParametar("dataId");
        if (dataId > 0) {
            setFestival(query.getEntityManager().createNamedQuery("Festival.findById", Festival.class).setParameter("id", dataId).getSingleResult());
        }

    }

    public void fileUpload(FileUploadEvent event) throws IOException {
        Video uploadedVideo = new Video();

        String path = FacesContext.getCurrentInstance().getExternalContext()
                .getRealPath("/");
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        long fileSize = event.getFile().getSize();
        String extension = "." + event.getFile().getFileName().substring(event.getFile().getFileName().lastIndexOf('.') + 1);
        String name = fmt.format(new Date()) + extension.toLowerCase();
         File file = new File(path + "/resources/videos/tmp/" + name);

        InputStream is = event.getFile().getInputstream();
        OutputStream out = new FileOutputStream(file);
        byte buf[] = new byte[1024];
        int len;
        while ((len = is.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        is.close();
        out.close();
        uploadedVideo.setFestivalId(getFestival().getId());
        uploadedVideo.setFileName(name);
        uploadedVideo.setFileSize(fileSize);
        if (AuthorizationBean.getLoggedInUser().getKind() == 1) {
            uploadedVideo.setApproved(1);
        } else {
            uploadedVideo.setApproved(0);
        }
        transactionCheck();
        query.getEntityManager().persist(uploadedVideo);
        query.getEntityManager().getTransaction().commit();
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    private void transactionCheck() {
        if (!query.getEntityManager().getTransaction().isActive()) {
            query.getEntityManager().getTransaction().begin();
        }
    }
}
