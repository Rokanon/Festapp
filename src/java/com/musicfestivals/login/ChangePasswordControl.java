package com.musicfestivals.login;

import com.musicfestivals.query.DataQuery;
import com.musicfestivals.user.UserProfile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "changePass")
@RequestScoped
public class ChangePasswordControl {

    private String username;
    private String password;
    private String newPassword;
    private String repeatPassword;
    private final DataQuery query = new DataQuery();
    private StringBuilder errorsSB;

    public final String changePassword() {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        errorsSB = new StringBuilder("");

        if (query.changePasswordControl(username, password)) {
            UserProfile user = query.getEntityManager().createNamedQuery("UserProfile.control", UserProfile.class).setParameter("username", username).setParameter("password", password).getSingleResult();
            System.out.println("User not null");
            if (validateNewPassword(newPassword)) {
                // success
                System.out.println("Usao u true");
                user.setPassword(newPassword);
                query.getEntityManager().getTransaction().commit();
                RequestContext.getCurrentInstance().update("growl");
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succes", "Password succesfully changed!"));
                return "login.xhtml?faces-redirect=true";
            }

        } else {
            System.out.println("User je null");
            errorsSB.append("Invalid authentification!");
        }

        RequestContext.getCurrentInstance().update("growl");
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", errorsSB.toString()));
        return "";

    }

    private boolean validateNewPassword(String password) {
        short numberOfLowercaseLetters = 0;

        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                numberOfLowercaseLetters++;
            }
        }

        if (password.length() < 6 || password.length() > 12) {
            errorsSB.append("Invalid password! Size of a password needs to be between 6 and 12 characters long!\n");
            return false;
        }
        if (!password.equals(password.toLowerCase())) {
            errorsSB.append("Password must contain at least one uppercase letter!\n");
            return false;
        }
        if (numberOfLowercaseLetters < 3) {
            errorsSB.append("Password must contain at least three lowercase letters!\n");
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z]");
        Matcher m = p.matcher(password);
        // boolean b = m.matches();
        boolean b = m.find();
        if (!b) {
            errorsSB.append("Password must contain at least one number or special character!\n");
            return false;
        }
        if (!Character.isLetter(password.charAt(0))) {
            errorsSB.append("First character of a password must be a letter!\n");
            return false;
        }
        for (int i = 0; (i + 2) < password.length(); i++) {
            char first = password.charAt(i);
            char second = password.charAt(i + 1);
            char third = password.charAt(i + 2);

            if ((second == first) && (second == third)) {
                errorsSB.append("Password cannot contain more than two consecutive characters!\n");
                return false;
            }
        }
        return true;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
