package app.roundtable.nepal.activity.managers;

import android.content.Context;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.database.Manager;
import app.roundtable.nepal.activity.network.ApiClient;
import app.roundtable.nepal.activity.network.ApiUrls;
import app.roundtable.nepal.activity.network.MultipartUtility;
import app.roundtable.nepal.activity.util.ApplicationPreferences;

/**
 * Created by afif on 20/6/15.
 */
public class LoginManager extends Manager {


    private Context mContext;
    private ApiClient mApiClient;
    private ApplicationPreferences mSharedPreferences;

    public LoginManager(Context context) {
        this.mContext = context;
        mApiClient = new ApiClient();
        mSharedPreferences= new ApplicationPreferences(mContext);
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


    public void saveUserInfo(String response) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);

        JSONObject dataObject = jsonObject.getJSONObject("data");

        JSONObject memberInfo = dataObject.getJSONObject("member_info");

        JSONObject details = memberInfo.getJSONObject("details");

        mSharedPreferences.setIsLoggedIn(true);
        mSharedPreferences.setUserId(details.getString("member_id"));
        mSharedPreferences.setUserName(details.getString("fname"));
        mSharedPreferences.setUserLastName(details.getString("lname"));
        mSharedPreferences.setUserGender(details.getString("gender"));
        mSharedPreferences.setUserMobile(details.getString("mobile"));
        mSharedPreferences.setUserEmail(details.getString("email"));
        mSharedPreferences.setUserBloodGroup(details.getString("blood_group"));
        mSharedPreferences.setSpouseName(details.getString("spouse_name"));
        mSharedPreferences.setUserDOB(details.getString("dob"));
        mSharedPreferences.setSpouseDOB(details.getString("spouse_dob"));
        mSharedPreferences.setUserProfileImage(details.getString("image_big_url"));
        mSharedPreferences.setUserResidencePhone(details.getString("res_phone"));
        mSharedPreferences.setOfficePhone(details.getString("office_phone"));
        mSharedPreferences.setUserDesignation(details.getString("designation"));
        mSharedPreferences.setResidenceCity(details.getString("res_city"));
        mSharedPreferences.setOfficeCity(details.getString("office_city"));
        mSharedPreferences.setUserState(details.getString("state"));
        mSharedPreferences.setAnniversaryDate(details.getString("anniversary_date"));
        mSharedPreferences.setTableId(details.getString("table_id"));
        mSharedPreferences.setTableCode(details.getString("table_code"));
        mSharedPreferences.setTableName(details.getString("table_name"));
        mSharedPreferences.setAddress(details.getString("address"));
        mSharedPreferences.setCompany(details.getString("company"));
    }

    public String updateProfile(String[] params) throws IOException {

        String response = null;

        MultipartUtility builder = new MultipartUtility(ApiUrls.EDIT_PROFILE_API_PATH,"UTF-8");

        String imagePath = params[0];

        if(imagePath != null && !imagePath.equals("")){
            File file = new File(imagePath);
            builder.addFilePart("profile_image", file);
        }

        builder.addFormField("fname", params[1]);
        builder.addFormField("lname", params[2]);
        builder.addFormField("email", params[3]);
        builder.addFormField("blood_group", params[4]);
        builder.addFormField("mobile", params[5]);
        builder.addFormField("spouse_name", params[6]);
        builder.addFormField("dob", params[7]);
        builder.addFormField("spouse_dob", params[8]);
        builder.addFormField("anniversary_date", params[9]);
        //builder.addFormField("res_phone", params[11]);
        builder.addFormField("office_phone", params[10]);
        //builder.addFormField("designation", params[13]);
        builder.addFormField("res_city", params[11]);
        //builder.addFormField("office_city", params[15]);
        //builder.addFormField("state", params[16]);
        builder.addFormField("company", params[12]);
        builder.addFormField("address", params[13]);
        builder.addFormField("gender", params[14]);
        builder.addFormField("member_id", mSharedPreferences.getUserId());
        builder.addFormField("table_id", mSharedPreferences.getTableId());

        response = builder.finish();

        return response;
    }

    public void saveupdatedData(String response) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);

        JSONObject dataObject = jsonObject.getJSONObject("data");

        JSONObject details = dataObject.getJSONObject("updated_info");

        mSharedPreferences.setUserId(details.getString("member_id"));
        mSharedPreferences.setUserName(details.getString("fname"));
        mSharedPreferences.setUserLastName(details.getString("lname"));
        mSharedPreferences.setUserGender(details.getString("gender"));
        mSharedPreferences.setUserMobile(details.getString("mobile"));
        mSharedPreferences.setUserEmail(details.getString("email"));
        mSharedPreferences.setUserBloodGroup(details.getString("blood_group"));
        mSharedPreferences.setSpouseName(details.getString("spouse_name"));
        mSharedPreferences.setUserDOB(details.getString("dob"));
        mSharedPreferences.setSpouseDOB(details.getString("spouse_dob"));
        mSharedPreferences.setUserProfileImage(details.getString("image_big_url"));
        mSharedPreferences.setUserResidencePhone(details.getString("res_phone"));
        mSharedPreferences.setOfficePhone(details.getString("office_phone"));
        mSharedPreferences.setUserDesignation(details.getString("designation"));
        mSharedPreferences.setResidenceCity(details.getString("res_city"));
        mSharedPreferences.setOfficeCity(details.getString("office_city"));
        mSharedPreferences.setUserState(details.getString("state"));
        mSharedPreferences.setAnniversaryDate(details.getString("anniversary_date"));
        mSharedPreferences.setTableId(details.getString("table_id"));
        mSharedPreferences.setCompany(details.getString("company"));
        mSharedPreferences.setAddress(details.getString("address"));

        mSharedPreferences.setProfileUpdated(true);
    }
}
