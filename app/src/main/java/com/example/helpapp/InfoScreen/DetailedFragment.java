package com.example.helpapp.InfoScreen;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.Data.Company;
import com.example.helpapp.Data.Company;
import com.example.helpapp.InfoFragment;
import com.example.helpapp.MainActivity;
import com.example.helpapp.R;
import com.example.helpapp.Adapters.RecyclerAdapter;
import com.example.helpapp.databinding.FragmentDetailedBinding;

import java.util.ArrayList;
import java.util.List;

public class DetailedFragment extends Fragment {

    private FragmentDetailedBinding binding;
    private RecyclerView recyclerView;
    public  RecyclerAdapter adapter;
    private ArrayList<Company> companiesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentDetailedBinding.inflate(inflater, container, false);
        MainActivity activity = (MainActivity) getActivity();
        if (activity.companiesList!=null) {
            companiesList = activity.companiesList;

            recyclerView = binding.getRoot().findViewById(R.id.recycler_view_detailed);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);

            recyclerView.setHasFixedSize(true);
            adapter = new RecyclerAdapter(getContext(), companiesList);
            recyclerView.setAdapter(adapter);
        }
        return binding.getRoot();
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//
//        inflater.inflate(R.menu.menu_toolbar, menu);
//        menu.findItem(R.id.search);
//
//        //SearchView searchView = (SearchView) menu.getActionView();
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//    }

}
