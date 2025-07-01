package com.s23010733.md_count_track;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

class goolemap extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Thulhiriya Location
        LatLng thulhiriya = new LatLng(7.275572944640557, 80.21751931394009);

        // Add Marker and Move Camera
        googleMap.addMarker(new MarkerOptions().position(thulhiriya).title("Thulhiriya (76G8+5XC)"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(thulhiriya, 15)); // Zoom level 15 for closer view
    }

}
