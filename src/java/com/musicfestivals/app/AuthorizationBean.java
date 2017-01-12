package com.musicfestivals.app;

import com.musicfestivals.user.UserProfile;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "appAuthorization")
@SessionScoped
public class AuthorizationBean implements Serializable {
    
    private UserProfile user;

    public UserProfile getUser() {
        if (user == null) {
            user = new UserProfile();
            user.setKind((short)-1);
        }
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }
    
    public boolean isAdmin(){
        return getUser().getKind() == 1;
    }
    
    public boolean isMember() {
        return getUser().getKind() == 0;
    }
    public boolean isAnonymous() {
        return getUser().getKind() == -1;
    }
}
