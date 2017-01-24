package com.musicfestivals.app;

import javax.faces.context.FacesContext;

public class GlobalVars {

    public static String getPathToImages() {
        String path = FacesContext.getCurrentInstance().getExternalContext()
                .getRealPath("/");
        return path + "resources/images/tmp/";
    }
    
    public static String getPathToVideos() {
        String path = FacesContext.getCurrentInstance().getExternalContext()
                .getRealPath("/");
        return path + "resources/videos/tmp/";
    }
}
