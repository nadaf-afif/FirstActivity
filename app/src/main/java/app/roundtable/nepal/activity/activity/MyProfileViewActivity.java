package app.roundtable.nepal.activity.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.UserProfileAdapter;
import app.roundtable.nepal.activity.database.MembersManager;
import app.roundtable.nepal.activity.databeans.ProfileData;
import app.roundtable.nepal.activity.network.ApiClient;
import app.roundtable.nepal.activity.network.ApiUrls;
import app.roundtable.nepal.activity.util.ApplicationPreferences;

/**
 * Created by afif on 8/7/15.
 */
public class MyProfileViewActivity extends AppCompatActivity {


    private ImageView mUserImageView;
    private int mutedColor;
    private String mMemberId;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private MembersManager mManager;
    private RecyclerView mRecyclerView;
    private String mTableName;
    UserProfileAdapter mAdapter;
    private List<ProfileData> mData= new ArrayList<ProfileData>();
    private ApplicationPreferences mSharedPreferences;

    private String[] mLabels = new String[]{"Table Name : ", "Mobile : ", "Email : ", "Company : ","Address : ", "City : ","Office Phone : ", "Blood Group : ", "Date Of Birth : ",  "Spouse Name : ", "Spouse DOB : ", "Anniversary : "};
    private String[] mValues ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        initView();
        getUserInfo();
    }

    private void getUserInfo() {

        new AsyncTask<String,String,String>(){

            ProgressDialog mProgressDialog;
            ApiClient mApiClient;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(MyProfileViewActivity.this);
                mProgressDialog.setMessage(getString(R.string.please_wait));
                mProgressDialog.show();
                mApiClient= new ApiClient();
            }


            @Override
            protected String doInBackground(String... params) {

                String response = null;

                try {
                    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("member_id",mSharedPreferences.getUserId()));
                    response = mApiClient.executePostRequestWithMemberIdHeader(pairs, ApiUrls.PROFILE_INFO_API,mSharedPreferences.getUserId());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return response;
            }


            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if(mProgressDialog!=null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("PROFILE RESPONSE",result);
                    JSONObject jsonObject = new JSONObject(result);
                    String success = jsonObject.getString("success");
                    if(success.equals("true")){
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        saveDataInSharedPrefernce(dataObject);
                        setDataInAdapter();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.execute();
    }

    private void setUpdatedData() {

        mData.clear();

        String imageUrl = ApiUrls.BASE_URL_PATH + mSharedPreferences.getUserProfileImage();

        Picasso.with(this).load(imageUrl).skipMemoryCache().into(mUserImageView);

        mValues = new String[]{mSharedPreferences.getTableCode(), mSharedPreferences.getMobile(), mSharedPreferences.getUserEmail(), mSharedPreferences.getCompany(), mSharedPreferences.getAddress(),
                mSharedPreferences.getResidenceCity(), mSharedPreferences.getOfficePhone(), mSharedPreferences.getUserBloodGroup(), mSharedPreferences.getUserDOB(), mSharedPreferences.getSpouseName(),
                mSharedPreferences.getSpouseDOB(), mSharedPreferences.getAnniversaryDate()};

        for (int i = 0; i < mValues.length; i++) {

            ProfileData profileData = new ProfileData();
            profileData.setLabel(mLabels[i]);
            profileData.setValue(mValues[i]);
            mData.add(profileData);
        }

    }

    private void setDataInAdapter() {

        if (mAdapter == null) {
            mAdapter = new UserProfileAdapter(this, mData);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            setUpdatedData();
            mAdapter.notifyDataSetChanged();
        }
    }

    private void saveDataInSharedPrefernce(JSONObject dataObject) throws JSONException {

        JSONObject jsonObject = dataObject.getJSONObject("member_info");
        JSONObject detailsObject = jsonObject.getJSONObject("details");

        mSharedPreferences.setAddress(detailsObject.getString("address"));
        mSharedPreferences.setCompany(detailsObject.getString("company"));
        mSharedPreferences.setTableName(detailsObject.getString("table_name"));
        mSharedPreferences.setAnniversaryDate(detailsObject.getString("anniversary_date"));
        mSharedPreferences.setOfficeCity(detailsObject.getString("office_city"));
        mSharedPreferences.setUserState(detailsObject.getString("state"));
        mSharedPreferences.setUserResidencePhone(detailsObject.getString("res_phone"));
        mSharedPreferences.setUserMobile(detailsObject.getString("mobile"));
        mSharedPreferences.setUserProfileImage(detailsObject.getString("image_thumb_url"));
        mSharedPreferences.setUserName(detailsObject.getString("fname"));
        mSharedPreferences.setUserBloodGroup(detailsObject.getString("blood_group"));
        mSharedPreferences.setUserDesignation(detailsObject.getString("designation"));
        mSharedPreferences.setUserDOB(detailsObject.getString("dob"));
        mSharedPreferences.setUserLastName(detailsObject.getString("lname"));
        mSharedPreferences.setUserEmail(detailsObject.getString("email"));
        mSharedPreferences.setUserGender(detailsObject.getString("gender"));
        mSharedPreferences.setResidenceCity(detailsObject.getString("res_city"));
        mSharedPreferences.setSpouseDOB(detailsObject.getString("spouse_dob"));
        mSharedPreferences.setOfficePhone(detailsObject.getString("office_phone"));
        mSharedPreferences.setSpouseName(detailsObject.getString("spouse_name"));
        mSharedPreferences.setTableCode(detailsObject.getString("table_code"));
        mSharedPreferences.setTableId(detailsObject.getString("table_id"));
    }

    private void initView() {

        toolbar  = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mSharedPreferences = new ApplicationPreferences(this);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleListView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mManager = new MembersManager(this, mTableName);
        mUserImageView = (ImageView) findViewById(R.id.userImageView);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.splash_logo);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                collapsingToolbarLayout.setContentScrimColor(mutedColor);
            }
        });

        getSupportActionBar().setTitle(mSharedPreferences.getUserName());
        collapsingToolbarLayout.setTitle(mSharedPreferences.getUserName());

        setUpdatedData();
        setDataInAdapter();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mUserImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mSharedPreferences.getProfileUpdated()) {
            String imagePath = ApiUrls.BASE_URL_PATH + mSharedPreferences.getUserProfileImage();
            Picasso.with(this).load(imagePath).skipMemoryCache().into(mUserImageView);
            setDataInAdapter();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){

            case R.id.action_edit :

                Intent intent = new Intent(this, EditProfileActivity.class);
                startActivity(intent);
                return true ;
        }

        return true;
    }

}
