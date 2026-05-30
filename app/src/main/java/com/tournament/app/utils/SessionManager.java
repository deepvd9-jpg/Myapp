package com.tournament.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "TournamentAppPref";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_FIREBASE_UID = "firebaseUid";
    private static final String KEY_USERNAME = "username";
    // Add other session keys as needed

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String firebaseUid, String username) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_FIREBASE_UID, firebaseUid);
        editor.putString(KEY_USERNAME, username);
        // Commit changes
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getFirebaseUid() {
        return pref.getString(KEY_FIREBASE_UID, null);
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, null);
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }
}
