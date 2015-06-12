package app.roundtable.nepal.activity.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.TableNameDialogAdapter;

/**
 * Created by afif on 13/6/15.
 */
public class AddNewNewsActivity extends AppCompatActivity implements View.OnClickListener{


    private Button mSubmitnewsButton;
    private Toolbar mToolBar;
    private EditText mConcernTableEditText;
    private String[] mTableName = new String[]{"Table 1","Table 2","Table 3","Table 4","Table 5","Table 6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        initViews();

    }

    private void initViews() {

        mSubmitnewsButton = (Button)findViewById(R.id.submitNewsButton);
        mConcernTableEditText = (EditText)findViewById(R.id.concernTablesEditText);
        mConcernTableEditText.setOnClickListener(this);
        mSubmitnewsButton.setOnClickListener(this);

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

                showConfirmationDialog();
                break;

            case R.id.concernTablesEditText :
                ShowTablesDialog();
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
}
