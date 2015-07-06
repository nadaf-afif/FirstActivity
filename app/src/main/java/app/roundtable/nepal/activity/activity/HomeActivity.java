package app.roundtable.nepal.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.fragments.AboutRTNFragment;
import app.roundtable.nepal.activity.fragments.EventsAndMeetingsFragment;
import app.roundtable.nepal.activity.fragments.FavoritesFragemet;
import app.roundtable.nepal.activity.fragments.NewsListFragment;
import app.roundtable.nepal.activity.fragments.PrivilegesFragment;
import app.roundtable.nepal.activity.fragments.RTNClubsFragment;
import app.roundtable.nepal.activity.navigation.NavigationDrawerFragment;
import app.roundtable.nepal.activity.util.ApplicationPreferences;


/**
 * Created by afif on 12/5/15.
 */
public class HomeActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private Fragment mFragment;
    private Map<Integer, Fragment> mTabFragmentsMap;
    public static final int NAVIGATION_TAB_PROFILE = 10;
    public static final int NAVIGATION_TAB_TABLES = 0;
    public static final int NAVIGATION_TAB_EVENTS = 1;
    public static final int NAVIGATION_TAB_NEWS = 2;
    public static final int NAVIGATION_TAB_FAVORITES = 3;
    public static final int NAVIGATION_TAB_PRIVILEGES = 4;
    public static final int NAVIGATION_TAB_SUBMIT_PHOTOS = 5;
    public static final int NAVIGATION_TAB_ABOUT_US = 6;

    private ApplicationPreferences mSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolBar = (Toolbar) findViewById(R.id.action_bar);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentNavigationDrawer);
        drawerFragment.setDrawerLayout(drawerLayout, mToolBar);

        mSharedPreference = new ApplicationPreferences(this);

        if (mTabFragmentsMap == null) {
            mTabFragmentsMap = new HashMap<Integer, Fragment>();
        }

        displayTab(NAVIGATION_TAB_TABLES);

    }



    public void displayTab(int position){

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position)
        {
            case  NAVIGATION_TAB_TABLES :

                mFragment = fragmentManager.findFragmentByTag(RTNClubsFragment.tag);
                mSharedPreference.setNavigationIndex(NAVIGATION_TAB_TABLES);
                if(mFragment == null) {
                    mFragment = new RTNClubsFragment();
                    showNewFragment(NAVIGATION_TAB_TABLES,mFragment,RTNClubsFragment.tag);
                }else {
                    showExistingFragment(mFragment,NAVIGATION_TAB_TABLES);
                }
                setTitle("RTN Clubs");
                break;

            case NAVIGATION_TAB_EVENTS :

                mFragment = fragmentManager.findFragmentByTag(EventsAndMeetingsFragment.tag);
                mSharedPreference.setNavigationIndex(NAVIGATION_TAB_EVENTS);
                if(mFragment == null) {
                    mFragment = new EventsAndMeetingsFragment();
                    showNewFragment(NAVIGATION_TAB_EVENTS,mFragment,EventsAndMeetingsFragment.tag);

                }else {
                    showExistingFragment(mFragment, NAVIGATION_TAB_EVENTS);
                }

                setTitle("Events");
                break;


            case NAVIGATION_TAB_NEWS :

                mFragment = fragmentManager.findFragmentByTag(NewsListFragment.tag);
                mSharedPreference.setNavigationIndex(NAVIGATION_TAB_NEWS);
                if(mFragment == null) {
                    mFragment = new NewsListFragment();
                    showNewFragment(NAVIGATION_TAB_NEWS,mFragment,NewsListFragment.tag);
                }else {
                    showExistingFragment(mFragment, NAVIGATION_TAB_NEWS);
                }
                setTitle("News");
                break;



            case NAVIGATION_TAB_FAVORITES :

                mFragment = fragmentManager.findFragmentByTag(FavoritesFragemet.tag);
                mSharedPreference.setNavigationIndex(NAVIGATION_TAB_FAVORITES);
                if(mFragment == null) {
                    mFragment = new FavoritesFragemet();
                    showNewFragment(NAVIGATION_TAB_FAVORITES,mFragment,FavoritesFragemet.tag);
                }else {
                    showExistingFragment(mFragment,NAVIGATION_TAB_FAVORITES);
                }
                setTitle("Favorites");
                break;

            case NAVIGATION_TAB_PRIVILEGES :

                mFragment = fragmentManager.findFragmentByTag(PrivilegesFragment.tag);
                mSharedPreference.setNavigationIndex(NAVIGATION_TAB_PRIVILEGES);
                if(mFragment == null) {
                    mFragment = new PrivilegesFragment();
                    showNewFragment(NAVIGATION_TAB_PRIVILEGES,mFragment,PrivilegesFragment.tag);

                }else {

                    showExistingFragment(mFragment,NAVIGATION_TAB_PRIVILEGES);
                }
                setTitle("Privileges");
                break;


            case  NAVIGATION_TAB_SUBMIT_PHOTOS :
                Intent intent = new Intent(this, SubmitPhotosActivity.class);
                startActivity(intent);
                break;


            case NAVIGATION_TAB_ABOUT_US :
                mFragment = fragmentManager.findFragmentByTag(AboutRTNFragment.tag);
                mSharedPreference.setNavigationIndex(NAVIGATION_TAB_ABOUT_US);
                if(mFragment == null) {
                    mFragment = new AboutRTNFragment();
                    showNewFragment(NAVIGATION_TAB_ABOUT_US,mFragment,AboutRTNFragment.tag);
                }else {
                    showExistingFragment(mFragment,NAVIGATION_TAB_ABOUT_US);
                }
                setTitle("About RTN");
                break;

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            mFragment.onActivityResult(requestCode,resultCode,data);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.my,menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){

            case R.id.action_search :

                Intent intent = new Intent(this, SearchMembersActivity.class);
                startActivity(intent);

        }
        return true;
    }


    private void showNewFragment(int tabIndex, Fragment fragment, String tag) {

        addFragmentToContainer(fragment, tag);

        addFragmentToMap(tabIndex, fragment);

        hideOtherFragments(tabIndex);
    }

    private void addFragmentToMap(int tabIndex, Fragment fragment) {


        if (mTabFragmentsMap.get(tabIndex) == null) {

            mTabFragmentsMap.put(tabIndex, fragment);
        }
    }

    private void addFragmentToContainer(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, tag).commit();
    }

    private void showExistingFragment(Fragment fragment, int tabIndex) {
        hideOtherFragments(tabIndex);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.show(fragment);
        ft.commit();
    }


    private void hideOtherFragments(int tabIndex) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment fragment;

        for (Map.Entry<Integer, Fragment> entry : mTabFragmentsMap.entrySet()) {
            int currentTabIndex = entry.getKey();

            fragment = entry.getValue();
            if (currentTabIndex != tabIndex) {

                ft.hide(fragment);

            }
        }

        ft.commit();
    }

}
