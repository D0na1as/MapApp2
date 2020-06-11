package com.example.helpapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Integer> NavGraphIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            setupBottomNavigationBar();
        } // Else, need to wait for onRestoreInstanceState

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar();
    }

    /**
     * Called on first creation and when restoring state.
     */
    @NonNull
    private void setupBottomNavigationBar() {

        BottomNavigationView bottomNavigationView = this.findViewById(R.id.bottom_navigation);

        //val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        NavGraphIds.add(R.navigation.map_graph);
        NavGraphIds.add(R.navigation.info_graph);
        NavGraphIds.add(R.navigation.feedback_graph);
        NavGraphIds.add(R.navigation.user_graph);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        NavigationExtensions nabExtension = new NavigationExtensions();

        // Setup the bottom navigation view with a list of navigation graphs
        LiveData controller = nabExtension.setupWithNavController(bottomNavigationView, NavGraphIds, fragmentManager, R.id.nav_host_fragment);
        NavController navController = (NavController) controller.getValue();

    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.edit:
                Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
