package app.roundtable.nepal.activity.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.TableMembersListAdapter;
import app.roundtable.nepal.activity.asynktasks.GetMembersByTableAsyncTask;
import app.roundtable.nepal.activity.database.Tables;
import app.roundtable.nepal.activity.interfaces.DataLoader;
import app.roundtable.nepal.activity.network.NetworkManager;

/**
 * Created by afif on 13/6/15.
 */
public class TableMembersListActivity extends AppCompatActivity implements DataLoader{

    private Toolbar mToolBar;
    private RecyclerView mRecyclerView;
    private String mTableName, mTableId;
    private TableMembersListAdapter mAdapter;
    private GetMembersByTableAsyncTask mAsyncTask;
    private ProgressBar mProgressBar;
    private TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_members_list);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null) {

            mTableName = bundle.getString("tableName");
            mTableId  = bundle.getString(Tables.RTNTables.TABLE_ID);
        }
        initView();
    }

    private void initView() {

        mToolBar = (Toolbar) findViewById(R.id.actionToolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.tableMembersRecyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mEmptyTextView = (TextView) findViewById(R.id.emptyListTextView);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(mTableName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getFirstPageData();

    }

    @Override
    public void getFirstPageData() {

        if(NetworkManager.isConnectedToInternet(this))
        {
            mAsyncTask = new GetMembersByTableAsyncTask(getApplicationContext(), this);

            mAsyncTask.execute(mTableId);

        }else {

            onNoInternet();
        }

    }

    @Override
    public void setFirstPageData(Cursor cursor) {

        if (cursor.getCount() > 0) {

            mEmptyTextView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            mAdapter = new TableMembersListAdapter(this, cursor);
            mRecyclerView.setAdapter(mAdapter);
        }else
            onNoData();
    }

    @Override
    public void onNoInternet() {
        mProgressBar.setVisibility(View.GONE);
        mEmptyTextView.setText(getString(R.string.no_internet_connection));
    }

    @Override
    public void onNoData() {

        mProgressBar.setVisibility(View.GONE);
        mEmptyTextView.setText(getString(R.string.no_members_found));
    }
}
