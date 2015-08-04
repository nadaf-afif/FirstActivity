package app.roundtable.nepal.activity.activity;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.asynktasks.EditProfileAsyncTask;
import app.roundtable.nepal.activity.network.ApiUrls;
import app.roundtable.nepal.activity.util.ApplicationPreferences;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by afif on 11/7/15.
 */
public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private ApplicationPreferences mSharedPreferences;
    private CircleImageView mUserProfileImageView;
    private Button mUpdateButton;
    private EditText mFirstNameEditText, mLastNameEditText, mEmailEditText, mMobileEditText, mOfficePhoneEditText, mSpouseNameEditText,
                     mDateOfBirthEditText, mSpouseDateOfBirthEditText, mResidenceCityEditText, mDesignationEditText, mResidencePhoneEditText,
                     mAnniversaryEditText, mStateEditText, mOfficeCityEditText, mAddressEdiText, mCompanyEditText;
    private Spinner mBloodGroupSpinner, mGenderSpinner;
    private Toolbar mToolBar;
    public static final int PICK_CAMERA_IMAGE = 011, PICK_GALLERY_IMAGE = 911;
    private String mUserImagePath;
    private int mYear, mMonth, mDay;
    private String mToday = null;
    private int mSelectedGender = 0, mSelectedBloodGroup = 0;
    private EditProfileAsyncTask mAsynTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mSharedPreferences = new ApplicationPreferences(this);

        mToolBar = (Toolbar) findViewById(R.id.actionToolbar);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(mSharedPreferences.getUserName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        setCurrentCalender();
        initViews();
        setValuesToViews();

    }

    private void setValuesToViews() {

        mFirstNameEditText.setText(mSharedPreferences.getUserName());
        mLastNameEditText.setText(mSharedPreferences.getUserLastName());
        mEmailEditText.setText(mSharedPreferences.getUserEmail());
        mMobileEditText.setText(mSharedPreferences.getMobile());
        mOfficePhoneEditText.setText(mSharedPreferences.getOfficePhone());
        mOfficeCityEditText.setText(mSharedPreferences.getOfficeCity());
        mSpouseNameEditText.setText(mSharedPreferences.getSpouseName());
        mSpouseDateOfBirthEditText.setText(mSharedPreferences.getSpouseDOB());
        mDateOfBirthEditText.setText(mSharedPreferences.getSpouseDOB());
        mResidenceCityEditText.setText(mSharedPreferences.getResidenceCity());
        mResidencePhoneEditText.setText(mSharedPreferences.getUserResidencePhone());
        mDesignationEditText.setText(mSharedPreferences.getUserDesignation());
        mAnniversaryEditText.setText(mSharedPreferences.getAnniversaryDate());
        mStateEditText.setText(mSharedPreferences.getUserState());
        mCompanyEditText.setText(mSharedPreferences.getCompany());
        mAddressEdiText.setText(mSharedPreferences.getAddress());

        mGenderSpinner.setSelection(getItemPosition(mSharedPreferences.getUserGender()));

        ArrayAdapter<String> adapter = (ArrayAdapter<String>) mBloodGroupSpinner.getAdapter();
        mSelectedBloodGroup = adapter.getPosition(mSharedPreferences.getUserBloodGroup());
        mBloodGroupSpinner.setSelection(mSelectedBloodGroup);

        mToday = mYear+"-"+(mMonth+1)+"-"+mDay;

    }

    private int getItemPosition(String userGender) {

        if (userGender.equalsIgnoreCase("male")) {
            mSelectedGender = 1;
            return 1;
        } else if (userGender.equalsIgnoreCase("female")){
            mSelectedGender = 2;
            return 2;
        }
        return 0;
    }


    private void initViews() {

        mFirstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        mLastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        mEmailEditText= (EditText) findViewById(R.id.emailAddressEditText);
        mMobileEditText = (EditText) findViewById(R.id.mobileNumberEditText);
        mOfficePhoneEditText = (EditText) findViewById(R.id.officePhoneEditText);
        mOfficeCityEditText = (EditText) findViewById(R.id.officeCityEditText);
        mSpouseNameEditText = (EditText) findViewById(R.id.userSpouseNameEditText);
        mSpouseDateOfBirthEditText = (EditText) findViewById(R.id.spouseDateOfBirthEditText);
        mDateOfBirthEditText = (EditText) findViewById(R.id.dateOfBirthEditText);
        mResidenceCityEditText = (EditText) findViewById(R.id.residenceCityEditText);
        mDesignationEditText = (EditText) findViewById(R.id.designationEditText);
        mResidencePhoneEditText = (EditText) findViewById(R.id.residencePhoneEditText);
        mAnniversaryEditText = (EditText) findViewById(R.id.anniversaryEditText);
        mStateEditText = (EditText) findViewById(R.id.stateEditText);
        mCompanyEditText = (EditText)findViewById(R.id.companyEditText);
        mAddressEdiText = (EditText) findViewById(R.id.addressEditText);

        mBloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);
        mGenderSpinner = (Spinner) findViewById(R.id.genderSpinner);

        mUpdateButton = (Button) findViewById(R.id.updateProfileButton);

        mUserProfileImageView = (CircleImageView) findViewById(R.id.userProfileImageView);
        mUserProfileImageView.setOnClickListener(this);
        mDateOfBirthEditText.setOnClickListener(this);
        mAnniversaryEditText.setOnClickListener(this);
        mSpouseDateOfBirthEditText.setOnClickListener(this);
        mUpdateButton.setOnClickListener(this);

        mGenderSpinner.setOnItemSelectedListener(this);
        mBloodGroupSpinner.setOnItemSelectedListener(this);

        String imagePath = ApiUrls.BASE_URL_PATH + mSharedPreferences.getUserProfileImage();
        Picasso.with(this).load(imagePath).skipMemoryCache().into(mUserProfileImageView);

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


            switch (requestCode) {


                case PICK_CAMERA_IMAGE:


                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap photo = bundle.getParcelable("data");
                        mUserProfileImageView.setImageBitmap(photo);
                        saveFile(photo);
                    }

                    break;


                case PICK_GALLERY_IMAGE:

                    Bundle bundle2 = data.getExtras();
                    if (bundle2 != null) {
                        Bitmap photo = bundle2.getParcelable("data");
                        mUserProfileImageView.setImageBitmap(photo);

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
        mUserImagePath = Environment.getExternalStorageDirectory()
                + "/RTN/thumbimage__"+ System.currentTimeMillis()+".png";
        file = new File(mUserImagePath);
        try {
            photo.compress(Bitmap.CompressFormat.PNG, 100,
                    new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.userProfileImageView :
                chooseSourceDialog();
                break;


            case R.id.dateOfBirthEditText :
                setCurrentCalender();
                showDateDialog(mDateOfBirthEditText);
                break;

            case R.id.anniversaryEditText : setCurrentCalender(); showDateDialog(mAnniversaryEditText); break;

            case R.id.spouseDateOfBirthEditText : setCurrentCalender(); showDateDialog(mSpouseDateOfBirthEditText); break;

            case R.id.updateProfileButton : validData(); break;

        }
    }

    private void validData() {

        String firstName = mFirstNameEditText.getText().toString();
        String lastName = mLastNameEditText.getText().toString();
        String emailAddress = mEmailEditText.getText().toString();
        String mobile = mMobileEditText.getText().toString();
        String officePhone = mOfficePhoneEditText.getText().toString();
        String gender = (String) mGenderSpinner.getItemAtPosition(mSelectedGender);
        String bloodGroup = (String) mBloodGroupSpinner.getItemAtPosition(mSelectedBloodGroup);
        String date_of_birth = mDateOfBirthEditText.getText().toString();
        String spouseName = mSpouseNameEditText.getText().toString();
        String spouseDateOfBirth = mSpouseDateOfBirthEditText.getText().toString();
        String annivesaryDate =mAnniversaryEditText.getText().toString();
        String residencePhone= mResidencePhoneEditText.getText().toString();
        String designation = mDesignationEditText.getText().toString();
        String residenceCity = mResidenceCityEditText.getText().toString();
        String OfficeCity = mOfficeCityEditText.getText().toString();
        String state = mStateEditText.getText().toString();
        String company = mCompanyEditText.getText().toString();
        String address = mAddressEdiText.getText().toString();


        if(TextUtils.isEmpty(firstName)){
            showToastMessage(getString(R.string.enter_first_name));
        }else if (TextUtils.isEmpty(lastName)){
            showToastMessage(getString(R.string.enter_last_name));
        } else if(TextUtils.isEmpty(emailAddress)){
            showToastMessage(getString(R.string.enter_email));
        } else if (TextUtils.isEmpty(mobile) || mobile.length() < 8){
            showToastMessage(getString(R.string.enter_valid_mobile));
        }else if (TextUtils.isEmpty(officePhone) || officePhone.length() < 6){
            showToastMessage(getString(R.string.enter_valid_phone_number));
        }else if (TextUtils.isEmpty(date_of_birth)){
            showToastMessage(getString(R.string.enter_date_of_birth));
        }else if (TextUtils.isEmpty(spouseName)){
            showToastMessage(getString(R.string.enter_spouse_name));
        }else if (TextUtils.isEmpty(spouseDateOfBirth)){
            showToastMessage(getString(R.string.enter_spouse_dob));
        }else if(TextUtils.isEmpty(annivesaryDate)){
            showToastMessage(getString(R.string.enter_annivesary_date));
        }else if(mSelectedBloodGroup == 0 ){
            showToastMessage(getString(R.string.please_select_bloodgroup));
        }else if (mSelectedGender == 0 ){
            showToastMessage(getString(R.string.please_select_gender));
        }else {

            String[] params = new String[]{mUserImagePath, firstName, lastName, emailAddress, gender, bloodGroup, mobile, spouseName, date_of_birth,
                            spouseDateOfBirth,annivesaryDate, residencePhone,officePhone, designation , residenceCity , OfficeCity, state, company, address};


            executeAsyncTask(params);

        }


    }

    private void executeAsyncTask(String[] params) {

        mAsynTask = new EditProfileAsyncTask(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                mAsynTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        else
                mAsynTask.execute(params);

    }


    public void setCurrentCalender() {

        Calendar calendar = Calendar.getInstance();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

    }

    private void showDateDialog(final EditText editText) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                String selectedDate = year+"-"+ (month+1) +"-"+ day;
                try {
                    Date userdate = sdf.parse(selectedDate);
                    Date today = sdf.parse(mToday);

                    if(userdate.before(today))
                        editText.setText(year+"-"+ (month+1) +"-"+ day);
                    else
                        showToastMessage(getString(R.string.date_is_not_valid));

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        },mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    private void showToastMessage(String message) {

        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){

            case R.id.genderSpinner : mSelectedGender = position; break;

            case R.id.bloodGroupSpinner : mSelectedBloodGroup = position; break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

