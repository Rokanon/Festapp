/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicfestivals.query;

import com.musicfestivals.user.UserProfile;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author marija
 */
public class DataQuery {

    EntityManagerFactory emf;
    EntityManager em;

    public DataQuery() {
        emf = Persistence.createEntityManagerFactory("festappPU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public boolean loginControl(String username, String password) {
        try {
            UserProfile up = em.createNamedQuery("UserProfile.control", UserProfile.class).setParameter("username", username).setParameter("password", password).getSingleResult();
            return up != null;

        } catch (Exception e) {
            return false;
        }
    }

}
