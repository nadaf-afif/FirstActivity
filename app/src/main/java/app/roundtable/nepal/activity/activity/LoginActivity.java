package app.roundtable.nepal.activity.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import app.roundtable.nepal.R;

/**
 * Created by afif on 2/6/15.
 */
public class LoginActivity extends Activity implements View.OnClickListener{


    private EditText mEmailEditText, mPasswordEditText;
    private Button mLoginButton;
    private TextView mGetOTPTextView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

    }

    private void initViews() {

        mEmailEditText = (EditText)findViewById(R.id.emailEditText);
        mPasswordEditText = (EditText)findViewById(R.id.passwordEditText);
        mLoginButton = (Button)findViewById(R.id.loginButton);
        mGetOTPTextView = (TextView)findViewById(R.id.getOTPTextView);
        mLoginButton.setOnClickListener(this);
        mGetOTPTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.getOTPTextView :
                showEmailDialog();
                break;

            case R.id.loginButton :
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    private void showEmailDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Request Access");
        dialog.setContentView(R.layout.dialog_get_otp);
        dialog.getWindow().setLayout(getWindowManager().getDefaultDisplay().getWidth(), (int) (getWindowManager().getDefaultDisplay().getHeight()/2.5));

        Button doneButton = (Button)dialog.findViewById(R.id.doneButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
