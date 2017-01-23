/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicfestivals.query;

import com.musicfestivals.app.AuthorizationBean;
import com.musicfestivals.user.UserProfile;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DataQuery implements Serializable{

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
            if (up != null) {
                System.out.println("UP NOT NULL, kind=" + up.getKind());
                AuthorizationBean.setLoggedInUser(up);
                return true;
            } else {
                AuthorizationBean.getLoggedInUser().setKind((short) -1);
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public boolean changePasswordControl(String username, String password) {
        try {
            UserProfile up = em.createNamedQuery("UserProfile.control", UserProfile.class).setParameter("username", username).setParameter("password", password).getSingleResult();
            return up != null;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean registrationUsernameControl(String username) {
        try {
            UserProfile up = em.createNamedQuery("UserProfile.findByUsername", UserProfile.class).setParameter("username", username).getSingleResult();
            if (up == null) {
                System.out.println("\nUsername doesnt exists");
                return false;
            } else {
                System.out.println("\nUsername exists");
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public EntityManager getEntityManager() {
        return this.em;
    }
}
