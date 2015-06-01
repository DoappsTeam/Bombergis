package me.doapps.bombergis.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MiguelGarrafa_2 on 29/05/2015.
 */
public class SessionManager {
    private static final String PREFERENCE_NAME = "bombergis_preference";
    private int PRIVATE_MODE = 0;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public static final String USER_LOGIN = "userLogin";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_PASS = "userPass";

    public SessionManager(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void setUserLogin(boolean userLogin){
        editor.putBoolean(USER_LOGIN, userLogin);
        editor.commit();
    }
    public boolean getUserLogin(){
        return preferences.getBoolean(USER_LOGIN, false);
    }

    public void setUserEmail(String userEmail){
        editor.putString(USER_EMAIL, userEmail);
        editor.commit();
    }

    public String getUserEmail(){
        return preferences.getString(USER_EMAIL, null);
    }

    public void setUserPass(String userPass){
        editor.putString(USER_PASS, userPass);
        editor.commit();
    }

    public String getUserPass(){
     return preferences.getString(USER_PASS,null);
    }

}
