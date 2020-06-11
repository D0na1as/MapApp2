package com.example.helpapp.InfoScreen;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.Adapters.IndividualAdapter;
import com.example.helpapp.Adapters.RecyclerAdapter;
import com.example.helpapp.Data.Rcp_data;
import com.example.helpapp.Data.Recipient;
import com.example.helpapp.InfoFragment;
import com.example.helpapp.R;
import com.example.helpapp.databinding.FragmentIndividualBinding;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

public class IndividualFragment extends Fragment {

    private FragmentIndividualBinding binding;
    private RecyclerView recyclerView;
    private String[] status = {
            "Yra",
            "Nera",
            "Yra",
            "Yra",
            "Nera",
            "Nera",
            "Yra",
    };

    private int[] image = {
            R.mipmap.box,
            R.mipmap.cap,
            R.mipmap.gloves,
            R.mipmap.goggles,
            R.mipmap.mask,
            R.mipmap.shoe_covers,
            R.mipmap.suit
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);

        Toast.makeText(getContext(), "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentIndividualBinding.inflate(inflater, container, false);

        recyclerView = binding.getRoot().findViewById(R.id.recycler_view_individual);
//
//        ImageView mainImage = binding.getRoot().findViewById(R.id.image_individual);
//        mainImage.setImageResource(InfoFragment.imageIndividual);

        //Toast.makeText(getContext(), "onCreateView", Toast.LENGTH_SHORT).show();

        List<Rcp_data> sampleData = new ArrayList<>();

        for (int i = 0; i < status.length; i++) {

            Rcp_data data = new Rcp_data();

            data.status = status[i];
            data.image = image[i];
            sampleData.add(data);

        }

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new IndividualAdapter(requireContext(), sampleData));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        ImageView mainImage = binding.getRoot().findViewById(R.id.image_individual);
        mainImage.setImageResource(InfoFragment.imageIndividual);


    }
}

