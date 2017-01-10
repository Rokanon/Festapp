package com.musicfestivals.users.model;

public class UserProfile {

    public static int KIND_REGULAR = 0;
    public static int KIND_ADMIN = 1;
    public static int KIND_OTHER = 2;

    private long id;
    private String firstName;
    private String lastName;
    private String userName;
    private int kind;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
