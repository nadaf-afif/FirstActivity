package app.roundtable.nepal.activity.activity;

import android.app.ProgressDialog;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.TableMembersListAdapter;
import app.roundtable.nepal.activity.network.ApiClient;

/**
 * Created by afif on 3/6/15.
 */
public class SearchMembersActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private Toolbar mToolBar;
    private Spinner mSpinner, mBloodGroupSpinner;
    private EditText mKeyEditText;
    private String mTypeSelected, mBloodGroupSelected, mSearchKey;
    private int mSelectedPosition, mBloodGroupPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_members);


        initViews();
    }

    private void initViews() {

        mRecyclerView = (RecyclerView) findViewById(R.id.seachMemberRecyclerview);
        mToolBar = (Toolbar) findViewById(R.id.actionToolbar);
        mSpinner = (Spinner) findViewById(R.id.searchTypeSpinner);
        mKeyEditText = (EditText) findViewById(R.id.searchKeyEditText);
        mBloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.search_members_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position > 0 ){

                    mBloodGroupSelected = (String) mBloodGroupSpinner.getItemAtPosition(position);
                    mBloodGroupPosition = position;
                    executeAsyncTask();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {

                    mTypeSelected = (String) mSpinner.getItemAtPosition(position);
                    mSelectedPosition = position;
                    setInputType(position);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mKeyEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    searchByKey();
                }

                return false;
            }
        });

    }

    private void executeAsyncTask() {



    }

    private void searchByKey() {

        if(mSelectedPosition == 0){

            showToastMessage(getString(R.string.please_select_search_type));

        }else if(mSelectedPosition== 6){




        }else {

            String searchKey = mKeyEditText.getText().toString();
            if(searchKey.equals("")){
                showToastMessage(getString(R.string.enter_search_key));

            }else if(searchKey.length()<4){

                showToastMessage(getString(R.string.please_enter_key_length));
            }

        }

    }

    private void showToastMessage(String message) {

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void setInputType(int position) {

        switch (position){

            case 1 :
                mBloodGroupSpinner.setVisibility(View.GONE);
                mKeyEditText.setVisibility(View.VISIBLE);
                mKeyEditText.setText("");
                mKeyEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                mKeyEditText.setHint(getString(R.string.enter_search_name));
                break;

            case 2 :
                mBloodGroupSpinner.setVisibility(View.GONE);
                mKeyEditText.setVisibility(View.VISIBLE);
                mKeyEditText.setText("");
                mKeyEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE);
                mKeyEditText.setHint(getString(R.string.enter_search_mobile));
                break;

            case 3 :
                mBloodGroupSpinner.setVisibility(View.GONE);
                mKeyEditText.setVisibility(View.VISIBLE);
                mKeyEditText.setText("");
                mKeyEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                mKeyEditText.setHint(getString(R.string.enter_email));
                break;

            case 4 :
                mBloodGroupSpinner.setVisibility(View.GONE);
                mKeyEditText.setVisibility(View.VISIBLE);
                mKeyEditText.setText("");
                mKeyEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                mKeyEditText.setHint(getString(R.string.enter_city));
                break;

            case 5 :
                mBloodGroupSpinner.setVisibility(View.GONE);
                mKeyEditText.setVisibility(View.VISIBLE);
                mKeyEditText.setText("");
                mKeyEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                mKeyEditText.setHint(getString(R.string.enter_date_of_birth));
                break;

            case 6 :
                mKeyEditText.setVisibility(View.GONE);
                mBloodGroupSpinner.setVisibility(View.VISIBLE);
                break;
        }

    }


}
