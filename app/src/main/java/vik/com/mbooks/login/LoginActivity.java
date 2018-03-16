package vik.com.mbooks.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import vik.com.mbooks.R;
import vik.com.mbooks.login.request.LoginRequest;
import vik.com.mbooks.login.response.LoginResponse;
import vik.com.mbooks.login.response.User;
import vik.com.mbooks.main.MainActivity;
import vik.com.mbooks.utils.BooksSharedPreferences;
import vik.com.mbooks.utils.CustomDialog;
import vik.com.mbooks.utils.CustomProgressDialog;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private Button mButtonLogin;
    private Button mButtonReset;
    private EditText mEditEmployeeId;
    private EditText mEditPassword;
    private CheckBox mCheckBoxRememberMe;
    private ProgressDialog mProgressDialog;

    CompositeDisposable compositeDisposable;
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        compositeDisposable = new CompositeDisposable();
        loginViewModel = new LoginViewModel(this);

        mProgressDialog = CustomProgressDialog.getProgressDialog(this, "logging...");
        this.mButtonLogin = (Button) findViewById(R.id.button_login);
        mButtonReset = findViewById(R.id.button_reset);
        mEditEmployeeId = findViewById(R.id.email);
        mEditPassword = findViewById(R.id.password);
        mCheckBoxRememberMe = findViewById(R.id.check_remember_me);
        //Login action
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        //Reset action
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetInputs();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetInputs();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void resetInputs() {
        mEditEmployeeId.setText("");
        mEditPassword.setText("");
        mEditEmployeeId.requestFocus();
    }

    private String getEmployeeId() {
        return mEditEmployeeId.getText().toString();
    }

    private String getPassword() {
        return mEditPassword.getText().toString();
    }

    private void setEmployeeIdEmptyError() {
        mEditEmployeeId.setError(getString(R.string.email_required));
    }

    private void setPasswordEmptyError() {
        mEditPassword.setError(getString(R.string.password_required));
    }

    private LoginRequest buildLoginRequest() {
        LoginRequest loginRequest = new LoginRequest(getEmployeeId(), getPassword());
        return loginRequest;
    }

    private void login() {
        //Check employee id or password not empty
        if (getEmployeeId().equals("") || getPassword().equals("")) {
            if (getEmployeeId().equals("")) {
                setEmployeeIdEmptyError();
                return;
            }
            if (getPassword().equals("")) {
                setPasswordEmptyError();
                return;
            }
        }
        //Login
        mProgressDialog.show();
        DisposableObserver disposableObserver = loginViewModel.login(buildLoginRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginResponse>() {
                    @Override
                    public void onNext(LoginResponse o) {

                        if (o != null) {
                            if (o.isSuccess()) {
                                String token = o.getToken();
                                //save token to preference
                                BooksSharedPreferences.saveToken(LoginActivity.this, token);
                                //save remember me
                                BooksSharedPreferences.saveRememberMe(LoginActivity.this, mCheckBoxRememberMe.isChecked());
                                User user = o.getUser();
                                //save user info
                                BooksSharedPreferences.saveUserInfo(LoginActivity.this, user);
                                navigateToMainScreen(user);
                            } else {
                                CustomDialog.showErrorDialog(LoginActivity.this, o.getMessage(),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                            }
                        } else {
                            CustomDialog.showErrorDialog(LoginActivity.this, "Internal server error",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        CustomDialog.showErrorDialog(LoginActivity.this, "User not found",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                    }
                });
        compositeDisposable.add(disposableObserver);
    }

    private void navigateToMainScreen(User user) {
        Intent intentMain = new Intent(this, MainActivity.class);
        intentMain.putExtra("user", user);
        startActivity(intentMain);
        finish();
    }
}

