package app.roundtable.nepal.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.network.ApiUrls;

/**
 * Created by afif on 13/6/15.
 */
public class NewsDetailsActivity extends AppCompatActivity{

    private Toolbar mToolBar;
    private TextView mHeadLineTextView, mNewsDescriptionTextView;
    private ImageView mNewsImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        initViews();

    }

    private void initViews() {

        mToolBar = (Toolbar) findViewById(R.id.actionToolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.news_deatils_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mHeadLineTextView = (TextView) findViewById(R.id.newsHeadLineTextView);
        mNewsDescriptionTextView = (TextView) findViewById(R.id.newsDesriptionTextView);
        mNewsImageView = (ImageView) findViewById(R.id.newsPhotoImageView);

        Bundle bundle = getIntent().getExtras();

        setValues(bundle);
    }


    private void setValues(Bundle bundle) {

        mHeadLineTextView.setText(bundle.getString(Tables.News.NEWS_HEADLINE));
        mNewsDescriptionTextView.setText(bundle.getString(Tables.News.NEWS_DESCRIPTION));

        String imageUrl = ApiUrls.BASE_URL_PATH + bundle.getString(Tables.News.NEWS_BIG_URL);

        Picasso.with(this).load(imageUrl).into(mNewsImageView);
    }


}
