package com.twu.biblioteca.user;

import com.twu.biblioteca.book.Book;
import com.twu.biblioteca.user.User;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UserRepository {

    public static List<User> availableUserInformations = new ArrayList<>(Arrays.asList(
            new User("5102636","Luna","moneymoneymoney","unswluna@gmail.com"),
            new User("5123456","ZHANGYUE","ILOVEMONEY","money@gmail.com")
    ));
    private UserStatus userStatus = new UserStatus();

    public UserRepository(List<User> availableUserInformations) {
        this.availableUserInformations = availableUserInformations;
    }

    public void addNewUser(User user){
        availableUserInformations.add(user);
    }

    public List<User> getUsers(){
        return availableUserInformations;
    }

    public static User getUser(String UserID) {
        return availableUserInformations.stream().filter(user -> user.getUserID().equals(UserID)).findFirst().orElse(null);
    }
    public static boolean areValidCredentials(String UserID, String Password) {
        User userAccount = getUserByUserID(UserID);
        return userAccount.isValidPassword(Password);
    }

    public static User getUserByUserID(String UserID){
        return availableUserInformations.stream().filter(availableUserInformation->availableUserInformation.getUserID().equals(UserID)).findFirst().get();
    }

}




