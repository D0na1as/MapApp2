package com.example.helpapp.UserScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.helpapp.MainActivity;
import com.example.helpapp.Objects.Recipient;
import com.example.helpapp.R;
import com.example.helpapp.databinding.FragmentResetBinding;

import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetFragment extends Fragment {

    private FragmentResetBinding binding;
    private EditText email;
    private boolean emailUnique;
    private MainActivity activity;
    private Recipient recipient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentResetBinding.inflate(inflater, container, false);

        email = binding.getRoot().findViewById(R.id.c_email_recreate);
        Button send = binding.getRoot().findViewById(R.id.password_send);

        send.setOnClickListener(this::onClickLogin);

        activity = new MainActivity();

        return binding.getRoot();
    }

    private boolean validateEmail() {
        String emailInput = email.getText().toString().trim();
        if (emailInput.isEmpty()) {
            email.setError("Laukas tuščias!");
            return false;
        } else if (emailUnique) {
            email.setError("Tokio vartotojo nėra!");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
    private void onClickLogin(View view) {

        String emailInput = email.getText().toString().trim();
        checkEmail(emailInput);
    }

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
                    resetPassword(email);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Recipient> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void resetPassword(String email) {

        MainActivity activity = (MainActivity) getActivity();
        Call<ResponseBody> call = activity.getService().reset(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {

                if (response.body() == null) {
                    Toast.makeText(getContext(), "Klaida. Nepavyko pakeisti slaptažodžio!", Toast.LENGTH_LONG).show();
                } else {
                    emailUnique = false;
                    validateEmail();
                    Toast.makeText(getContext(), "Slaptažodis sėkmingai išsiųstas!", Toast.LENGTH_LONG).show();
                    goToUserFragment(binding.getRoot());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void goToUserFragment(View view) {
        Navigation.findNavController(view).navigate(R.id.userFragment);
    }
}
