package app.roundtable.nepal.activity.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.util.ApplicationPreferences;

/**
 * Created by afif on 20/6/15.
 */
public class ApiClient {

    private int mResponseStatusCode;



    public static final String HEADER_API_KEY = "Api-Key";
    public static final String API_KEY = "1234";
    public static final String MEMBER_ID = "Member-Id";


    public String executePostRequestWithHeader(List<NameValuePair> pairs, String apiUrl) throws IOException {

        String response = null;

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(apiUrl);
        post.addHeader(HEADER_API_KEY,API_KEY);
        post.setEntity(new UrlEncodedFormEntity(pairs));

        HttpResponse httpResponse = client.execute(post);
        mResponseStatusCode = httpResponse.getStatusLine().getStatusCode();
        response = EntityUtils.toString(httpResponse.getEntity());

        return response;
    }


    public String executeHttpGetWithHeader(String Url) throws IOException {

        String response = null;

        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(Url);
        httpGet.addHeader(HEADER_API_KEY, API_KEY);

        HttpResponse httpResponse = client.execute(httpGet);

        response = EntityUtils.toString(httpResponse.getEntity());
        mResponseStatusCode = httpResponse.getStatusLine().getStatusCode();

        return response;
    }

    public String executeHttpGetWithHeaderMemberId(String Url, String member_id) throws IOException {

        String response = null;

        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(Url);
        httpGet.addHeader(HEADER_API_KEY, API_KEY);
        httpGet.addHeader(MEMBER_ID, member_id);

        HttpResponse httpResponse = client.execute(httpGet);

        response = EntityUtils.toString(httpResponse.getEntity());
        mResponseStatusCode = httpResponse.getStatusLine().getStatusCode();

        return response;
    }

    public String executePostRequestWithMemberIdHeader(List<NameValuePair> pairs, String apiUrl, String memberId) throws IOException {

        String response = null;

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(apiUrl);
        post.addHeader(HEADER_API_KEY,API_KEY);
        post.addHeader(MEMBER_ID,memberId);
        post.setEntity(new UrlEncodedFormEntity(pairs));

        HttpResponse httpResponse = client.execute(post);
        mResponseStatusCode = httpResponse.getStatusLine().getStatusCode();
        response = EntityUtils.toString(httpResponse.getEntity());

        return response;
    }



    public String executeMultipartUtilityWithHeader(String[] params, Context context, String url) throws IOException {

        String response = null;

        ApplicationPreferences sharedPreference = new ApplicationPreferences(context);

        MultipartUtility builder = new MultipartUtility(url, "UTF-8");

        String user_image = params[7];

        if (user_image != null && !user_image.equals("")) {
            File file = new File(user_image);
            builder.addFilePart("event_image", file);
        }


        builder.addFormField(Tables.Events.EVENT_TYPE,"event");
        builder.addFormField("invites", params[0]);
        builder.addFormField(Tables.Events.IS_SPOUSE, params[1]);
        builder.addFormField(Tables.Events.IS_CHILDREN, params[2]);
        builder.addFormField(Tables.Events.EVENT_NAME, params[3]);
        builder.addFormField(Tables.Events.EVENT_VENUE, params[4]);
        builder.addFormField(Tables.Events.EVENT_DATE, params[5]);
        builder.addFormField(Tables.Events.EVENT_TIME, params[6]);
        builder.addFormField(Tables.Events.EVENT_ADDRESS_LINE, params[8]);
        builder.addFormField(Tables.Events.EVENT_LATITUDE, params[9]);
        builder.addFormField(Tables.Events.EVENT_LONGITUDE, params[10]);
        builder.addFormField("member_id", sharedPreference.getUserId());


        response = builder.finish();

        mResponseStatusCode = builder.getmResponseStatusCode();


        return response;
    }



    public int getResponseStatusCode() {

        return mResponseStatusCode;
    }
}
