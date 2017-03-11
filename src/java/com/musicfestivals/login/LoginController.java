package com.musicfestivals.login;

import com.musicfestivals.app.AuthorizationBean;
import com.musicfestivals.loggedusers.LoggedInUsers;
import com.musicfestivals.query.DataQuery;
import com.musicfestivals.user.UserProfile;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "login")
@SessionScoped
public class LoginController implements Serializable {

    private String username;
    private String password;
    private final DataQuery query = new DataQuery();

    public final String loginControl() {
        System.out.println("Login control invoked!");
        if (query.loginControl(username, password)) {
            putToTheListOfLastLoggedIn();
            return "home.xhtml?faces-redirect=true";
        } else {
            RequestContext.getCurrentInstance().update("growl");
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Username or password invalid, or account still not aproved!"));
            return "";
        }
    }

    public final String logoutControl() {
        System.out.println("Logout invoked");
        setPassword("");
        setUsername("");
        UserProfile guest = new UserProfile();
        AuthorizationBean.setLoggedInUser(guest);
        System.out.println("Before return");
        return "login.xhtml?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private void putToTheListOfLastLoggedIn() {
        if (AuthorizationBean.getLoggedInUser().getKind() == 0) {
            DataQuery query = new DataQuery();
            LoggedInUsers liu = new LoggedInUsers();
            liu.setUserId(AuthorizationBean.getLoggedInUser().getId());
            liu.setUserName(AuthorizationBean.getLoggedInUser().getUsername());
            query.getEntityManager().persist(liu);
            query.getEntityManager().getTransaction().commit();
        }
    }

}
