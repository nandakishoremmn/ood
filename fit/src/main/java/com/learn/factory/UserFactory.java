package com.learn.factory;

import com.learn.UserImpl;
import com.learn.ifaces.User;

public class UserFactory {
    public static User get(String username) {
        return new UserImpl(username);
    }
}
