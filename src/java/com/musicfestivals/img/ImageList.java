package com.musicfestivals.img;

import com.musicfestivals.query.DataQuery;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "imageList")
@ViewScoped
public class ImageList implements Serializable {

    private List<Image> imageList;
    private final DataQuery query = new DataQuery();

    public List<Image> getImageList() {
        imageList = query.getEntityManager().createNamedQuery("Image.findByFestivalId", Image.class).getResultList();
        return imageList;
    }
}
