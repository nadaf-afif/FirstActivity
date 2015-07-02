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

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.activity.AddNewNewsActivity;
import app.roundtable.nepal.activity.adapters.NewsListAdapter;
import app.roundtable.nepal.activity.asynktasks.GetNewsListAsyncTask;
import app.roundtable.nepal.activity.interfaces.DataLoader;
import app.roundtable.nepal.activity.network.NetworkManager;

/**
 * Created by afif on 8/6/15.
 */
public class NewsListFragment extends Fragment implements DataLoader{

    private RecyclerView mRecyclerView;
    private FloatingActionButton mAddNewsFloatingButton;
    private NewsListAdapter mAdapter;
    private GetNewsListAsyncTask mAsyncTask;

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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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

            mAdapter = new NewsListAdapter(getActivity(), cursor, this);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onNoInternet() {

    }

    @Override
    public void onNoData() {

    }
}
