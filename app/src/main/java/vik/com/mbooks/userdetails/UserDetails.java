package vik.com.mbooks.userdetails;

import vik.com.mbooks.login.response.User;
import vik.com.mbooks.retrofit.Response;

/**
 * Created by M1032130 on 2/26/2018.
 */

public class UserDetails extends Response {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
