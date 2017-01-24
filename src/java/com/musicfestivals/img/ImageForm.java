package com.musicfestivals.img;

import com.musicfestivals.app.GlobalVars;
import com.musicfestivals.app.JSFParamGetter;
import com.musicfestivals.festival.Festival;
import com.musicfestivals.query.DataQuery;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
import javax.imageio.ImageIO;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "uploadImage")
@ViewScoped
public class ImageForm implements Serializable {

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
        Image uploadedImage = new Image();

        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        long fileSize = event.getFile().getSize();
        String extension = "." + event.getFile().getFileName().substring(event.getFile().getFileName().lastIndexOf('.') + 1);
        String name = fmt.format(new Date()) + extension;
        File file = new File(GlobalVars.getPathToImages() + name);

        InputStream is = event.getFile().getInputstream();
        OutputStream out = new FileOutputStream(file);
        BufferedImage img = ImageIO.read(is);
        int type = img.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : img.getType();
        BufferedImage scaledImg;
        scaledImg = resizeImage(img, type);
        ImageIO.write(scaledImg, "jpg", out);
        byte buf[] = new byte[1024];
        int len;
        while ((len = is.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        is.close();
        out.close();
        uploadedImage.setFestivalId(getFestival().getId());
        uploadedImage.setFileName(name);
        uploadedImage.setFileSize(fileSize);
        query.getEntityManager().persist(uploadedImage);
        query.getEntityManager().getTransaction().commit();
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(500, 315, type);//set width and height of image
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 500, 315, null);
        g.dispose();

        return resizedImage;
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }
}
