package app.roundtable.nepal.activity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.util.ApplicationPreferences;


public class FirstActivity extends Activity {

    private ApplicationPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mSharedPreferences = new ApplicationPreferences(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                if(mSharedPreferences.IsLoggedIn()) {


                    Intent intent = new Intent(FirstActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                }else {


                    Intent intent = new Intent(FirstActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }).start();


    }



}
