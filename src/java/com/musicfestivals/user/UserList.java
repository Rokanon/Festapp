package com.musicfestivals.user;

import com.musicfestivals.query.DataQuery;
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
    public List<UserProfile> loadList(String sqlSearchCondition) {
        StringBuilder sb = new StringBuilder("SELECT * FROM user_profile up WHERE ");
        sb.append(sqlSearchCondition);
        List<UserProfile> list;
        list = query.getEntityManager().createQuery(sb.toString()).getResultList();
        return list == null ? getUserList() : list;
    }

    public List<UserProfile> getUserList() {
        if (userList == null) {
            userList = new ArrayList<>();
        }
        return userList;
    }

    public void setUserList(List<UserProfile> userList) {
        this.userList = userList;
    }
    
    
    
}
