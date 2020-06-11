package com.example.helpapp.InfoScreen;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.Data.Recipient;
import com.example.helpapp.R;
import com.example.helpapp.Adapters.RecyclerAdapter;
import com.example.helpapp.databinding.FragmentDetailedBinding;

import java.util.ArrayList;
import java.util.List;

public class DetailedFragment extends Fragment {

    private FragmentDetailedBinding binding;
    private RecyclerView recyclerView;
    private String[] name = {
            "Recipient1",
            "Recipient2",
            "Recipient3",
            "Recipient4",
            "Recipient5",
            "Recipient6",
            "Recipient7",
            "Recipient8",
            "Recipient9",
            "Recipient10"
    };

    String[] description = {
            "Description1",
            "Description2",
            "Description3",
            "Description4",
            "Description5",
            "Description6",
            "Description7",
            "Description8",
            "Description9",
            "Description10"
    };

    int[] image = {
            R.mipmap.box,
            R.mipmap.cap,
            R.mipmap.gloves,
            R.mipmap.box,
            R.mipmap.goggles,
            R.mipmap.mask,
            R.mipmap.shoe_covers,
            R.mipmap.suit,
            R.mipmap.box,
            R.mipmap.cap
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentDetailedBinding.inflate(inflater, container, false);


        recyclerView = binding.getRoot().findViewById(R.id.recycler_view_detailed);

        List<Recipient> sampleRecipient = new ArrayList<>();

        for (int i = 0; i < name.length; i++) {

            Recipient recipient = new Recipient();

            recipient.recipientName = name[i];
            recipient.recipientDescription = description[i];
            recipient.recipientImage = image[i];
            sampleRecipient.add(recipient);

        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RecyclerAdapter(requireContext(), sampleRecipient));

        return binding.getRoot();
    }


}
