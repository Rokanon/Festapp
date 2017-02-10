package com.musicfestivals.app;

import com.musicfestivals.loggedusers.LoggedInUsers;
import com.musicfestivals.query.DataQuery;
import com.musicfestivals.user.UserProfile;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "appAuthorization")
@SessionScoped
public class AuthorizationBean implements Serializable {
    
    private static UserProfile loggedInUser;

    public boolean isAdmin(){
        return getLoggedInUser().getKind() == 1;
    }
    
    public boolean isMember() {
        return getLoggedInUser().getKind() == 0;
    }
    public boolean isAnonymous() {
        return getLoggedInUser().getKind() == -1;
    }

    public static UserProfile getLoggedInUser() {
        if (loggedInUser == null) {
            loggedInUser = new UserProfile(); 
            System.out.println("Logged user = null");
        }
        return loggedInUser;
    }

    public static void setLoggedInUser(UserProfile aLoggedInUser) {
        System.out.println("set logged user, loged user kind is; " + aLoggedInUser.getKind());
        if(loggedInUser.getKind() == 0) {
            DataQuery query = new DataQuery();
            LoggedInUsers liu = new LoggedInUsers();
            liu.setUserId(loggedInUser.getId());
            liu.setUserName(loggedInUser.getUsername());
            query.getEntityManager().persist(liu);
            query.getEntityManager().getTransaction().commit();
        }
        loggedInUser = aLoggedInUser;
    }
    
    public String getUserName() {
        return getLoggedInUser().getUsername();
    }
    
    public UserProfile getUserProfile(){
        return getLoggedInUser();
    }
}
