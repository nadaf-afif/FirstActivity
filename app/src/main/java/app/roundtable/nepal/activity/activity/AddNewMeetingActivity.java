package app.roundtable.nepal.activity.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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

import java.util.Calendar;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.TableNameDialogAdapter;

/**
 * Created by afif on 11/6/15.
 */
public class AddNewMeetingActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mToolBar;
    private EditText mEventNameEditText, mVenueEdiText, mTimeEditText, mDateEditText, mInviteesEditText;
    private Switch mSpouseAndChildrenSwitch;
    private Button mAddEventButton;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static int TIME_PICKER_DIALOG = 111, DATE_PICKER_DIALOG = 222;
    private String[] mTableName = new String[]{"Table 1","Table 2","Table 3","Table 4","Table 5","Table 6"};

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
        mSpouseAndChildrenSwitch = (Switch)findViewById(R.id.spouceAndChildrenSwitch);


        mTimeEditText.setOnClickListener(this);
        mDateEditText.setOnClickListener(this);
        mInviteesEditText.setOnClickListener(this);
        mAddEventButton.setOnClickListener(this);

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
                showConfirmationDialog();
                break;

        }

    }

    private void showConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Request Accepted !");
        builder.setMessage(getString(R.string.confirm_message_text));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }

    private void ShowTablesDialog() {

        Dialog dialog  = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tablenames);

        ListView mListView = (ListView)dialog.findViewById(R.id.tableNameListView);
        Button doneButton = (Button)dialog.findViewById(R.id.doneButton);

        TableNameDialogAdapter adapter = new TableNameDialogAdapter(this,R.layout.list_item_table_names,mTableName);
        mListView.setAdapter(adapter);

        dialog.show();

    }

    private void showDateDialog() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                mDateEditText.setText(day+"/"+month+"/"+year);
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
}
