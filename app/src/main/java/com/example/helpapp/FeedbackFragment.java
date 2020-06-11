package com.example.helpapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.helpapp.databinding.FragmentFeedbackBinding;


public class FeedbackFragment extends Fragment {

    private FragmentFeedbackBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}

