package vik.com.mbooks.login;

import android.content.Context;

import io.reactivex.Observable;
import vik.com.mbooks.login.request.LoginRequest;
import vik.com.mbooks.login.response.LoginResponse;

/**
 * Created by M1032130 on 1/8/2018.
 */

public class LoginViewModel {
    ILoginModel loginModel;
    Context mContext;

    public LoginViewModel(Context context) {
        loginModel = new LoginModel();
        mContext = context;
    }

    public Observable<LoginResponse> login(LoginRequest loginRequest) {
        return loginModel.login(loginRequest);
    }
}
