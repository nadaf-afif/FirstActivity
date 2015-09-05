package app.roundtable.nepal.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import app.roundtable.nepal.R;

/**
 * Created by afif on 17/8/15.
 */
public class ShowLocationActivity extends AppCompatActivity {

    GoogleMap mGoogleMap;
    private double mLatitude, mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


        mGoogleMap = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.map)).getMap();

        Bundle bundle = getIntent().getExtras();
        showLocation(bundle);

    }

    private void showLocation(Bundle bundle) {


        mLatitude = Double.parseDouble(bundle.getString("latitude"));
        mLongitude = Double.parseDouble(bundle.getString("longitude"));
        String location = bundle.getString("location");
        Log.d("Locations", mLatitude+" "+ mLongitude);

        LatLng latLong = new LatLng(mLatitude, mLongitude);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLong).zoom(14f).tilt(50).build();

        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.map_marker);

        MarkerOptions markerOptions = new MarkerOptions().position(latLong)
                .title(location)
                .snippet(location);
        mGoogleMap.addMarker(markerOptions);
    }
}
