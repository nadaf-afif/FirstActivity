package app.roundtable.nepal.activity.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.TableNameDialogAdapter;
import app.roundtable.nepal.activity.asynktasks.AddEventAsyncTasks;
import app.roundtable.nepal.activity.database.RTNTablesManager;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.databeans.TablesInfoBean;

/**
 * Created by afif on 10/6/15.
 */
public class AddNewEventActivity extends AppCompatActivity implements View.OnClickListener, Tables.RTNTables{

    private Toolbar mToolBar;
    private EditText mEventNameEditText, mVenueEdiText, mTimeEditText, mDateEditText, mInviteesEditText, mVenueAddressDetail;
    private Switch mSpouseSwitch, mChildrenSwitch;
    private Button mAddEventButton;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private ImageView mEventImageView;
    private static int TIME_PICKER_DIALOG = 111, DATE_PICKER_DIALOG = 222;
    public static final int PICK_CAMERA_IMAGE = 011, PICK_GALLERY_IMAGE = 911, REQUEST_CODE_MAP = 696;
    public  ArrayList<TablesInfoBean> tablesData = new ArrayList<>();
    private Set<String > selectedId = new HashSet<>();
    private String mEVentImagePath;
    private double mLatitude, mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_event);

        mToolBar = (Toolbar) findViewById(R.id.actionToolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.add_event));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        setCurrentCalender();
        Cursor cursor = new RTNTablesManager(this).getTables();
        tablesData = getObject(cursor);

    }

    private void setCurrentCalender() {

        Calendar calendar = Calendar.getInstance();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        mHour = calendar.get(Calendar.HOUR);
        mMinute = calendar.get(Calendar.MINUTE);

    }



    private void initView() {

        mEventNameEditText = (EditText) findViewById(R.id.eventNameEditText);
        mVenueEdiText = (EditText)findViewById(R.id.venueNameEditText);
        mTimeEditText = (EditText)findViewById(R.id.timeEditText);
        mDateEditText = (EditText)findViewById(R.id.dateEditText);
        mInviteesEditText = (EditText)findViewById(R.id.invitessEditText);
        mAddEventButton = (Button)findViewById(R.id.addeventButton);
        mSpouseSwitch = (Switch)findViewById(R.id.spouceSwitch);
        mChildrenSwitch = (Switch) findViewById(R.id.childrenSwitch);
        mEventImageView = (ImageView) findViewById(R.id.eventPhotoImageView);
        mVenueAddressDetail = (EditText) findViewById(R.id.venueAddressEditText);


        mTimeEditText.setOnClickListener(this);
        mDateEditText.setOnClickListener(this);
        mInviteesEditText.setOnClickListener(this);
        mAddEventButton.setOnClickListener(this);
        mEventImageView.setOnClickListener(this);
        mVenueEdiText.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.timeEditText :

                showTimeDialog();

                break;


            case R.id.dateEditText :

                showDateDialog();
                break;

            case R.id.invitessEditText :

                ShowTablesDialog();
                break;


            case R.id.addeventButton :
                validateData();
                break;

            case R.id.eventPhotoImageView:

                chooseSourceDialog();

                break;

            case R.id.venueNameEditText :

                Intent intent = new Intent(AddNewEventActivity.this, MapActivity.class);
                startActivityForResult(intent,REQUEST_CODE_MAP);
                break;

        }

    }

    private void validateData() {

        String eventName = mEventNameEditText.getText().toString();
        String venueAddressDetail = mVenueAddressDetail.getText().toString();
        String date = mDateEditText.getText().toString();
        String time = mTimeEditText.getText().toString();
        boolean isSpouse = mSpouseSwitch.isChecked();
        boolean isChildren = mChildrenSwitch.isChecked();
        String venueLocation = mVenueEdiText.getText().toString();

        if(eventName.equals("") || eventName.isEmpty()){
            Toast.makeText(this,getString(R.string.enter_event_name),Toast.LENGTH_SHORT).show();
        }else if(venueAddressDetail.equals("") || venueAddressDetail.isEmpty()){
            Toast.makeText(this,getString(R.string.enter_event_venue),Toast.LENGTH_SHORT).show();
        }else if(date.equals("") || date.isEmpty()){
            Toast.makeText(this,getString(R.string.enter_date),Toast.LENGTH_SHORT).show();        Cursor cursor = new RTNTablesManager(this).getTables();

        }else if(time.equals("") || time.isEmpty()){
            Toast.makeText(this,getString(R.string.enter_time),Toast.LENGTH_SHORT).show();
        }else if(selectedId.size() < 1) {
            Toast.makeText(this, getString(R.string.select_at_least_one_table), Toast.LENGTH_SHORT).show();
//        }else if (mEVentImagePath == null || mEVentImagePath.equals("")){
//
//            Toast.makeText(this,getString(R.string.please_select_event_photo),Toast.LENGTH_SHORT).show();
//
//        }
        }else {

            String invitees = selectedId.toString().substring(1, selectedId.toString().length()-1);
            String spouse = (isSpouse ? "1" : "0");
            String children = (isChildren ? "1" : "0");

            executeAsyncTask(invitees, spouse, children, eventName, venueAddressDetail, date, time, mEVentImagePath, venueLocation, mLatitude, mLongitude);

        }


    }

    private void executeAsyncTask(String invitees, String spouse, String children, String eventName, String venueAddressDetail, String date, String time, String mEVentImagePath, String venueLocation, double latitude, double longitude) {

        AddEventAsyncTasks asyncTasks = new AddEventAsyncTasks(this);
        asyncTasks.execute(invitees, spouse, children, eventName, venueAddressDetail, date, time, mEVentImagePath, venueLocation, latitude+"", longitude+"");

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

    private TableNameDialogAdapter adapter;

    private void ShowTablesDialog() {

        final Dialog dialog  = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tablenames);

        ListView mListView = (ListView)dialog.findViewById(R.id.tableNameListView);
        Button doneButton = (Button)dialog.findViewById(R.id.doneButton);

        adapter = new TableNameDialogAdapter(this,tablesData, selectedId);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                selectedId = adapter.mSelectedId;
                tablesData = adapter.tableNames;
                int selectedCount = getSelectedTables(tablesData);
                mInviteesEditText.setText(selectedId.size() + " Tables selected");
            }
        });

        dialog.show();

    }

    private int getSelectedTables(ArrayList<TablesInfoBean> tablesData) {

        int count = 0;

        for (int i=0;i<tablesData.size();i++)
        {
            if(tablesData.get(i).isSelected())
                count++;
        }

        return count;
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

    private void showDateDialog() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                mDateEditText.setText(year+"-"+ (month+1) +"-"+ day);
            }
        },mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    private void showTimeDialog() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                  if(hour > 12 )
                   mTimeEditText.setText(hour+"."+minute+ ".00");
                 else
                      mTimeEditText.setText(hour+"."+minute+ ".00");


            }
        },mHour,mMinute,false);

        timePickerDialog.show();

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
                        mEventImageView.setImageBitmap(photo);
                        saveFile(photo);
                    }

                    break;


                case PICK_GALLERY_IMAGE:

                    Bundle bundle2 = data.getExtras();
                    if (bundle2 != null) {
                        Bitmap photo = bundle2.getParcelable("data");
                        mEventImageView.setImageBitmap(photo);

                        saveFile(photo);
                    }

                    break;



                case REQUEST_CODE_MAP :

                    Bundle bundle3 = data.getExtras();
                    mLatitude = bundle3.getDouble("latitude");
                    mLongitude = bundle3.getDouble("longitude");
                    String address = bundle3.getString("venueName");
                    mVenueEdiText.setText(address);

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
        mEVentImagePath = Environment.getExternalStorageDirectory()
                + "/RTN/event__"+ System.currentTimeMillis()+".png";
        file = new File(mEVentImagePath);
        try {
            photo.compress(Bitmap.CompressFormat.PNG, 100,
                    new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        if(bundle.containsKey("event_photo"))
        {
            mEVentImagePath = bundle.getString("event_photo");
            Bitmap bitmap = BitmapFactory.decodeFile(mEVentImagePath);

            mEventImageView.setImageBitmap(bitmap);

        }
        super.onRestoreInstanceState(bundle);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("event_photo", mEVentImagePath);
        super.onSaveInstanceState(outState);
    }
}
