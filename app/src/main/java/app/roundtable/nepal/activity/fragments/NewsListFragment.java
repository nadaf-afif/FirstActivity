package app.roundtable.nepal.activity.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.AddNewNewsActivity;
import app.roundtable.nepal.activity.adapters.NewsListAdapter;
import app.roundtable.nepal.activity.asynktasks.GetNewsListAsyncTask;
import app.roundtable.nepal.activity.interfaces.DataLoader;
import app.roundtable.nepal.activity.network.NetworkManager;

/**
 * Created by afif on 8/6/15.
 */
public class NewsListFragment extends Fragment implements DataLoader, View.OnClickListener{

    public static String tag = NewsListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private FloatingActionButton mAddNewsFloatingButton;
    private NewsListAdapter mAdapter;
    private GetNewsListAsyncTask mAsyncTask;
    private FloatingActionButton mFloatingActionButton;
    private ProgressBar mProgressBar;
    private TextView mEmptyTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_news,container,false);

        return view;


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.newsRecyclerView);
        mAddNewsFloatingButton = (FloatingActionButton)view.findViewById(R.id.addNewsFloatingActionButton);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mEmptyTextView = (TextView) view.findViewById(R.id.emptyListTextView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.addNewsFloatingActionButton);
        mFloatingActionButton.setOnClickListener(this);

        getFirstPageData();
    }


    @Override
    public void getFirstPageData() {

       if(NetworkManager.isConnectedToInternet(getActivity()))
       {
           mAsyncTask = new GetNewsListAsyncTask(getActivity(), this);
           mAsyncTask.execute();

       }else
           onNoInternet();
    }

    @Override
    public void setFirstPageData(Cursor cursor) {

        if(isAdded()){

            mEmptyTextView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            mAdapter = new NewsListAdapter(getActivity(), cursor, this);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onNoInternet() {
        mProgressBar.setVisibility(View.GONE);
        mEmptyTextView.setText(getString(R.string.no_internet_connection));
    }

    @Override
    public void onNoData() {
        mProgressBar.setVisibility(View.GONE);
        mEmptyTextView.setText(getString(R.string.no_news_available));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.addNewsFloatingActionButton :

                Intent intent = new Intent(getActivity(), AddNewNewsActivity.class);
                startActivity(intent);

                break;


        }

    }
}
