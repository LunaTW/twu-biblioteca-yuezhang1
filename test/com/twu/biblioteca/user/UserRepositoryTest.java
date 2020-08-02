package com.twu.biblioteca.user;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class UserRepositoryTest {
    User user1 = new User("5102636","Luna","moneymoneymoney","unswluna@gmail.com");
    User user2 =  new User("5123456","ZHANGYUE","ILOVEMONEY","money@gmail.com");

    @Test
    public void UserInformationCanFoundFromAvailableUser(){
        assertEquals(UserRepository.getUser("5102636").getUserName(),"Luna");
    }

    @Test
    public void ID_Pd_areValidCredentials(){}{
        assertTrue(UserRepository.areValidCredentials("5102636","moneymoneymoney"));
        assertFalse(UserRepository.areValidCredentials("5102636","Wrong"));
    }

    @Test
    public void Fuction_getUserByUserID(){
        assertEquals(UserRepository.getUserByUserID("5102636").getUserName(),"Luna");
    }
}
