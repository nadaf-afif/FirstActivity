package app.roundtable.nepal.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import app.roundtable.nepal.R;

/**
 * Created by afif on 13/6/15.
 */
public class AimsAndObjectiveActivity extends AppCompatActivity {

    private Toolbar mToolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aims_objective);

        initViews();
    }

    private void initViews() {

        mToolBar = (Toolbar) findViewById(R.id.actionToolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.aims_and_objective));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


}
