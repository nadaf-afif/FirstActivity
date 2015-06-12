package app.roundtable.nepal.activity.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import app.roundtable.nepal.R;

/**
 * Created by afif on 9/6/15.
 */
public class SubmitPhotosActivity extends AppCompatActivity implements View.OnClickListener{


    private ImageView mBrowseImageView;
    private TextView mClickHereTV;
    private Button mUploadButton;
    public static final int PICK_CAMERA_IMAGE = 011, PICK_GALLERY_IMAGE = 911;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_submit_photos);

        initView();
    }

    private void initView() {




        mBrowseImageView = (ImageView) findViewById(R.id.submitPhotoImageView);
        mClickHereTV = (TextView)findViewById(R.id.clickHereTextView);
        mUploadButton = (Button)findViewById(R.id.uploadButton);

        mBrowseImageView.setOnClickListener(this);
        mClickHereTV.setOnClickListener(this);
        mUploadButton.setOnClickListener(this);

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



                break;


        }
    }

    private void chooseSourceDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Choose Image From");


        alertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra("crop", "true");
                startActivityForResult(intent, PICK_CAMERA_IMAGE);
            }
        });


        alertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_GALLERY_IMAGE);

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

                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    mBrowseImageView.setImageBitmap(bitmap);
                    mUploadButton.setVisibility(View.VISIBLE);
                    mClickHereTV.setVisibility(View.INVISIBLE);
                    break;


                case PICK_GALLERY_IMAGE:

                    Uri imageuri = data.getData();
                    mBrowseImageView.setImageURI(imageuri);
                    mUploadButton.setVisibility(View.VISIBLE);
                    mClickHereTV.setVisibility(View.INVISIBLE);
                    break;


            }

        }
    }
}
