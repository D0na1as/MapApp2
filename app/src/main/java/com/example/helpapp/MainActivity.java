package com.example.helpapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.example.helpapp.Data.Company;
import com.example.helpapp.Data.Statistic;
import com.example.helpapp.Objects.Recipient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String id = "id";
    public static final String password = "password";
    public static final String email = "email";
    public static final String company_name = "company_name";
    public static final String ac_person = "ac_person";
    public static final String phone = "phone";
    public static final String street = "street";
    public static final String house_nr = "house_nr";
    public static final String city = "city";
    public static final String latitude = "latitude";
    public static final String longitude = "longitude";
    public static final String caps = "caps";
    public static final String gloves = "gloves";
    public static final String goggles = "goggles";
    public static final String masks = "masks";
    public static final String shoe_covers = "shoe_covers";
    public static final String suits = "suits";
    public static final String priority = "priority";

    private List<Integer> NavGraphIds = new ArrayList<>();
    private ApiService service;
    public Statistic dataStatistic;
    public ArrayList<Company> companiesList;
    public static int viewedCompanyId = 1;
    public static BottomNavigationView bottomNavigationView;
    public static int infoTabPos = 0;
    public List<Recipient> nodes;
    private boolean gavo = false;
    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.168.0.110")
                .baseUrl("https://mapappserver.herokuapp.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
        sharedPref = getPreferences(Context.MODE_PRIVATE);

//        final ProgressDialog progressDoalog;
//        progressDoalog = new ProgressDialog(this);
        //progressDoalog.setMax(100);
        //progressDoalog.setMessage("Its loading....");
        //progressDoalog.setTitle("ProgressDialog bar example");
//        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
//        progressDoalog.show();
        getNodes();
        getStatistic();
        getCompanies();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

        bottomNavigationView = this.findViewById(R.id.bottom_navigation);
        //val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        NavGraphIds.add(R.navigation.map_graph);
        NavGraphIds.add(R.navigation.info_graph);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//
//            case R.id.edit:
//                Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.search:
//                MenuItem searchItem = findViewById(R.id.search);
//                SearchView searchView = (SearchView) searchItem.getActionView();
//
//                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                    @Override
//                    public boolean onQueryTextSubmit(String query) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onQueryTextChange(String newText) {
//                        DetailedFragment fragment = new DetailedFragment();
//                        fragment.adapter.getFilter().filter(newText);
//                        return false;
//                    }
//                });
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public ApiService getService() {

        return service;
    }

    //Prisijungimas prie serverio
    private void getNodes() {
        Call<List<Recipient>> call = getService().getNodes();
        call.enqueue(new Callback<List<Recipient>>() {
            @Override
            public void onResponse(@NotNull Call<List<Recipient>> call, @NotNull Response<List<Recipient>> response) {

                if (response.isSuccessful()) {
                    nodes = new ArrayList<>(response.body());
                    saveNodes(nodes);
                    IntListener.setNodesBoolean(true);
                } else {
                    nodes = getSavedNodes();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Recipient>> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getStatistic() {

        //Prisijungimas prie serverio
        Call<Statistic> call = getService().getStatistic();
        call.enqueue(new Callback<Statistic>() {
            @Override
            public void onResponse(Call<Statistic> call, Response<Statistic> response) {

                if (response.isSuccessful()) {
                    dataStatistic = response.body();
                    saveStatistic(dataStatistic);
                } else {
                    dataStatistic = getSavedStatistics();
                }
            }

            @Override
            public void onFailure(Call<Statistic> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getCompanies() {
        //Prisijungimas prie serverio
        Call<List<Company>> call = getService().getCompanies();
        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(@NotNull Call<List<Company>> call, @NotNull Response<List<Company>> response) {

                if (response.isSuccessful()) {
                    companiesList = new ArrayList<>(response.body());
                    saveCompanies(companiesList);
                } else {
                    companiesList = new ArrayList<>(getSavedCompanies());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Company>> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static int getIcon(int id) {
        int[] iconArray = {R.mipmap.box, R.mipmap.shoe_covers, R.mipmap.cap, R.mipmap.goggles,
                R.mipmap.suit, R.mipmap.mask, R.mipmap.gloves, R.mipmap.ok};
        return iconArray[id];
    }


    public static String itemDescription(String name) {
        switch (name) {
            case "shoe_covers":
            case "1":
                return "Antbačių";
            case "caps":
            case "2":
                return "Kepuraičių";
            case "goggles":
            case "3":
                return "Apsauginių akinių";
            case "suits":
            case "4":
                return "Vienkartinių kostiumų";
            case "masks":
            case "5":
                return "Apsauginių kaukių";
            case "gloves":
            case "6":
                return "Vienkartinių pirštinių";
            case "ok":
            case "7":
                return "Turime visko pakankamai";
        }
        return null;
    }

    public void saveNodes(List<Recipient> nodes) {
        Gson gson = new Gson();
        String nodesToString = gson.toJson(nodes);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nodes", nodesToString);
        editor.apply();
    }

    public List<Recipient> getSavedNodes() {

        Type type = new TypeToken<List<Recipient>>() {
        }.getType();
        String nodesToString = sharedPref.getString("nodes", "");
        Gson gson = new Gson();
        List<Recipient> item = gson.fromJson(nodesToString, type);

        return item;
    }

    public void saveStatistic(Statistic item) {

        Gson gson = new Gson();
        String nodesToString = gson.toJson(item);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("statistics", nodesToString);
        editor.apply();
    }

    public Statistic getSavedStatistics() {

        Type type = new TypeToken<Statistic>() {
        }.getType();
        String nodesToString = sharedPref.getString("statistics", "");
        Gson gson = new Gson();
        Statistic item = gson.fromJson(nodesToString, type);

        return item;
    }

    public void saveCompanies(List<Company> item) {
        Gson gson = new Gson();
        String nodesToString = gson.toJson(item);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("companies", nodesToString);
        editor.apply();
    }

    public List<Company> getSavedCompanies() {

        Type type = new TypeToken<List<Company>>() {
        }.getType();
        String nodesToString = sharedPref.getString("companies", "");
        Gson gson = new Gson();
        List<Company> item = gson.fromJson(nodesToString, type);

        return item;
    }
    public void saveRecipient(Recipient item) {
        Gson gson = new Gson();
        String nodesToString = gson.toJson(item);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("recipient", nodesToString);
        editor.apply();
    }

    public Recipient getRecipient() {

        Type type = new TypeToken<Recipient>() {}.getType();
        String nodesToString = sharedPref.getString("recipient", null);
        Gson gson = new Gson();
        Recipient item = gson.fromJson(nodesToString, type);

        return item;
    }
//    public void saveRecipient(Recipient recipient) {
//
//        SharedPreferences.Editor editor = sharedPref.edit();
//
//        editor.putInt(id, recipient.getId());
//        editor.putString(email, recipient.getEmail());
//        editor.putString(password, recipient.getPassword());
//        editor.putString(company_name, recipient.getCompany_name());
//        editor.putString(ac_person, recipient.getAc_person());
//        editor.putString(phone, recipient.getPhone());
//        editor.putString(street, recipient.getCity());
//        editor.putInt(house_nr, recipient.getHouse_nr());
//        editor.putString(city, recipient.getCity());
//        editor.putLong(latitude, Double.doubleToRawLongBits(recipient.getLatitude()));
//        editor.putLong(longitude, Double.doubleToRawLongBits(recipient.getLongitude()));
//        editor.putInt(caps, recipient.getCaps());
//        editor.putInt(gloves, recipient.getGloves());
//        editor.putInt(goggles, recipient.getGoggles());
//        editor.putInt(masks, recipient.getMasks());
//        editor.putInt(shoe_covers, recipient.getShoe_covers());
//        editor.putInt(suits, recipient.getSuits());
//        editor.putInt(priority, recipient.getPriority());
//
//        editor.apply();
//    }

//    public Recipient getRecipient() {
//
//        int idSaved = sharedPref.getInt(id, -1);
//        String passwordSaved = sharedPref.getString(password, "");
//        String emailSaved = sharedPref.getString(email, "");
//        String company_nameSaved = sharedPref.getString(company_name, "");
//        String ac_personSaved = sharedPref.getString(ac_person, "");
//        String phoneSaved = sharedPref.getString(phone, "");
//        String streetSaved = sharedPref.getString(street, "");
//        int house_nrSaved = sharedPref.getInt(house_nr, -1);
//        String citySaved = sharedPref.getString(city, "");
//        double latitudeSaved = Double.longBitsToDouble(sharedPref.getLong(latitude, Double.doubleToLongBits(0)));
//        double longitudeSaved = Double.longBitsToDouble(sharedPref.getLong(longitude, Double.doubleToLongBits(0)));
//        int capsSaved = sharedPref.getInt(caps, 0);
//        int glovesSaved = sharedPref.getInt(gloves, 0);
//        int gogglesSaved = sharedPref.getInt(goggles, 0);
//        int masksSaved = sharedPref.getInt(masks, 0);
//        int shoe_coversSaved = sharedPref.getInt(shoe_covers, 0);
//        int suitsSaved = sharedPref.getInt(suits, 0);
//        int prioritySaved = sharedPref.getInt(priority, 7);
//
//        Recipient recipient = new Recipient();
//        recipient.setId(idSaved);
//        recipient.setEmail(emailSaved);
//        recipient.setPassword(passwordSaved);
//        recipient.setCompany_name(company_nameSaved);
//        recipient.setAc_person(ac_personSaved);
//        recipient.setPhone(phoneSaved);
//        recipient.setStreet(streetSaved);
//        recipient.setHouse_nr(house_nrSaved);
//        recipient.setCity(citySaved);
//        recipient.setLatitude(latitudeSaved);
//        recipient.setLongitude(longitudeSaved);
//        recipient.setCaps(capsSaved);
//        recipient.setGloves(glovesSaved);
//        recipient.setGoggles(gogglesSaved);
//        recipient.setMasks(masksSaved);
//        recipient.setShoe_covers(shoe_coversSaved);
//        recipient.setSuits(suitsSaved);
//        recipient.setPriority(prioritySaved);
//
//        return recipient;
//    }

    public void logoutUser() {

        sharedPref.edit().remove("recipient").apply();
    }


//        sharedPref.edit().remove(id).apply();
//        sharedPref.edit().remove(email).apply();
//        sharedPref.edit().remove(password).apply();
//        sharedPref.edit().remove(company_name).apply();
//        sharedPref.edit().remove(ac_person).apply();
//        sharedPref.edit().remove(phone).apply();
//        sharedPref.edit().remove(street).apply();
//        sharedPref.edit().remove(house_nr).apply();
//        sharedPref.edit().remove(city).apply();
//        sharedPref.edit().remove(latitude).apply();
//        sharedPref.edit().remove(longitude).apply();
//        sharedPref.edit().remove(caps).apply();
//        sharedPref.edit().remove(gloves).apply();
//        sharedPref.edit().remove(goggles).apply();
//        sharedPref.edit().remove(masks).apply();
//        sharedPref.edit().remove(shoe_covers).apply();
//        sharedPref.edit().remove(suits).apply();
//        sharedPref.edit().remove(priority).apply();
//
//    }
}

