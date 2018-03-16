package vik.com.mbooks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vik.com.mbooks.login.LoginActivity;
import vik.com.mbooks.main.MainActivity;
import vik.com.mbooks.utils.BooksSharedPreferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = null;
        if (BooksSharedPreferences.isRememberMe(this)) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
