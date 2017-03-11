package com.musicfestivals.loggedusers;

import com.musicfestivals.query.DataQuery;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

@ManagedBean(name = "loggedUsersList")
@ViewScoped
public class LoggedUsersList implements Serializable {

    private final DataQuery query = new DataQuery();
    List<LoggedInUsers> loggedUsers;

    public List<LoggedInUsers> getLoggedUsers() {
        loggedUsers = query.getEntityManager().createNamedQuery("LoggedInUsers.findAll", LoggedInUsers.class).setMaxResults(10).getResultList();
        return loggedUsers;
    }
}
