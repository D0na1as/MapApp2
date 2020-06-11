package com.example.helpapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatCallback;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.helpapp.InfoScreen.DetailedFragment;
import com.example.helpapp.InfoScreen.IndividualFragment;
import com.example.helpapp.InfoScreen.OverallFragment;
import com.example.helpapp.databinding.FragmentInfoBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;
    private final static int NUM_OF_TABS = 3;
    private TabAdapter tabAdapter;
    public static ViewPager2 viewPager;
    public static int imageIndividual;


    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
//    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater)
//    {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_toolbar, menu);
//        MenuItem searchItem = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        //searchView.setOnQueryTextListener();
//        searchView.setQueryHint("Nieko nerasi");
//    }
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);

        Toolbar toolbar = binding.getRoot().findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(" Informacija");
        toolbar.setLogo(R.drawable.info_outline);

        imageIndividual = R.mipmap.cap;
        viewPager = binding.getRoot().findViewById(R.id.view_pager);
        TabLayout tabLayout = binding.getRoot().findViewById(R.id.tab_layout);
        tabAdapter = new TabAdapter(this);

        viewPager.setAdapter(tabAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (TabLayout.Tab tab, int position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Bendra Informacija");
                            break;
                        case 1:
                            tab.setText("Visos vietos");
                            break;
                        case 2:
                            tab.setText("Detali informacija");
                            break;
                    }
                }).attach();
        return binding.getRoot();
    }

    public static class TabAdapter extends FragmentStateAdapter {
        @Override
        public void registerFragmentTransactionCallback(@NonNull FragmentTransactionCallback callback) {
            super.registerFragmentTransactionCallback(callback);
        }

        public TabAdapter(Fragment fragment) {

            super(fragment);
            if (fragment!=null)
            Log.i("nenulas", "nenulas");
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new OverallFragment();
                case 1:
                    return new DetailedFragment();
                case 2:
                    return new IndividualFragment();
            }
            return null;

        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getItemCount() {

            return NUM_OF_TABS;
        }
    }

}
