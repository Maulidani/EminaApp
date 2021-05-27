package emina.eminaapp.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

public class SessionManager {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String EMAIL = "Semail";
    public static final String PASSWORD = "Spassword";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void CreatLoginSession(String email, String password) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public HashMap<String, String> getDataCommit() {
        HashMap<String, String> data = new HashMap<>();
        data.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        data.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        return data;
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void logOut() {
        editor.clear();
        editor.commit();
    }
}
