package com.musicfestivals.app;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "navigationControl", eager = true)
@RequestScoped
public class PageNavigation implements Serializable {

    private static final long serialVersionUID = 1L;

    public static void goTo(String page) throws IOException {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(page);
            System.out.println("User: " + AuthorizationBean.getLoggedInUser().getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }

    public String getURLPath() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURL().toString();
        return url;
    }    
    
            
}
