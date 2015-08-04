package app.roundtable.nepal.activity.fragments;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.adapters.FavoritesAdapter;
import app.roundtable.nepal.activity.asynktasks.GetFavoritesAsyncTask;
import app.roundtable.nepal.activity.interfaces.DataLoader;
import app.roundtable.nepal.activity.network.NetworkManager;

/**
 * Created by afif on 8/6/15.
 */
public class FavoritesFragemet extends Fragment implements DataLoader{

    public static String tag = FavoritesFragemet.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private GetFavoritesAsyncTask mAsyncTask;
    private FavoritesAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mEmptyTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_favorites,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.favoritesRecyclerView);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mEmptyTextView = (TextView) view.findViewById(R.id.emptyListTextView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        getFirstPageData();

    }

    @Override
    public void getFirstPageData() {

        if(isAdded()){

            if(NetworkManager.isConnectedToInternet(getActivity())){

                mAsyncTask = new GetFavoritesAsyncTask(this, getActivity());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    mAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    mAsyncTask.execute();

            }else {

                onNoInternet();
            }

        }

    }

    @Override
    public void setFirstPageData(Cursor cursor) {
        if(isAdded()){

            mEmptyTextView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            mAdapter = new FavoritesAdapter(getActivity(),cursor);
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
        mEmptyTextView.setText(getString(R.string.no_favourites_available));
    }
}
