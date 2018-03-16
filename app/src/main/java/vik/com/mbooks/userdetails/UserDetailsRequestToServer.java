package vik.com.mbooks.userdetails;

import io.reactivex.Observable;
import vik.com.mbooks.retrofit.AppDataClient;
import vik.com.mbooks.retrofit.IAppDataAPIs;

/**
 * Created by M1032130 on 2/26/2018.
 */

public class UserDetailsRequestToServer {
    IAppDataAPIs mClient;
    String authHeader;
    String userId;

    public UserDetailsRequestToServer(String userId, String authHeader) {
        mClient = AppDataClient.getClient();
        this.userId = userId;
        this.authHeader = authHeader;
    }

    public Observable<UserDetails> callService() {

        Observable<UserDetails> observable = mClient.getUserDetailsByUserId(userId, authHeader);
        return observable;
    }
}
