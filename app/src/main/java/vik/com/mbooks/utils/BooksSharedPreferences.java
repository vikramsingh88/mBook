package vik.com.mbooks.utils;

import android.content.Context;
import android.content.SharedPreferences;

import vik.com.mbooks.login.response.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by M1032130 on 2/23/2018.
 */

public class BooksSharedPreferences {
    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        return editor;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);

        return pref;
    }

    public static void saveToken(Context context, String token) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString("token", token);
        editor.commit();
    }
    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getString("token", null);
    }

    public static void saveRememberMe(Context context, boolean isChecked) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean("isRemember", isChecked);
        editor.commit();
    }

    public static boolean isRememberMe(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getBoolean("isRemember", false);
    }

    public static void saveUserInfo(Context context, User user) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString("id",user.getId());
        editor.putString("name",user.getName());
        editor.putString("email",user.getEmail());
        editor.putString("pincoode",user.getPincode());
        editor.putString("image",user.getProfilePic());
        editor.commit();
    }

    public static User getUserInfo(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        User user = new User(sharedPreferences.getString("id", null), sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null), sharedPreferences.getString("pincoode", null),
                sharedPreferences.getString("image", null));
        return user;
    }
}
