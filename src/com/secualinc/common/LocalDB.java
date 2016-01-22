package com.secualinc.common;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalDB {
    private static LocalDB mInstance = new LocalDB();
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;
    private static Context mContext;


    /**
     * The only way to get the only one instance
     * @param context
     * @return
     */
    public static LocalDB getInstance(Context context) {
        if (null == mContext) {
            mContext = context;
        }
        if (null == mSharedPreferences) {
            mSharedPreferences = mContext.getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        }
        return mInstance;
    }
    

    /**
     * Private constructor, force user to call getInstance
     */
    private LocalDB() {
    }

    /**
     * Save token to use in every api calls
     * @param token
     */
    public void saveToken(String token) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(Constants.ACCESS_TOKEN, token);
        mEditor.commit();
    }

    /**
     * Get token
     * @return
     */
    public String getToken() {
        return mSharedPreferences.getString(Constants.ACCESS_TOKEN, "");
    }

    /**
     * Save client to use in every api calls
     * @param client
     */
    public void saveClient(String client) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(Constants.CLIENT, client);
        mEditor.commit();
    }

    /**
     * Get client
     * @return
     */
    public String getClient() {
        return mSharedPreferences.getString(Constants.CLIENT, "");
    }

    /**
     * Save uid to use in every api calls
     * @param uid
     */
    public void saveUID(String uid) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(Constants.UID, uid);
        mEditor.commit();
    }

    /**
     * Get uid
     * @return
     */
    public String getUID() {
        return mSharedPreferences.getString(Constants.UID, "");
    }
}
