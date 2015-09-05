package app.roundtable.nepal.activity.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.roundtable.nepal.R;
import app.roundtable.nepal.activity.util.GData;

/**
 * Created by afif on 11/8/15.
 */
public class MapActivity extends Activity implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    // A request to connect to Location Services
    private LocationRequest mLocationRequest;
    GoogleMap mGoogleMap;


    public static String ShopLat;
    public static String ShopPlaceId;
    public static String ShopLong;
    // Stores the current instantiation of the location client in this object
    private GoogleApiClient mLocationClient;
    boolean mUpdatesRequested = false;
    private TextView markerText;
    private LatLng center;
    private LinearLayout markerLayout;
    private Geocoder geocoder;
    private List<Address> addresses;
    private TextView Address;
    private String mVenueAddress;
    private double mLatitude, mLongitude;
    private Button mDoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mao);
        markerText = (TextView) findViewById(R.id.locationMarkertext);
        Address = (TextView) findViewById(R.id.adressText);
        markerLayout = (LinearLayout) findViewById(R.id.locationMarker);
        mDoneButton = (Button) findViewById(R.id.doneButton);
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getBaseContext());

        if (status != ConnectionResult.SUCCESS) { // Google Play Services are
            // not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
                    requestCode);
            dialog.show();

        } else { // Google Play Services are available

            // Getting reference to the SupportMapFragment
            // Create a new global location parameters object
            mLocationRequest = LocationRequest.create();

            /*
             * Set the update interval
             */
            mLocationRequest.setInterval(GData.UPDATE_INTERVAL_IN_MILLISECONDS);

            // Use high accuracy
            mLocationRequest
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            // Set the interval ceiling to one minute
            mLocationRequest
                    .setFastestInterval(GData.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

            // Note that location updates are off until the user turns them on
            mUpdatesRequested = false;

            /*
             * Create a new location client, using the enclosing class to handle
             * callbacks.
             */
            mLocationClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            mLocationClient.connect();
        }


        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                passDataToParentActivity();
            }
        });

    }

    private void passDataToParentActivity() {

        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", mLatitude);
        bundle.putDouble("longitude", mLongitude);
        bundle.putString("venueName", mVenueAddress);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void stupMap() {
        try {
            LatLng latLong;
            // TODO Auto-generated method stub
            mGoogleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

//            // Enabling MyLocation in Google Map
//            mGoogleMap.setMyLocationEnabled(true);
//            if (LocationServices.FusedLocationApi.getLastLocation(mLocationClient) != null) {
//                latLong = new LatLng(LocationServices.FusedLocationApi.getLastLocation(mLocationClient)
//                        .getLatitude(), LocationServices.FusedLocationApi.getLastLocation(mLocationClient)
//                        .getLongitude());
//                ShopLat = LocationServices.FusedLocationApi.getLastLocation(mLocationClient).getLatitude() + "";
//                ShopLong = LocationServices.FusedLocationApi.getLastLocation(mLocationClient).getLongitude()
//                        + "";
//
//
//            } else {
            //}
            //27.7000° N, 85.3333° E
            latLong = new LatLng(27.7000, 85.3333);

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLong).zoom(14f).tilt(50).build();

            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
            // Clears all the existing markers
            mGoogleMap.clear();

            mGoogleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

                @Override
                public void onCameraChange(CameraPosition arg0) {
                    // TODO Auto-generated method stub
                    center = mGoogleMap.getCameraPosition().target;

                    markerText.setText(" Set your Location ");
                    mGoogleMap.clear();
                    markerLayout.setVisibility(View.VISIBLE);

                    try {
                        new GetLocationAsync(center.latitude, center.longitude)
                                .execute();

                    } catch (Exception e) {
                    }
                }
            });

            markerLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub
        stupMap();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    private class GetLocationAsync extends AsyncTask<String, Void, String> {

        // boolean duplicateResponse;
        double x, y;
        StringBuilder str;

        public GetLocationAsync(double latitude, double longitude) {
            // TODO Auto-generated constructor stub

            mLatitude = x = latitude;
            mLongitude = y = longitude;
        }

        @Override
        protected void onPreExecute() {
            Address.setText(" Getting location ");
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                geocoder = new Geocoder(MapActivity.this, Locale.ENGLISH);
                addresses = geocoder.getFromLocation(x, y, 1);
                str = new StringBuilder();
                if (geocoder.isPresent()) {
                    Address returnAddress = addresses.get(0);

                    String localityString = returnAddress.getLocality();
                    String city = returnAddress.getCountryName();
                    String region_code = returnAddress.getCountryCode();
                    String zipcode = returnAddress.getPostalCode();

                    str.append(localityString + "");
                    str.append(city + "" + region_code + "");
                    str.append(zipcode + "");

                } else {
                }
            } catch (IOException e) {
                Log.e("tag", e.getMessage());
            }catch (Exception e){
                Log.e("Exception", e.getMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                Address.setText(addresses.get(0).getAddressLine(0)
                        + addresses.get(0).getAddressLine(1) + " "+addresses.get(0).getLocality());
                mVenueAddress = addresses.get(0).getAddressLine(0)+", " +addresses.get(0).getLocality();
                markerText.setText(mVenueAddress);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

}