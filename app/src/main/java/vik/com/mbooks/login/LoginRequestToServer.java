package vik.com.mbooks.login;

import io.reactivex.Observable;
import vik.com.mbooks.login.request.LoginRequest;
import vik.com.mbooks.login.response.LoginResponse;
import vik.com.mbooks.retrofit.AppDataClient;
import vik.com.mbooks.retrofit.IAppDataAPIs;

/**
 * Created by M1032130 on 2/22/2018.
 */

public class LoginRequestToServer {
    IAppDataAPIs mClient;
    LoginRequest input;

    public LoginRequestToServer(LoginRequest input) {
        mClient = AppDataClient.getClient();
        this.input = input;
    }

    public Observable<LoginResponse> callService() {
        Observable<LoginResponse> observable = mClient.login(input);
        return observable;
    }
}
