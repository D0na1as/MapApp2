package com.example.helpapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBinderMapper;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.helpapp.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private FragmentMapsBinding binding;
    private GoogleMap mMap;
    private SupportMapFragment fragment;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private LatLngBounds Lietuva = new LatLngBounds(
            new LatLng(53.914964, 21.077476), new LatLng(56.310243, 26.884515));


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getContext(), "onCreateView", Toast.LENGTH_SHORT).show();

        if (fragment != null) {
            Toast.makeText(getContext(), "onCreateView NULLAS", Toast.LENGTH_SHORT).show();
        }
        if (fragment == null) {
            Toast.makeText(getContext(), "onCreateView PILNAS!!!", Toast.LENGTH_SHORT).show();

            binding = FragmentMapsBinding.inflate(inflater, container, false);
            SearchView searchField = binding.getRoot().findViewById(R.id.searchView);
            searchField.setBackgroundResource(R.drawable.search_field_rounded);

            FragmentManager manager = getChildFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            fragment = new SupportMapFragment();

            transaction.add(R.id.map, fragment);
            transaction.commit();

            fragment.getMapAsync(this);
        }
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Toast.makeText(getContext(), "onDestroyView", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getContext(), "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getContext(), "onDetached", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);

        MapStyleOptions mapStyleOptions = MapStyleOptions.loadRawResourceStyle(binding.getRoot().getContext(), R.raw.map_style);
        googleMap.setMapStyle(mapStyleOptions);
        //mMap.setMyLocationEnabled(true);
        mMap.setMinZoomPreference(6.5f);
        // Constrain the camera target bounds.
        mMap.setLatLngBoundsForCameraTarget(Lietuva);
        LatLng centras = new LatLng(55.287158, 23.8);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(centras).zoom(6.5f).bearing(0f).tilt(0f).build()));
        fillCountries();

        Marker ligoninė = mMap.addMarker(new MarkerOptions().position(new LatLng(55.28, 23.8)).icon(BitmapDescriptorFactory.
                fromResource(R.drawable.alert)).title("LCR Ligoninė"));

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location loc) {
//                LatLng currentPos = new LatLng(loc.getLatitude(), loc.getLongitude());
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 7));
                //mMap.addMarker(new MarkerOptions().position(currentPos).title("Here You Are!"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPos));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

        };
        if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }

        binding.returnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng centras = new LatLng(55.287158, 23.8);
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(centras).zoom(6.5f).bearing(0f).tilt(0f).build()));
            }
        });
    }

    private void fillCountries() {
        try {

            GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.detailed, binding.getRoot().getContext());
            GeoJsonPolygonStyle style = layer.getDefaultPolygonStyle();
            style.setFillColor(0xCC696969);
            //style.setStrokeColor(Color.DKGRAY);
            style.setStrokeWidth(1f);
            layer.addLayerToMap();
        } catch (IOException ex) {
            Log.e("IOException", ex.getLocalizedMessage());
        } catch (JSONException ex) {
            Log.e("JSONException", ex.getLocalizedMessage());
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getContext(), "Paspausta: " + marker.getTitle(), Toast.LENGTH_SHORT).show();

    }
}
