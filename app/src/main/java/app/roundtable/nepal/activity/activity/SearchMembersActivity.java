package app.roundtable.nepal.activity.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.TableMembersListAdapter;

/**
 * Created by afif on 3/6/15.
 */
public class SearchMembersActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private Toolbar mToolBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_members);


        initViews();
    }

    private void initViews() {

        mRecyclerView = (RecyclerView) findViewById(R.id.seachMemberRecyclerview);
        mToolBar = (Toolbar) findViewById(R.id.actionToolbar);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.search_members_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


}
