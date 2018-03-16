package vik.com.mbooks.login.response;

import vik.com.mbooks.retrofit.Response;

/**
 * Created by M1032130 on 2/22/2018.
 */

public class LoginResponse extends Response{
    private String token;
    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
