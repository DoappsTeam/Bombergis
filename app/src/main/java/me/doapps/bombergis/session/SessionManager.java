package me.doapps.bombergis.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MiguelGarrafa_2 on 29/05/2015.
 */
public class SessionManager {
    private static final String PREFERENCE_NAME = "entel_preference";
    private int PRIVATE_MODE = 0;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public static final String USER_LOGIN = "userLogin";
    public static final String USER_ID = "userId";
    public static final String USER_RUT = "userRut";
    public static final String USER_NAME = "userName";

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

    public void setUserId(String userId){
        editor.putString(USER_ID, userId);
        editor.commit();
    }
    public String getUserId(){
        return preferences.getString(USER_ID, null);
    }

    public void setUserRut(String userRut){
        editor.putString(USER_RUT, userRut);
        editor.commit();
    }
    public String getUserRut(){
        return preferences.getString(USER_RUT, null);
    }

    public void setUserName(String userName){
        editor.putString(USER_NAME, userName);
        editor.commit();
    }
    public String getUserName(){
        return preferences.getString(USER_NAME, null);
    }
}
