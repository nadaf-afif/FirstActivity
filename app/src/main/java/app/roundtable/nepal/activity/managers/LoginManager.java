package app.roundtable.nepal.activity.managers;

import android.content.Context;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.roundtable.nepal.activity.database.Manager;
import app.roundtable.nepal.activity.network.ApiClient;
import app.roundtable.nepal.activity.network.ApiUrls;
import app.roundtable.nepal.activity.util.ApplicationPreferences;

/**
 * Created by afif on 20/6/15.
 */
public class LoginManager extends Manager {


    private Context mContext;
    private ApiClient mApiClient;

    public LoginManager(Context mContext) {

        this.mContext = mContext;
        mApiClient = new ApiClient();
    }


    public String doEmailLogin(String email, String password) throws IOException {

        ApplicationPreferences mPreferences = new ApplicationPreferences(mContext);

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        pairs.add(new BasicNameValuePair("email", email));
        pairs.add(new BasicNameValuePair("otp", password));
        pairs.add(new BasicNameValuePair("os","gcm"));
        pairs.add(new BasicNameValuePair("token", mPreferences.getGcmRegistrationId()));


        String response = mApiClient.executePostRequestWithHeader(pairs, ApiUrls.LOGIN_API_PATH);

        return response;

    }



    public String getOTPRequest(String email) throws IOException {

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        pairs.add(new BasicNameValuePair("email",email));

        String response = mApiClient.executePostRequestWithHeader(pairs,ApiUrls.REQUEST_API_PATH);

        return response;
    }


    public String requestNewAccess(String userName, String email, String table_name) throws IOException {


        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("email",email));
        pairs.add(new BasicNameValuePair("name",userName));
        pairs.add(new BasicNameValuePair("table_name",table_name));

        String response = mApiClient.executePostRequestWithHeader(pairs, ApiUrls.REQUEST_ACCESS_API_PATH);

        return response;
    }


}
