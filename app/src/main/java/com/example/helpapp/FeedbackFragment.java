package com.example.helpapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.Adapters.MessageAdapter;
import com.example.helpapp.Data.Messages;
import com.example.helpapp.databinding.FragmentFeedbackBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class FeedbackFragment extends Fragment {

    private FragmentFeedbackBinding binding;
    private RecyclerView recyclerView;
    private String[] aname = {
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

    String[] amessage = {
            "Description1",
            "Description2",
            "Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d Description3d 9",
            "Description4",
            "Description5",
            "Description6",
            "Description7",
            "Description8",
            "Description9",
            "Description10"
    };

    int[] aimage = {
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

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false);

        recyclerView = binding.getRoot().findViewById(R.id.recycler_view_feedback);

        List<Messages> sampleMessage = new ArrayList<>();

        for (int i = 0; i < aname.length; i++) {

            Messages messages = new Messages();

            messages.name = aname[i];
            messages.massage = amessage[i];
            messages.image = aimage[i];
            sampleMessage.add(messages);

        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new MessageAdapter(binding.getRoot().getContext(), sampleMessage));

        return binding.getRoot();
    }
}

