package app.roundtable.nepal.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import app.roundtable.nepal.R;

/**
 * Created by afif on 13/6/15.
 */
public class ConveyersActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conveyers);

        initViews();
    }



    private void initViews() {

        Toolbar mToolBar = (Toolbar) findViewById(R.id.actionToolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.board_members_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
