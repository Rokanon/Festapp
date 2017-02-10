package com.musicfestivals.loggedusers;

import com.musicfestivals.query.DataQuery;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "loggedUsersList")
@RequestScoped
public class LoggedUsersList implements Serializable {

    private final DataQuery query = new DataQuery();
    List<LoggedInUsers> loggedInUsers;

    public List<LoggedInUsers> getLoggedUsersList() {
        loggedInUsers = query.getEntityManager().createNamedQuery("LoggedInUsers.findAll", LoggedInUsers.class).setMaxResults(10).getResultList();
        return loggedInUsers;
    }
}
