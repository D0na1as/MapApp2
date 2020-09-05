package com.example.helpapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatCallback;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;

import com.example.helpapp.Data.Statistic;
import com.example.helpapp.InfoScreen.DetailedFragment;
import com.example.helpapp.InfoScreen.IndividualFragment;
import com.example.helpapp.InfoScreen.OverallFragment;
import com.example.helpapp.databinding.FragmentInfoBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;
    private final static int NUM_OF_TABS = 3;
    private TabAdapter tabAdapter;
    private TabLayout tabLayout;
    public static ViewPager2 viewPager;


    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDetach() {
        super.onDetach();
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

        viewPager = binding.getRoot().findViewById(R.id.view_pager);
        tabLayout = binding.getRoot().findViewById(R.id.tab_layout);
        if (viewPager.getAdapter() != null) {
            viewPager.setAdapter(null);
        }

        tabAdapter = new TabAdapter(getParentFragment());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //MainActivity.infoTabPos = tab.getPosition();
                if (tab.getPosition() == 2) {
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2) {
//                    FragmentManager manager = getChildFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.remove(individualFragment);
//                    transaction.commit();
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.setAdapter(tabAdapter);
        viewPager.setCurrentItem(MainActivity.infoTabPos, false);

        new TabLayoutMediator(tabLayout, viewPager, true,
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

    public class TabAdapter extends FragmentStateAdapter {

        public TabAdapter(Fragment fragment) {
            super(fragment);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
//                    if (overallFragment == null) {
//                        overallFragment = new OverallFragment();
//                        return overallFragment;
//                    } else {
//                        return overallFragment;
//                    }
                    return new OverallFragment();
                case 1:
//                    if (detailedFragment == null) {
//                        detailedFragment = new DetailedFragment();
//                        return detailedFragment;
//                    } else {
//                        return detailedFragment;
//                    }
                    return new DetailedFragment();
                case 2:
//                    if (individualFragment == null) {
//                        individualFragment = new IndividualFragment();
//                        return individualFragment;
//                    } else {
//                        return individualFragment;
//                    }
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
