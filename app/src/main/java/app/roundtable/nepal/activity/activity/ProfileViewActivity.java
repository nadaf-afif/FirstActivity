package app.roundtable.nepal.activity.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.UserProfileAdapter;
import app.roundtable.nepal.activity.databeans.ProfileData;
import app.roundtable.nepal.activity.view.HidingScrollListener;


/**
 * Created by afif on 4/6/15.
 */

/*
https://gist.github.com/chrisbanes/91ac8a20acfbdc410a68

http://stackoverflow.com/questions/27070079/expand-collapse-lollipop-toolbar-animation-telegram-app

https://mzgreen.github.io/2015/02/28/How-to-hideshow-Toolbar-when-list-is-scrolling(part2)/

https://github.com/Suleiman19/Android-Material-Design-for-pre-Lollipop

* */

public class ProfileViewActivity extends AppCompatActivity {


    private ImageView mUserImageView;
    private int mutedColor;
    private List<ProfileData> mData= new ArrayList<ProfileData>();

    private String[] mLabels = new String[]{"Table Name : ","Date Of Birth : ","Email : ", "Mobile : ", "Blood Group : ", "City : ", "Company Name : ", "Company Address : ", "Office Contact: ", "Data Of Joinig : "};
    private String[] mValues = new String[]{"Round Table", "18th Jully 1991", "nadafafif@gmail.com", "181521546543", "B Positive", "Pune", "Parity Cube", "Pune", "2165445645", "1st Nov 2013"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        final Toolbar toolbar= (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Afif Nadaf");
        final AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Afif Nadaf");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.events_1);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                collapsingToolbarLayout.setContentScrimColor(mutedColor);
            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for (int i = 0; i<mValues.length;i++){

            ProfileData profileData = new ProfileData();
            profileData.setLabel(mLabels[i]);
            profileData.setValue(mValues[i]);
            mData.add(profileData);
        }


        UserProfileAdapter adapter = new UserProfileAdapter(this,mData);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public static int getToolBarHeight(){

        int height = getToolBarHeight();
        return  height;

    }
}
