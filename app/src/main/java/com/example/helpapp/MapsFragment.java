package com.example.helpapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.helpapp.Data.Company;
import com.example.helpapp.Objects.MarkerItem;
import com.example.helpapp.Objects.Node;
import com.example.helpapp.Objects.Recipient;
import com.google.android.gms.maps.SupportMapFragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpapp.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.collections.MarkerManager;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import com.google.maps.android.ui.IconGenerator;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.helpapp.MainActivity.bottomNavigationView;

public class MapsFragment extends Fragment implements OnMapReadyCallback, ClusterManager.OnClusterClickListener<MarkerItem> {

    private FragmentMapsBinding binding;
    private GoogleMap mMap;
    private SupportMapFragment fragment;

    private ClusterManager<MarkerItem> clusterManager;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private LatLngBounds Lietuva = new LatLngBounds(
            new LatLng(53.914964, 21.077476), new LatLng(56.310243, 26.884515));
    private ArrayList<Recipient> nodes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (fragment == null) {
            binding = FragmentMapsBinding.inflate(inflater, container, false);
//            SearchView searchField = binding.getRoot().findViewById(R.id.searchView);
//            searchField.setBackgroundResource(R.drawable.search_field_rounded);


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
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        //mMap.setOnInfoWindowClickListener(this);

        MapStyleOptions mapStyleOptions = MapStyleOptions.loadRawResourceStyle(binding.getRoot().getContext(), R.raw.map_style);
        googleMap.setMapStyle(mapStyleOptions);
        //mMap.setMyLocationEnabled(true);
        mMap.setMinZoomPreference(6.5f);
        // Constrain the camera target bounds.
        mMap.setLatLngBoundsForCameraTarget(Lietuva);
        LatLng centras = new LatLng(55.287158, 23.8);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(centras).zoom(6.5f).bearing(0f).tilt(0f).build()));
        fillCountries();
        if (!IntListener.getNodesBoolean()) {
            connectionFailed();
        } else {
            MainActivity activity = (MainActivity) getActivity();
            nodes = new ArrayList<>(activity.nodes);
            clusterManager = new ClusterManager<>(binding.getRoot().getContext(), mMap);
            clusterManager.setRenderer(new PersonRenderer());
            mMap.setOnCameraIdleListener(clusterManager);
            clusterManager.getMarkerCollection().setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    final LayoutInflater inflater = LayoutInflater.from(binding.getRoot().getContext());
                    final View view = inflater.inflate(R.layout.marker_layout, null);
                    final TextView textView = view.findViewById(R.id.marker_text);
                    String text = (marker.getTitle() != null) ? marker.getTitle() : "Cluster Item";
                    textView.setText(text);
                    return view;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    return null;
                }
            });
            mMap.setOnInfoWindowClickListener(clusterManager);
            clusterManager.setOnClusterClickListener(this);
            clusterManager.getMarkerCollection().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    MainActivity.viewedCompanyId = Integer.parseInt(marker.getSnippet());
                    MainActivity.infoTabPos = 2;
                    bottomNavigationView.setSelectedItemId(R.id.info_graph);
                }
            });
            for (Recipient dot : nodes) {
                clusterManager.addItem(new MarkerItem(dot.getCompany_name(),
                        MainActivity.getIcon(dot.getPriority()), new LatLng(dot.getLatitude(), dot.getLongitude()), dot.getId()));
            }

            clusterManager.cluster();
        }
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

                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
    private void connectionFailed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());

        builder.setTitle("Dėmesio!");
        builder.setMessage("Nepavyko prisijungti!");

        builder.setPositiveButton("Supratau", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
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

    //Padarys p[aveiksliuka vietoj markerio
    private class PersonRenderer extends DefaultClusterRenderer<MarkerItem> {

        public PersonRenderer() {
            super(requireContext(), mMap, clusterManager);

        }

        @Override
        protected void onBeforeClusterItemRendered(@NonNull MarkerItem marker, MarkerOptions markerOptions) {
            // Draw a single person - show their profile photo and set the info window to show their name
            markerOptions.icon(BitmapDescriptorFactory.
                    fromResource(marker.profilePhoto)).title(marker.title).snippet(String.valueOf(marker.id));
        }

//        @Override
//        protected void onClusterItemUpdated(@NonNull MarkerItem markerItem, Marker marker) {
//            // Same implementation as onBeforeClusterItemRendered() (to update cached markers)
//
//        }
//
//        @Override
//        protected void onBeforeClusterRendered(@NonNull Cluster<MarkerItem> cluster, MarkerOptions markerOptions) {
//            // Draw multiple people.
//            // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
//
//        }
//
//        @Override
//        protected void onClusterUpdated(@NonNull Cluster<MarkerItem> cluster, Marker marker) {
//            // Same implementation as onBeforeClusterRendered() (to update cached markers)
//
//
//        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 1;
        }
    }

    @Override
    public boolean onClusterClick(Cluster<MarkerItem> cluster) {
        // Show a toast with some info when the cluster is clicked.
        String firstName = cluster.getItems().iterator().next().title;
        Toast.makeText(getContext(), cluster.getSize() + " (Įskaitant ir " + firstName + ")", Toast.LENGTH_SHORT).show();

        // Zoom in the cluster. Need to create LatLngBounds and including all the cluster items
        // inside of bounds, then animate to center of the bounds.

        // Create the builder to collect all essential cluster items for the bounds.
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
            builder.include(item.getPosition());
        }
        // Get the LatLngBounds
        final LatLngBounds bounds = builder.build();

        // Animate camera to the bounds
        try {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
