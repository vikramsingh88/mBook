package vik.com.mbooks.login;

import io.reactivex.Observable;
import vik.com.mbooks.login.request.LoginRequest;
import vik.com.mbooks.login.response.LoginResponse;

/**
 * Created by M1032130 on 12/20/2017.
 */

public interface ILoginModel {
    Observable<LoginResponse> login(LoginRequest loginRequest);
}
