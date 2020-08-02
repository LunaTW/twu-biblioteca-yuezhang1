package com.twu.biblioteca.user;

public class UserStatus {
    private User user;
    private boolean userisLoggedIn;

    public UserStatus() {    }

    public boolean UserisLoggedIn() {
        return userisLoggedIn;
    }

    public void login(User user) {
        this.userisLoggedIn = true;
        this.user = user;
    }

    public User getUser() {
        if (user == null) {
            System.out.println("Please login first.");
        }
        return user;
    }

}
