package com.example.helpapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpapp.Adapters.IndividualAdapter;
import com.example.helpapp.Objects.Recipient;
import com.example.helpapp.databinding.FragmentDetailedBinding;
import com.example.helpapp.databinding.FragmentUserBinding;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserFragment extends Fragment {


    private FragmentUserBinding binding;
    private Recipient recipient;
    private int selected = 0;
    private RadioButton recipientBtn;
    private RadioButton giverBtn;
    private TextView userReset;
    private Button userLogin;
    private Button userRegister;
    private EditText email;
    private MainActivity activity;
    private boolean emailUnique = true;
    private boolean badPassword = false;
    EditText password;


    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);



        userLogin = binding.getRoot().findViewById(R.id.user_login);
        userLogin.setOnClickListener(this::onClickLogin);
        userRegister = binding.getRoot().findViewById(R.id.user_register);
        userRegister.setOnClickListener(this::goToRecipientRegister);
        userReset = binding.getRoot().findViewById(R.id.user_reset);
        userReset.setOnClickListener(this::goToResetFragment);
        email = binding.getRoot().findViewById(R.id.user_name);
        password = binding.getRoot().findViewById(R.id.user_password);

        activity = (MainActivity) getActivity();

        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    private void goToRecipientRegister(View view) {
        Navigation.findNavController(view).navigate(R.id.recipientRegister);
    }

    private void goToResetFragment(View view) {
        Navigation.findNavController(view).navigate(R.id.resetFragment);
    }

    private void onClickLogin(View view) {

        if (validateEmail() | validatePassword()) {
            Call<Recipient> call = activity.getService().login(getEmail(), getPassword());
            call.enqueue(new Callback<Recipient>() {
                @Override
                public void onResponse(@NotNull Call<Recipient> call, @NotNull Response<Recipient> response) {

                    recipient = response.body();
                    if (recipient == null) {
                        emailUnique = false;
                        validateEmail();
                    } else if (recipient.getId() == 0) {
                        emailUnique = true;
                        badPassword = true;
                        validateEmail();
                        validatePassword();
                    } else {
                        recipient.setPassword(password.getText().toString().trim());
                        activity.saveRecipient(recipient);
                        Toast.makeText(getContext(), "Sėkmingai prisijungėte!", Toast.LENGTH_LONG).show();
                        goToRecipientFragment(binding.getRoot());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Recipient> call, @NotNull Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    private boolean validateEmail() {
        if (getEmail().isEmpty()) {
            email.setError("Laukas tuščias!");
            return false;
        } else if (!emailUnique) {
            email.setError("Tokio vartotojo nėra!");
            emailUnique = true;
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        if (getPassword().isEmpty()) {
            password.setError("Laukas tuščias!");
            return false;
        }  else if (badPassword) {
            password.setError("Neteisingas slaptažodis!");
            badPassword=false;
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
    private void goToRecipientFragment(View view) {
        Navigation.findNavController(view).navigate(R.id.recipientFragment);
    }
    @Override
    public void onResume() {
        super.onResume();

        Recipient memUser = activity.getRecipient();
        if ( memUser !=null ) {
            goToRecipientFragment(binding.getRoot());
        }
    }
    private String getEmail() {
        return email.getText().toString().trim();
    }
    private String getPassword() {
        return password.getText().toString().trim();
    }
//    private void goToUserInfo(FragmentUserBinding binding) {
//        NavDirections action = UserFragmentDirections.actionUserFragmentToIndividualFragment();
//        Navigation.findNavController(binding.getRoot()).navigate(action);
//    }

//    private void onClickUser(View view) {
//        switch (view.getId()) {
//            case R.id.user_login:
//                if (selected == 1) {
//                    Toast.makeText(getContext(), "Prisijungti kaip gavejui", Toast.LENGTH_SHORT).show();
//                    goToIndividualFragment(view);
//                } else if (selected == 2) {
//                    goToDetailedFragment (view);
//                    Toast.makeText(getContext(), "Prisijungti kaip davejui", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getContext(), "Pažymėkite kas būsite (Gavėjas ar Davėjas)", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.user_register:if (selected == 1) {
//                Toast.makeText(getContext(), "Registruotis kaip gavejui", Toast.LENGTH_SHORT).show();
//                goToRecipientRegister(view);
//            } else if (selected == 2) {
//                Toast.makeText(getContext(), "Registruotis kaip davejui", Toast.LENGTH_SHORT).show();
//                goToGiverRegister(view);
//            } else {
//                Toast.makeText(getContext(), "Pažymėkite kas būsite (Gavėjas ar Davėjas)", Toast.LENGTH_SHORT).show();
//            }
//                break;
//            case R.id.user_reset:
//                Toast.makeText(getContext(), "Atkurti slaptazodu", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }

//    private void onClickRadio(View view) {
//        switch (radioGroup.getCheckedRadioButtonId()) {
////            case R.id.user_btn_left:
////                Toast.makeText(getContext(), "Kairysis", Toast.LENGTH_SHORT).show();
////                selected = 1;
////                break;
//            case R.id.user_btn_rgt:
//                Toast.makeText(getContext(), "Desinysis", Toast.LENGTH_SHORT).show();
//                selected = 2;
//                break;
//        }
//    }
}