package helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by whdinata on 11/8/15.
 */
public class UrbanRootSharedPreferenceHelper {
    private static final String PREF_NAME = "Urban Root";
    public static final String EMAIL = "email";
    public static final String LOGIN = "login";

    public static void putBoolean(Context context, String name, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String name){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(name, false);
    }

    public static void putString(Context context, String name, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static String getString(Context context, String name){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(name, "");
    }
}
