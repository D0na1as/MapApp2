package com.example.helpapp.InfoScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.Adapters.IndividualAdapter;
import com.example.helpapp.Data.Company;
import com.example.helpapp.Data.Rcp_data;
import com.example.helpapp.InfoFragment;
import com.example.helpapp.MainActivity;
import com.example.helpapp.R;
import com.example.helpapp.databinding.FragmentIndividualBinding;

import java.util.ArrayList;
import java.util.List;

public class IndividualFragment extends Fragment {

    private FragmentIndividualBinding binding;
    public RecyclerView recyclerView;
    private Company shownCompany;
    private ArrayList<Company> companiesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentIndividualBinding.inflate(inflater, container, false);
        MainActivity activity = (MainActivity) getActivity();
        if (activity.companiesList!=null) {
            for (Company x : activity.companiesList) {
                if (x.getId() == MainActivity.viewedCompanyId) {
                    shownCompany = x;
                    break;
                }
            }

            recyclerView = binding.getRoot().findViewById(R.id.recycler_view_individual);

            ImageView mainImage = binding.getRoot().findViewById(R.id.image_individual);
            mainImage.setImageResource(MainActivity.getIcon(shownCompany.getPriority()));
            TextView title = binding.getRoot().findViewById(R.id.r_title_individual);
            title.setText(shownCompany.getCompany_name());
            TextView acPerson = binding.getRoot().findViewById(R.id.r_ac_person_individual);
            acPerson.setText(shownCompany.getAc_person());
            TextView email = binding.getRoot().findViewById(R.id.r_email_individual);
            email.setText(shownCompany.getEmail());
            TextView street = binding.getRoot().findViewById(R.id.r_street_individual);
            street.setText(shownCompany.getStreet());
            TextView nrHouse = binding.getRoot().findViewById(R.id.r_str_number_individual);
            nrHouse.setText("" + shownCompany.getHouse_nr());
            TextView companyCity = binding.getRoot().findViewById(R.id.r_city_individual);
            companyCity.setText(shownCompany.getCity());
            TextView phone = binding.getRoot().findViewById(R.id.r_phone_individual);
            phone.setText("tel.: " + shownCompany.getPhone());
            TextView urgentItem = binding.getRoot().findViewById(R.id.r_img_caption_individual);
            urgentItem.setText(MainActivity.itemDescription(String.valueOf(shownCompany.getPriority())));

            List<Rcp_data> sampleData = new ArrayList<>();
            sampleData.add(new Rcp_data(MainActivity.getIcon(1), shownCompany.getShoe_covers(), MainActivity.itemDescription("shoe_covers")));
            sampleData.add(new Rcp_data(MainActivity.getIcon(2), shownCompany.getCaps(), MainActivity.itemDescription("caps")));
            sampleData.add(new Rcp_data(MainActivity.getIcon(3), shownCompany.getGoggles(), MainActivity.itemDescription("goggles")));
            sampleData.add(new Rcp_data(MainActivity.getIcon(4), shownCompany.getSuits(), MainActivity.itemDescription("suits")));
            sampleData.add(new Rcp_data(MainActivity.getIcon(5), shownCompany.getMasks(), MainActivity.itemDescription("masks")));
            sampleData.add(new Rcp_data(MainActivity.getIcon(6), shownCompany.getGloves(), MainActivity.itemDescription("gloves")));

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);

            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new IndividualAdapter(requireContext(), sampleData));
        }
        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        //Fragment fragment = getTargetFragment();
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.remove(this).commit();
    }
}

