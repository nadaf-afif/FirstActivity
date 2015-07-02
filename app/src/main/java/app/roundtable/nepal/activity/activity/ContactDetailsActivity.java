package app.roundtable.nepal.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import app.roundtable.nepal.R;

/**
 * Created by afif on 13/6/15.
 */
public class ContactDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        initViews();
    }


    private void initViews() {

        Toolbar mToolBar = (Toolbar) findViewById(R.id.actionToolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.contact_us_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
