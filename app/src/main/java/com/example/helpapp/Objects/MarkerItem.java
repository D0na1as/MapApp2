package com.example.helpapp.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MarkerItem implements ClusterItem {

    public  String title;
    public int profilePhoto;
    private LatLng mPosition;
    public int id;

    public MarkerItem(String title, int profilePhoto, LatLng mPosition, int id) {
        this.title = title;
        this.profilePhoto = profilePhoto;
        this.mPosition = mPosition;
        this.id = id;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Nullable
    @Override
    public String getTitle() {
        return null;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return null;
    }
}
