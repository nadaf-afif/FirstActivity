package app.roundtable.nepal.activity.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import app.roundtable.nepal.R;

/**
 * Created by afif on 20/6/15.
 */
public class ApplicationPreferences {

    private Context mContext;
    private SharedPreferences mSharedPreferences;


    public static final String GCM_REGISTRATION_ID = "gcm_id";
    public static final String IS_LOGGED_IN = "is_login";

    public ApplicationPreferences(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.preference_name),Context.MODE_PRIVATE);
    }


    public String getGcmRegistrationId(){

        //default id to be removed
        return mSharedPreferences.getString(GCM_REGISTRATION_ID,"16487873dqqdfq65997");

    }


    public void setGcmRegistrationId(String gcmRegistrationId){

        mSharedPreferences.edit().putString(GCM_REGISTRATION_ID,gcmRegistrationId).commit();
    }



    public void setIsLoggedIn(boolean isLoggedIn){

        mSharedPreferences.edit().putBoolean(IS_LOGGED_IN,isLoggedIn).apply();
    }

    public boolean IsLoggedIn(){ return mSharedPreferences.getBoolean(IS_LOGGED_IN, true); };

}
