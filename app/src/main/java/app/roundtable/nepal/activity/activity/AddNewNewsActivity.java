package app.roundtable.nepal.activity.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Set;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.TableNameDialogAdapter;
import app.roundtable.nepal.activity.asynktasks.AddNewsAsyncTask;
import app.roundtable.nepal.activity.database.RTNTablesManager;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.databeans.TablesInfoBean;

/**
 * Created by afif on 13/6/15.
 */
public class AddNewNewsActivity extends AppCompatActivity implements View.OnClickListener, Tables.RTNTables{


    private Button mSubmitnewsButton;
    private Toolbar mToolBar;
    private EditText mConcernTableEditText, mHeadLineEditText, mNewsDetailEditText;
    private ImageView mNewsImageView;
    private static int TIME_PICKER_DIALOG = 111, DATE_PICKER_DIALOG = 222;
    public static final int PICK_CAMERA_IMAGE = 011, PICK_GALLERY_IMAGE = 911;
    public ArrayList<TablesInfoBean> tablesData = new ArrayList<>();
    private Set<String > selectedId;
    private TableNameDialogAdapter adapter;
    private String mNewsImagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        initViews();

        Cursor cursor = new RTNTablesManager(this).getTables();
        tablesData = getObject(cursor);
    }

    private void initViews() {

        mSubmitnewsButton = (Button)findViewById(R.id.submitNewsButton);
        mConcernTableEditText = (EditText)findViewById(R.id.concernTablesEditText);
        mNewsImageView = (ImageView) findViewById(R.id.newsPhotoImageView);
        mHeadLineEditText = (EditText) findViewById(R.id.newsHeadlineEditText);
        mNewsDetailEditText = (EditText) findViewById(R.id.newsDetailsEditText);

        mConcernTableEditText.setOnClickListener(this);
        mSubmitnewsButton.setOnClickListener(this);
        mNewsImageView.setOnClickListener(this);
        mToolBar = (Toolbar) findViewById(R.id.actionToolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.add_news_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {

            case R.id.submitNewsButton :

                validData();

                break;

            case R.id.concernTablesEditText :

                ShowTablesDialog();

                break;


            case R.id.newsPhotoImageView :

                chooseSourceDialog();

                break;
        }

    }

    private void validData() {

        String newsHeadline = mHeadLineEditText.getText().toString();
        String newsDetails = mNewsDetailEditText.getText().toString();

        if(newsHeadline.equals("")){

            showToastMessage(getString(R.string.enter_news_headline));

        } else if(newsDetails.equals("")){

            showToastMessage(getString(R.string.enter_news_detail));
        }else if(selectedId.size()< 1){

            showToastMessage(getString(R.string.select_at_least_one_table));
        }else if(mNewsImagePath.equals("")){

            showToastMessage(getString(R.string.please_select_news_photo));
        }else {

            executeAsyncTask(newsHeadline, newsDetails);
        }

    }

    private void executeAsyncTask(String newsHeadline, String newsDetails) {

        AddNewsAsyncTask asyncTask = new AddNewsAsyncTask(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            asyncTask.execute(newsHeadline,newsDetails,selectedId.toString(),mNewsImagePath);
        else
            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, newsHeadline, newsDetails, selectedId.toString(),mNewsImagePath);


    }

    private void showToastMessage(String message) {

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void showConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Request Accepted !");
        builder.setMessage(getString(R.string.confirm_message_text));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finish();
            }
        });
        builder.show();
    }


    private void ShowTablesDialog() {

        final Dialog dialog  = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tablenames);

        ListView mListView = (ListView)dialog.findViewById(R.id.tableNameListView);
        Button doneButton = (Button)dialog.findViewById(R.id.doneButton);

        adapter = new TableNameDialogAdapter(this,tablesData);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                selectedId = adapter.selectedId;
                tablesData = adapter.tableNames;
                mConcernTableEditText.setText(selectedId.size() + " Tables selected");
            }
        });



        dialog.show();

    }


    private ArrayList<TablesInfoBean> getObject(Cursor cursor) {


        ArrayList<TablesInfoBean> tables = new ArrayList<>();
        cursor.moveToFirst();
        TablesInfoBean object1 = new TablesInfoBean();

        object1.setTableName("select all");
        object1.setTableId("0");
        object1.setIsSelected(false);

        tables.add(object1);

        for (int i = 0; i<cursor.getCount(); i++){

            cursor.moveToPosition(i);
            TablesInfoBean object = new TablesInfoBean();

            object.setIsSelected(false);
            object.setTableId(cursor.getString(cursor.getColumnIndex(TABLE_ID)));
            object.setTableName(cursor.getString(cursor.getColumnIndex(TABLE_DESCRIPTION)));
            tables.add(object);
        }

        return tables;

    }





    private void chooseSourceDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Choose Image From");


        alertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // call android default camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
                // ******** code for crop image
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 150);

                try {

                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, PICK_CAMERA_IMAGE);

                } catch (ActivityNotFoundException e) {
                }


            }
        });


        alertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 150);

                try {

                    intent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_GALLERY_IMAGE);

                } catch (ActivityNotFoundException e) {
                }


            }
        });

        alertDialog.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // mUriPath = data.getData();

            switch (requestCode) {


                case PICK_CAMERA_IMAGE:


                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap photo = bundle.getParcelable("data");
                        mNewsImageView.setImageBitmap(photo);
                        saveFile(photo);
                    }

                    break;


                case PICK_GALLERY_IMAGE:

                    Bundle bundle2 = data.getExtras();
                    if (bundle2 != null) {
                        Bitmap photo = bundle2.getParcelable("data");
                        mNewsImageView.setImageBitmap(photo);

                        saveFile(photo);
                    }

                    break;


            }

        }

    }


    private void saveFile(Bitmap photo) {

        File file = new File(
                Environment.getExternalStorageDirectory()
                        + "/RTN/");
        if (!file.isDirectory())
            file.mkdir();
        mNewsImagePath = Environment.getExternalStorageDirectory()
                + "/RTN/news_"+ System.currentTimeMillis()+".png";
        file = new File(mNewsImagePath);
        try {
            photo.compress(Bitmap.CompressFormat.PNG, 100,
                    new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
