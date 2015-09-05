package app.roundtable.nepal.activity.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.TableNameDialogAdapter;
import app.roundtable.nepal.activity.asynktasks.AddMeetingAsyncTask;
import app.roundtable.nepal.activity.database.RTNTablesManager;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.databeans.TablesInfoBean;

/**
 * Created by afif on 11/6/15.
 */
public class AddNewMeetingActivity extends AppCompatActivity implements View.OnClickListener, Tables.RTNTables{

    private static final int REQUEST_CODE_MAP = 696;
    private Toolbar mToolBar;
    private EditText mEventNameEditText, mVenueEdiText, mTimeEditText, mDateEditText, mInviteesEditText, mVenueAddressEditText;
    private Switch mSpouseSwitch, mChildrenSwitch;
    private Button mAddEventButton;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static int TIME_PICKER_DIALOG = 111, DATE_PICKER_DIALOG = 222;
    private Set<String > selectedId = new HashSet<>();
    public  ArrayList<TablesInfoBean> tablesData = new ArrayList<>();
    TableNameDialogAdapter adapter;
    private double mLatitude;
    private double mLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_meeting);

        mToolBar = (Toolbar) findViewById(R.id.actionToolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.add_meeting));
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
        mAddEventButton = (Button)findViewById(R.id.addMeetingButton);
        mSpouseSwitch = (Switch)findViewById(R.id.spouceSwitch);
        mChildrenSwitch = (Switch) findViewById(R.id.childrenSwitch);
        mVenueAddressEditText = (EditText) findViewById(R.id.venueAddressEditText);

        mTimeEditText.setOnClickListener(this);
        mDateEditText.setOnClickListener(this);
        mInviteesEditText.setOnClickListener(this);
        mAddEventButton.setOnClickListener(this);
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

            case R.id.addMeetingButton :

                validateData();

                break;

            case  R.id.venueNameEditText:
                Intent intent = new Intent(AddNewMeetingActivity.this, MapActivity.class);
                startActivityForResult(intent,REQUEST_CODE_MAP);
                break;

        }

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

        adapter = new TableNameDialogAdapter(this,tablesData, selectedId);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                selectedId = adapter.mSelectedId;
                tablesData = adapter.tableNames;
                mInviteesEditText.setText(selectedId.size() + " Tables selected");

            }
        });


        dialog.show();

    }

    private void showDateDialog() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                mDateEditText.setText(year+"-"+(month+1)+"-"+day);
            }
        },mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    private void showTimeDialog() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                mTimeEditText.setText(hour+":"+minute);
            }
        },mHour,mMinute,false);

        timePickerDialog.show();

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

    private void validateData() {

        String eventName = mEventNameEditText.getText().toString();
        String venueAddressName = mVenueAddressEditText.getText().toString();
        String venueName = mVenueEdiText.getText().toString();
        String date = mDateEditText.getText().toString();
        String time = mTimeEditText.getText().toString();
        boolean isSpouse = mSpouseSwitch.isChecked();
        boolean isChildren = mChildrenSwitch.isChecked();

        if(eventName.equals("") || eventName.isEmpty()){
            Toast.makeText(this, getString(R.string.enter_event_name), Toast.LENGTH_SHORT).show();
        }else if(venueAddressName.equals("") || venueAddressName.isEmpty()){
            Toast.makeText(this,getString(R.string.enter_event_venue),Toast.LENGTH_SHORT).show();
        }else if(date.equals("") || date.isEmpty()){
            Toast.makeText(this,getString(R.string.enter_date),Toast.LENGTH_SHORT).show();        Cursor cursor = new RTNTablesManager(this).getTables();

        }else if(time.equals("") || time.isEmpty()){
            Toast.makeText(this,getString(R.string.enter_time),Toast.LENGTH_SHORT).show();
        }else if(selectedId.size() < 1){
            Toast.makeText(this,getString(R.string.select_at_least_one_table),Toast.LENGTH_SHORT).show();
        }

            String invitees = selectedId.toString().substring(1, selectedId.toString().length()-1);
            String spouse = (isSpouse ? "1" : "0");
            String children = (isChildren ? "1" : "0");

            executeAsyncTask(invitees, spouse, children, eventName, venueAddressName, venueName, date, time, mLatitude, mLongitude);

        }

    private void executeAsyncTask(String invitees, String spouse, String children, String eventName, String venueAddress, String venueName, String date, String time, double latitude, double longitude) {

        AddMeetingAsyncTask mAsyncTask = new AddMeetingAsyncTask(this);
        mAsyncTask.execute(invitees,spouse,children,eventName,venueName,date,time, venueAddress, latitude+"", longitude+"");

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {

            switch (requestCode) {
                case REQUEST_CODE_MAP:

                    Bundle bundle3 = data.getExtras();
                    mLatitude = bundle3.getDouble("latitude");
                    mLongitude = bundle3.getDouble("longitude");
                    String address = bundle3.getString("venueName");
                    mVenueEdiText.setText(address);

                    break;
            }
        }
    }
}
