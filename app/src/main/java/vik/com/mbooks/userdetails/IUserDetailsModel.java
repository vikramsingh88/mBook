package vik.com.mbooks.userdetails;

import io.reactivex.Observable;

/**
 * Created by M1032130 on 2/26/2018.
 */

public interface IUserDetailsModel {
    Observable<UserDetails> getUserDetailsByUserId(String userId, String token);
}
