package com.musicfestivals.user;

import com.musicfestivals.query.DataQuery;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "userList")
@ViewScoped
public class UserList {
    private List<UserProfile> userList;
    private final DataQuery query = new DataQuery();
    
    
    private String sqlSearchCondition(){
        return "true";
    }
    public List<UserProfile> loadList() {
        StringBuilder sb = new StringBuilder("SELECT * FROM user_profile");
//        sb.append(sqlSearchCondition());
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
    
    
    
}
