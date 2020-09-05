package com.example.helpapp.UserScreen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.Adapters.IndividualAdapter;
import com.example.helpapp.Data.Company;
import com.example.helpapp.MainActivity;
import com.example.helpapp.Objects.Recipient;
import com.example.helpapp.R;
import com.example.helpapp.databinding.RegisterRecipientBinding;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipientRegister extends Fragment {

    private RegisterRecipientBinding binding;
    private Recipient recipient;
    public static Recipient rec;

    private TextInputLayout email;
    private TextInputLayout password1;
    private TextInputLayout password2;
    private TextInputLayout name;
    private TextInputLayout acPerson;
    private TextInputLayout phone;
    private TextInputLayout street;
    private TextInputLayout houseNr;
    private TextInputLayout city;
    private double longitude;
    private double latitude;
    private String streetInput;
    private String cityInput;
    private String houseNrInput;
    private String emailInput;
    private boolean validAddress = false;
    private boolean emailUnique = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = RegisterRecipientBinding.inflate(inflater, container, false);

        email = binding.getRoot().findViewById(R.id.c_email_recipient);
        password1 = binding.getRoot().findViewById(R.id.c_password1_recipient);
        password2 = binding.getRoot().findViewById(R.id.c_password2_recipient);
        name = binding.getRoot().findViewById(R.id.c_name_recipient);
        acPerson = binding.getRoot().findViewById(R.id.c_ac_person_recipient);
        phone = binding.getRoot().findViewById(R.id.c_phone_recipient);
        street = binding.getRoot().findViewById(R.id.c_street_recipient);
        houseNr = binding.getRoot().findViewById(R.id.c_nr_house_recipient);
        city = binding.getRoot().findViewById(R.id.c_city_recipient);

        Button register = binding.getRoot().findViewById(R.id.user_login);
        register.setOnClickListener(this::confirmInput);

        return binding.getRoot();
    }

    private boolean validateEmail() {
        emailInput = email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            email.setError("Laukas tuščias!");
            return false;
        } else if (!emailUnique) {
            email.setError("Toks vartotojas jau yra!");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validatePassword1() {
        String passwordInput1 = password1.getEditText().getText().toString().trim();
        if (passwordInput1.isEmpty()) {
            password1.setError("Laukas tuščias!");
            return false;
        } else if (passwordInput1.length() < 6) {
            password1.setError("Slaptažodis pertrumpas!");
            return false;
        } else {
            password1.setError(null);
            return true;
        }
    }

    private boolean validatePassword2() {
        String passwordInput2 = password2.getEditText().getText().toString().trim();
        String passwordInput1 = password1.getEditText().getText().toString().trim();
        if (passwordInput2.isEmpty()) {
            password2.setError("Laukas tuščias!");
            return false;
        } else if (passwordInput2.length() < 6) {
            password2.setError("Slaptažodis pertrumpas!");
            return false;
        } else if (!passwordInput2.equals(passwordInput1)) {
            password2.setError("Slaptažodžiai nesutampa!");
            return false;
        } else {
            password2.setError(null);
            return true;
        }
    }

    private boolean validateName() {
        String namedInput = name.getEditText().getText().toString().trim();
        if (namedInput.isEmpty()) {
            name.setError("Laukas tuščias!");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }

    private boolean validatePhone() {
        String phoneInput = phone.getEditText().getText().toString().trim();
        if (phoneInput.isEmpty()) {
            phone.setError("Laukas tuščias!");
            return false;
        } else {
            phone.setError(null);
            return true;
        }
    }

    private boolean validateAcPerson() {
        String acPersonInput = acPerson.getEditText().getText().toString().trim();
        if (acPersonInput.isEmpty()) {
            acPerson.setError("Laukas tuščias!");
            return true;
        } else {
            acPerson.setError(null);
            return true;
        }
    }

    private boolean validateStreet() {
        streetInput = street.getEditText().getText().toString().trim();
        if (streetInput.isEmpty()) {
            street.setError("Laukas tuščias!");
            return false;
        } else {
            street.setError(null);
            return true;
        }
    }

    private boolean validateHouseNr() {
        houseNrInput = houseNr.getEditText().getText().toString().trim();
        if (houseNrInput.isEmpty()) {
            houseNr.setError("Laukas tuščias!");
            return false;
        } else {
            houseNr.setError(null);
            return true;
        }
    }

    private boolean validateCity() {
        cityInput = city.getEditText().getText().toString().trim();

        if (cityInput.isEmpty()) {
            city.setError("Laukas tuščias!");
            return false;
        } else {
            city.setError(null);
            return true;
        }
    }

    private boolean validateAddress() {
        if (!validAddress) {
            street.setError("Blogas adresas!");
            houseNr.setError("Blogas adresas!");
            city.setError("Blogas adresas!");
            return false;
        } else {
            city.setError(null);
            houseNr.setError(null);
            city.setError(null);
            return true;
        }
    }

    private void confirmInput(View view) {
        streetInput = street.getEditText().getText().toString().trim();
        houseNrInput = houseNr.getEditText().getText().toString().trim();
        cityInput = city.getEditText().getText().toString().trim();
        emailInput = email.getEditText().getText().toString().trim();
        checkEmail(emailInput);
        if (!streetInput.isEmpty() && !houseNrInput.isEmpty() && !cityInput.isEmpty()) {
            getLocation(streetInput, Integer.parseInt(houseNrInput), cityInput);
        }
        if (!validateEmail() | !validatePassword1() | !validatePassword2() | !validateName()
                | !validateAcPerson() | !validatePhone() | !validateStreet() |
                !validateCity() | !validateHouseNr()) {
            return;
        }
    }

    //Tikrina ar tokia vieta yra ir grazina ilguma ir platuma
    private void getLocation(String street, int houseNr, String city) {
        MainActivity activity = (MainActivity) getActivity();
        Call<Recipient> call = activity.getService().getLocation(street, houseNr, city);
        call.enqueue(new Callback<Recipient>() {
            @Override
            public void onResponse(@NotNull Call<Recipient> call, @NotNull Response<Recipient> response) {

                recipient = response.body();
                if (recipient == null) {
                    validAddress = false;
                    validateAddress();
                } else {
                    validAddress = true;
                    latitude = recipient.getLatitude();
                    longitude = recipient.getLongitude();
                    validateAddress();
                    if (validateEmail() & validatePassword1() & validatePassword2() & validateName()
                            & validateAcPerson() & validatePhone() & validateStreet() &
                            validateCity() & validateHouseNr() & validAddress) {
                        registerRecipient();

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<Recipient> call, @NotNull Throwable t) {
                validAddress = false;
                validateAddress();
                t.printStackTrace();
            }
        });
    }
    //Tikrina ar toks el.pastas jau yra bazeje
    private void checkEmail(String email) {

        MainActivity activity = (MainActivity) getActivity();
        Call<Recipient> call = activity.getService().checkEmail(email);
        call.enqueue(new Callback<Recipient>() {
            @Override
            public void onResponse(@NotNull Call<Recipient> call, @NotNull Response<Recipient> response) {

                recipient = response.body();
                if (recipient == null) {
                    emailUnique = true;
                    validateEmail();
                } else {
                    emailUnique = false;
                    validateEmail();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Recipient> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //Naujo vartotojo registracija
    private void registerRecipient() {
        MainActivity mainActivity = (MainActivity) getActivity();
        Call<ResponseBody> call = mainActivity.getService().registerRecipient(
                email.getEditText().getText().toString().trim(),
                password1.getEditText().getText().toString().trim(),
                name.getEditText().getText().toString().trim(),
                acPerson.getEditText().getText().toString().trim(),
                phone.getEditText().getText().toString().trim(),
                street.getEditText().getText().toString().trim(),
                Integer.parseInt(houseNr.getEditText().getText().toString().trim()),
                city.getEditText().getText().toString().trim(),
                longitude, latitude);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Sėkmingai prisijungta!", Toast.LENGTH_LONG).show();
                    recipient.setPassword(password1.getEditText().getText().toString().trim());
                    mainActivity.saveRecipient(recipient);
                    rec = recipient;
                    goToRecipientFragment(binding.getRoot());
                    Log.i("Suma capsu ", response.body().toString());
                } else {
                    Toast.makeText(getContext(), "Klaida! Registracija nepavyko!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void goToRecipientFragment(View view) {
        Navigation.findNavController(view).navigate(R.id.recipientFragment);
    }
}
