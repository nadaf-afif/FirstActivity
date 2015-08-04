package app.roundtable.nepal.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import app.roundtable.nepal.R;

/**
 * Created by afif on 25/7/15.
 */
public class MeetingProcedureActivity extends AppCompatActivity {

    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_procedure);

        mToolBar = (Toolbar) findViewById(R.id.actionToolbar);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.meeting_procedure));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
