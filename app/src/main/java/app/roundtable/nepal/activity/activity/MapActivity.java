package app.roundtable.nepal.activity.activity;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.roundtable.nepal.R;

/**
 * Created by afif on 11/8/15.
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback{

    private AutoCompleteTextView mLocationEditText;
    private GoogleMap mGoogleMap;
    private LatLng latLng;
    private MarkerOptions markerOptions;
    private ArrayList<String> mLocationList = new ArrayList<>();
    private ArrayAdapter<String> mAddressAdapter;
    private ArrayList<LatLng> mLatLongList = new ArrayList<>();
    private String mVenueName;
    private LatLng mLatLong  = new LatLng(27.7000, 85.3333);
    private boolean mShowOnMap = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mao);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mGoogleMap = mapFragment.getMap();

        mLocationEditText = (AutoCompleteTextView) findViewById(R.id.searchLocationEditText);


        mLocationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String location  = s.toString();
                if(location!=null && !location.equals("") && location.length()>2 ) {
                    mShowOnMap = true;
                    new GeocoderTask().execute(location);
                }else{
                    mGoogleMap.clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        mLocationEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mShowOnMap = false;
                mVenueName = mLocationList.get(position);
                mLatLong = mLatLongList.get(position);

                mGoogleMap.clear();
                markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(mVenueName);

                mGoogleMap.addMarker(markerOptions);
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(mLatLong));


            }
        });

        mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                mGoogleMap.clear();
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));

            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setCompassEnabled(true);

       //27.7000° N, 85.3333° E
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLong, 16.0f));
        mGoogleMap.addMarker(new MarkerOptions().position(mLatLong).snippet("Kathmandu"));

    }


    // An AsyncTask class for accessing the GeoCoding Web Service
    private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {

            if(addresses==null || addresses.size()==0){
                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
            }


            if(mShowOnMap){
            // Clears all the existing markers on the map
            mGoogleMap.clear();

            mLocationList.clear();
            mLatLongList.clear();

            // Adding Markers on Google Map for each matching address
            for(int i=0;i<addresses.size();i++) {

                Address address = (Address) addresses.get(i);

                // Creating an instance of GeoPoint, to display in Google Map
                latLng = new LatLng(address.getLatitude(), address.getLongitude());

                String addressText = String.format("%s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getCountryName());

                markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(addressText);

                mGoogleMap.addMarker(markerOptions);

                mLocationList.add(addressText);
                mLatLongList.add(latLng);

                mAddressAdapter = new ArrayAdapter<String>(MapActivity.this, android.R.layout.simple_list_item_1, mLocationList);
                mLocationEditText.setAdapter(mAddressAdapter);

                // Locate the first location
                if (i == 0) {

                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                }
            }

            }
        }

    }


}
