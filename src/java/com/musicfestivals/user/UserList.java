package com.musicfestivals.user;

import com.musicfestivals.query.DataQuery;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "userList")
@ViewScoped
public class UserList implements Serializable {

    private List<UserProfile> userList;
    private final DataQuery query = new DataQuery();

    private String sqlSearchCondition() {
        return "true";
    }

    public List<UserProfile> loadList() {
        List<UserProfile> list;
        Connection conn;
        list = query.getEntityManager().createNamedQuery("UserProfile.findAll", UserProfile.class).getResultList();
        System.out.println(list);
        return list;
    }

    public List<UserProfile> getUserList() {
        if (userList == null) {
            userList = loadList();
        }
        return userList;
    }

    public void setUserList(List<UserProfile> userList) {
        this.userList = userList;
    }

    public void acceptUser(long id) {
        transactionCheck();
        UserProfile up = query.getEntityManager().createNamedQuery("UserProfile.findById", UserProfile.class).setParameter("id", id).getSingleResult();
        if (up != null) {
            System.out.println("Accept user");
            up.setVerified((short) 1);
            query.getEntityManager().getTransaction().commit();
        }
    }

    public void rejectUser(long id) {
        transactionCheck(); 
        UserProfile up = query.getEntityManager().createNamedQuery("UserProfile.findById", UserProfile.class).setParameter("id", id).getSingleResult();
        if (up != null) {
            System.out.println("Reject user");
            query.getEntityManager().remove(up);
            query.getEntityManager().getTransaction().commit();
            getUserList().remove(up);
        }
    }

    public void removeUser(long id) {
        rejectUser(id);
    }

    private void transactionCheck() {
        if (!query.getEntityManager().getTransaction().isActive()) {
            query.getEntityManager().getTransaction().begin();
        }
    }
}

