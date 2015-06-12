package app.roundtable.nepal.activity.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import app.roundtable.nepal.R;

/**
 * Created by afif on 9/6/15.
 */
public class SubmitPhotosFragment extends Fragment implements View.OnClickListener{


    private ImageView mBrowseImageView;
    private TextView mClickHereTV;
    private Button mUploadButton;
    public static final int PICK_CAMERA_IMAGE = 011, PICK_GALLERY_IMAGE = 911;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_submit_photos,container,false);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBrowseImageView = (ImageView) view.findViewById(R.id.submitPhotoImageView);
        mClickHereTV = (TextView)view.findViewById(R.id.clickHereTextView);
        mUploadButton = (Button)view.findViewById(R.id.uploadButton);

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

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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
                startActivityForResult(galleryIntent , PICK_GALLERY_IMAGE );

            }
        });

        alertDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == getActivity().RESULT_OK){

            switch (requestCode){

                case PICK_CAMERA_IMAGE :

                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    mBrowseImageView.setImageBitmap(bitmap);
                    mUploadButton.setVisibility(View.VISIBLE);
                    mClickHereTV.setVisibility(View.GONE);
                    break;


                case PICK_GALLERY_IMAGE:

                    Uri imageuri = data.getData();
                    Bitmap bitmap1 = BitmapFactory.decodeFile(imageuri.getPath());
                    mBrowseImageView.setImageBitmap(bitmap1);
                    mUploadButton.setVisibility(View.VISIBLE);
                    mClickHereTV.setVisibility(View.GONE);
                    break;


            }

        }
    }
}
