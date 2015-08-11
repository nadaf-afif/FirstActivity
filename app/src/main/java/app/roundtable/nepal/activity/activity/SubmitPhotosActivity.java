package app.roundtable.nepal.activity.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.network.ApiClient;
import app.roundtable.nepal.activity.network.ApiUrls;
import app.roundtable.nepal.activity.util.ApplicationPreferences;
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
    private String mImageDescription = "";


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
                   uploadImageToCloud();
                else
                    Toast.makeText(SubmitPhotosActivity.this,getString(R.string.no_image_selected), Toast.LENGTH_SHORT).show();


                break;


        }
    }

    private void uploadImageToCloud() {


        new AsyncTask<String,String, String>(){

            ProgressDialog progressDialog = new ProgressDialog(SubmitPhotosActivity.this);
            ApiClient apiClient = new ApiClient();
            ApplicationPreferences mSharedPreference = new ApplicationPreferences(SubmitPhotosActivity.this);
            Boolean mSuccess = true;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage(getString(R.string.please_wait));
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                String response = null;

                File file = new File(getRealPathFromURI(mUriPath));
                try {

                    FileInputStream inputStream = new FileInputStream(file);
                    JSONObject jsonObject = mCloudinary.uploader().upload(inputStream,Cloudinary.emptyMap());
                    Log.d("UPLOAD STATUS", jsonObject.toString());

                    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("image_name", file.getName()));
                    pairs.add(new BasicNameValuePair("image_desc", mImageDescription));
                    pairs.add(new BasicNameValuePair("member_id", mSharedPreference.getUserId()));

                    response = apiClient.executePostRequestWithHeader(pairs, ApiUrls.GALLERY_UPDATE_API_PATH);

                    JSONObject responseObject = new JSONObject(response);
                    String success = responseObject.getString("success");

                    if(success.equals("false")){

                        mSuccess = false;
                    }


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    mSuccess = false;
                } catch (IOException e) {
                    e.printStackTrace();
                    mSuccess = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                    mSuccess = false;
                }


                return response;

            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if(progressDialog!=null && progressDialog.isShowing())
                    progressDialog.dismiss();


                if(mSuccess)
                    Toast.makeText(SubmitPhotosActivity.this,getString(R.string.photo_uploaded_success), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SubmitPhotosActivity.this,getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();


            }



        }.execute();



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

                intent.putExtra("return-data", true);

                try {

                    startActivityForResult(intent, PICK_CAMERA_IMAGE);

                } catch (ActivityNotFoundException e) {
                }


            }
        });


        alertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {



                try {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    intent.putExtra("crop", "true");
//                    intent.putExtra("aspectX", 150);
//                    intent.putExtra("aspectY", 100);
//                    intent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_GALLERY_IMAGE);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "No application found",
                            Toast.LENGTH_LONG).show();
                    Log.e(e.getClass().getName(), e.getMessage(), e);
                }


            }
        });

        alertDialog.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {


            switch (requestCode) {


                case PICK_CAMERA_IMAGE:

                    if (resultCode == RESULT_OK) {
                        Uri selectedImageUri = data.getData();
                        String filePath = null;

                        try {
                            // OI FILE Manager
                            String filemanagerstring = selectedImageUri.getPath();

                            // MEDIA GALLERY
                            String selectedImagePath = getRealPathFromURI(selectedImageUri);

                            if (selectedImagePath != null) {
                                filePath = selectedImagePath;
                            } else if (filemanagerstring != null) {
                                filePath = filemanagerstring;
                            } else {
                                Toast.makeText(getApplicationContext(), "Unknown path",
                                        Toast.LENGTH_LONG).show();
                                Log.e("Bitmap", "Unknown path");
                            }

                            if (filePath != null) {
                                decodeFile(filePath);
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Internal error",
                                    Toast.LENGTH_LONG).show();
                            Log.e(e.getClass().getName(), e.getMessage(), e);
                        }
                    }
                    break;



                case PICK_GALLERY_IMAGE:

                    if (resultCode == RESULT_OK) {
                        Uri selectedImageUri = data.getData();
                        String filePath = null;

                        try {
                            // OI FILE Manager
                            String filemanagerstring = selectedImageUri.getPath();

                            // MEDIA GALLERY
                            String selectedImagePath = getRealPathFromURI(selectedImageUri);

                            if (selectedImagePath != null) {
                                filePath = selectedImagePath;
                            } else if (filemanagerstring != null) {
                                filePath = filemanagerstring;
                            } else {
                                Toast.makeText(getApplicationContext(), "Unknown path",
                                        Toast.LENGTH_LONG).show();
                                Log.e("Bitmap", "Unknown path");
                            }

                            if (filePath != null) {
                                decodeFile(filePath);
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Internal error",
                                    Toast.LENGTH_LONG).show();
                            Log.e(e.getClass().getName(), e.getMessage(), e);
                        }
                    }
                    break;

            }

        }
    }

    private void showDialogToAddDescrption() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Add Photo Description");
        final EditText descriptionET = new EditText(this);
        descriptionET.setEms(15);
        descriptionET.setTextColor(Color.DKGRAY);
        LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        descriptionET.setLayoutParams(params);
        dialog.setView(descriptionET);

        dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mImageDescription = descriptionET.getText().toString();
                dialog.dismiss();
            }
        });

        dialog.show();

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


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "uploadImage_"+System.currentTimeMillis()  , null);
        return Uri.parse(path);
    }


    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, o2);

        mBrowseImageView.setImageBitmap(bitmap);

        mUploadButton.setVisibility(View.VISIBLE);
        mClickHereTV.setVisibility(View.INVISIBLE);
        mUriPath = getImageUri(this, bitmap);
        showDialogToAddDescrption();

    }

}
