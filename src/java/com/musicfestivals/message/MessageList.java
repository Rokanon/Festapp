/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicfestivals.message;

import com.musicfestivals.app.AuthorizationBean;
import com.musicfestivals.query.DataQuery;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "messageList")
@ViewScoped
public class MessageList implements Serializable {

    private final DataQuery query = new DataQuery();
    private long userId;

    public List<Message> getList() {
        List<Message> list = null;
        System.out.println("User id" + getUserId());
        list = query.getEntityManager().createNamedQuery("Message.findByUserId", Message.class).setParameter("userId", getUserId()).getResultList();
        return list;
    }

    public long getUserId() {
        return AuthorizationBean.getLoggedInUser().getId();
    }
}
