package app.roundtable.nepal.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import app.roundtable.nepal.R;

/**
 * Created by afif on 13/6/15.
 */
public class EventDetailsActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_deatils);

        initViews();

    }

    private void initViews() {

        mToolbar = (Toolbar) findViewById(R.id.actionToolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle(getString(R.string.event_details_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


}
