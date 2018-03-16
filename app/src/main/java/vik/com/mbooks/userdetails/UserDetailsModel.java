package vik.com.mbooks.userdetails;

import io.reactivex.Observable;

/**
 * Created by M1032130 on 2/26/2018.
 */

public class UserDetailsModel implements IUserDetailsModel {
    @Override
    public Observable<UserDetails> getUserDetailsByUserId(String userId, String token) {
        return new UserDetailsRequestToServer(userId, token).callService();
    }
}
