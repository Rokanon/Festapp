package com.musicfestivals.login;

import com.musicfestivals.query.DataQuery;
import com.musicfestivals.user.UserProfile;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "registration")
@RequestScoped
public class RegistrationController implements Serializable {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String passwordRepeated; // TODO: in passValidation check if match
    private final DataQuery query = new DataQuery();
    private final StringBuilder errorsSB = new StringBuilder("");

    public final String registrationControl() {

        if (validateUser()) {
            UserProfile user = new UserProfile();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            user.setPhone(phone);
            user.setKind((short)0);
            // TODO: hash pass before setting, and do this in LoginController before getting it from db
            user.setPassword(password);
            query.getEntityManager().persist(user);
            query.getEntityManager().getTransaction().commit();

            RequestContext.getCurrentInstance().update("growl");
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succes", "Registration succesfull!"));
            return "home.xhtml?faces-redirect=true";
        } else {
            RequestContext.getCurrentInstance().update("growl");
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", errorsSB.toString()));
            return "";
        }

    }

    private boolean validateUser() {
        boolean allowedToAdd = true;
        if (query.registrationUsernameControl(username)) {
            errorsSB.append("Username already exists!\n");
            return false;
        }
        // TODO: add other fields validations
        allowedToAdd = allowedToAdd && validatePassword() && validateEmail();
        return allowedToAdd;
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

    private boolean validateEmail() {
        final String EMAIL_PATTERN
                = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(getEmail());
        if (matcher.matches() == false) {
            errorsSB.append("Invalid email adress!\n");
            return false;
        }

        return true;
    }

    private boolean validatePassword() {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    public void setPasswordRepeated(String passwordRepeated) {
        this.passwordRepeated = passwordRepeated;
    }
}
