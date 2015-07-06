package app.roundtable.nepal.activity.database;

import org.json.JSONException;
import org.json.JSONObject;

import app.roundtable.nepal.activity.network.ApiClient;

/**
 * Created by afif on 20/6/15.
 */
public class Manager {


    public String getErrorMessage(String response) {

        JSONObject jsonObject = null;
        try {

            jsonObject = new JSONObject(response);

            JSONObject errorJson = jsonObject.getJSONObject("error");
            String errorMessage = errorJson.getString("msg");
            return errorMessage;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String getSuccessMessage(String response){


        JSONObject jsonObject = null;
        try {

            jsonObject = new JSONObject(response);

            JSONObject errorJson = jsonObject.getJSONObject("data");
            String successMessage = errorJson.getString("msg");
            return successMessage;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getResponseStatusCode(String response) {

        JSONObject jsonObject = null;
        try {

            jsonObject = new JSONObject(response);

            JSONObject errorJson = jsonObject.getJSONObject("error");
            int code = errorJson.getInt("code");
            return code;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0 ;
    }

    public String getSuccessString(String response) {

        JSONObject jsonObject = null;
        try {

            jsonObject = new JSONObject(response);

            String success = jsonObject.getString("success");
            return success;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
