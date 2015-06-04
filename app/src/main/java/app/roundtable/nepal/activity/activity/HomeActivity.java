package app.roundtable.nepal.activity.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.fragments.EventsAndMeetingsFragment;
import app.roundtable.nepal.activity.fragments.SearchMembersFragment;
import app.roundtable.nepal.activity.navigation.NavigationDrawerFragment;


/**
 * Created by afif on 12/5/15.
 */
public class HomeActivity extends ActionBarActivity {

    private Toolbar mToolBar;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolBar = (Toolbar) findViewById(R.id.action_bar);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentNavigationDrawer);
        drawerFragment.setDrawerLayout(drawerLayout, mToolBar);

        displayTab(0);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public void displayTab(int position){




        switch (position)
        {
            case  0 :
                mFragment = new SearchMembersFragment();
                setTitle("Search Members");
                break;

            case 1 :

                mFragment = new EventsAndMeetingsFragment();
                setTitle("Events");

                break;


            case 2 : break;

            case 3 : break;


        }


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,mFragment);
        ft.commit();

    }



}
