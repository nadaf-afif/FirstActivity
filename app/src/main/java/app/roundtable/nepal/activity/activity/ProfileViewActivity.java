package app.roundtable.nepal.activity.activity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.UserProfileAdapter;
import app.roundtable.nepal.activity.database.MembersManager;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.databeans.ProfileData;
import app.roundtable.nepal.activity.network.ApiUrls;


/**
 * Created by afif on 4/6/15.
 */

/*
https://gist.github.com/chrisbanes/91ac8a20acfbdc410a68

http://stackoverflow.com/questions/27070079/expand-collapse-lollipop-toolbar-animation-telegram-app

https://mzgreen.github.io/2015/02/28/How-to-hideshow-Toolbar-when-list-is-scrolling(part2)/

https://github.com/Suleiman19/Android-Material-Design-for-pre-Lollipop

* */

public class ProfileViewActivity extends AppCompatActivity implements Tables.Members{


    private ImageView mUserImageView;
    private int mutedColor;
    private String mMemberId;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private MembersManager mManager;
    private Cursor mCursor;
    private RecyclerView mRecyclerView;
    private List<ProfileData> mData= new ArrayList<ProfileData>();

    private String[] mLabels = new String[]{"Table Name : ","Date Of Birth : ","Email : ", "Mobile : ", "Blood Group : ", "City : ", "Gender : ", "Spouse Name : ", "Spouse Date of Birth : "};
    private String[] mValues ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){

            mMemberId = bundle.getString(MEMBER_ID);

        }

        initViews();

    }

    private void initViews() {


        toolbar  = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleListView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mManager = new MembersManager(this);
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

        mCursor = mManager.getMemberDetail(mMemberId);

        mCursor.moveToFirst();
        mValues = new String[]{ mCursor.getString(mCursor.getColumnIndex(MEMBER_ID)),
                                mCursor.getString(mCursor.getColumnIndex(DATE_OF_BIRTH)),
                                mCursor.getString(mCursor.getColumnIndex(EMAIL)),
                                mCursor.getString(mCursor.getColumnIndex(MOBILE)),
                                mCursor.getString(mCursor.getColumnIndex(BLOOD_GROUP)),
                                mCursor.getString(mCursor.getColumnIndex(RESIDENCE_CITY)),
                                mCursor.getString(mCursor.getColumnIndex(GENDER)),
                                mCursor.getString(mCursor.getColumnIndex(SPOUSE_NAME)),
                                mCursor.getString(mCursor.getColumnIndex(SPOUSE_DATE_OF_BIRTH)) };



        String userName = mCursor.getString(mCursor.getColumnIndex(MEMBER_FIRST_NAME)) + " "+ mCursor.getString(mCursor.getColumnIndex(MEMBER_LAST_NAME));
        getSupportActionBar().setTitle(userName);
        collapsingToolbarLayout.setTitle(userName);

        String imageUrl = ApiUrls.BASE_URL_PATH + mCursor.getString(mCursor.getColumnIndex(IMAGE_THUMB_URL));

        Picasso.with(this).load(imageUrl).into(mUserImageView);

        for (int i = 0; i<mValues.length;i++){

            ProfileData profileData = new ProfileData();
            profileData.setLabel(mLabels[i]);
            profileData.setValue(mValues[i]);
            mData.add(profileData);
        }

        UserProfileAdapter adapter = new UserProfileAdapter(this,mData);
        mRecyclerView.setAdapter(adapter);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

         super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       return super.onOptionsItemSelected(item);

    }


}
