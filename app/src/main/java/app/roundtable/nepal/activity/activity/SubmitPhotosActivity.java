package app.roundtable.nepal.activity.activity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudinary.Cloudinary;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.util.Constants;

/**
 * Created by afif on 9/6/15.
 */
public class SubmitPhotosActivity extends AppCompatActivity implements View.OnClickListener{


    private ImageView mBrowseImageView;
    private TextView mClickHereTV;
    private Button mUploadButton;
    private Toolbar mToolBar;
    public static final int PICK_CAMERA_IMAGE = 011, PICK_GALLERY_IMAGE = 911;
    private Uri mUriPath;
    private Cloudinary mCloudinary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_submit_photos);

        initView();
    }

    private void initView() {

        mToolBar = (Toolbar) findViewById(R.id.actionToolbar);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.submit_photos_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBrowseImageView = (ImageView) findViewById(R.id.submitPhotoImageView);
        mClickHereTV = (TextView)findViewById(R.id.clickHereTextView);
        mUploadButton = (Button)findViewById(R.id.uploadButton);

        mBrowseImageView.setOnClickListener(this);
        mClickHereTV.setOnClickListener(this);
        mUploadButton.setOnClickListener(this);


        Map config = new HashMap();
        config.put("cloud_name", Constants.CLOUD_NAME);
        config.put("api_key",Constants.CLOUD_API_KEY);
        config.put("api_secret",Constants.CLOUD_API_SECRET);

        mCloudinary = new Cloudinary(config);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){


            case R.id.submitPhotoImageView :

                chooseSourceDialog();

                break;


            case R.id.clickHereTextView :

                chooseSourceDialog();

                break;


            case R.id.uploadButton :

                if (mUriPath != null)
                   new UploadImageAsync().execute();

                break;


        }
    }

    private void uploadImageToCloud() {

        File file = new File(getRealPathFromURI(mUriPath));
        try {

            FileInputStream inputStream = new FileInputStream(file);
            JSONObject jsonObject = mCloudinary.uploader().upload(inputStream,Cloudinary.emptyMap());
            Log.d("UPLOAD STATUS",jsonObject.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

            mUriPath = data.getData();

            switch (requestCode) {


                case PICK_CAMERA_IMAGE:


                    Bundle bundle = data.getExtras();
                        if(bundle!=null)
                        {
                            Bitmap photo = bundle.getParcelable("data");
                            mBrowseImageView.setImageBitmap(photo);
                            mUploadButton.setVisibility(View.VISIBLE);
                             mClickHereTV.setVisibility(View.INVISIBLE);

                        }


//                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                    mBrowseImageView.setImageBitmap(bitmap);
//                    mUploadButton.setVisibility(View.VISIBLE);
//                    mClickHereTV.setVisibility(View.INVISIBLE);
                    break;


                case PICK_GALLERY_IMAGE:

                    Bundle bundle2 = data.getExtras();
                    if(bundle2!=null)
                    {
                        Bitmap photo = bundle2.getParcelable("data");
                        mBrowseImageView.setImageBitmap(photo);
                        mUploadButton.setVisibility(View.VISIBLE);
                        mClickHereTV.setVisibility(View.INVISIBLE);
                    }

//                    Uri imageuri = data.getData();
//                    mBrowseImageView.setImageURI(imageuri);
//                    mUploadButton.setVisibility(View.VISIBLE);
//                    mClickHereTV.setVisibility(View.INVISIBLE);
                    break;


            }

        }
    }



    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private class UploadImageAsync extends AsyncTask<String, String, String>{


        @Override
        protected String doInBackground(String... params) {

            uploadImageToCloud();

            return null;
        }
    }

}
