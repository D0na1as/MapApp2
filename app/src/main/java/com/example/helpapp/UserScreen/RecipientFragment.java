package com.example.helpapp.UserScreen;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.Adapters.UserAdapter;
import com.example.helpapp.ConnectionBooleanChangedListener;
import com.example.helpapp.Data.Rcp_data;
import com.example.helpapp.IntListener;
import com.example.helpapp.MainActivity;
import com.example.helpapp.Objects.Recipient;
import com.example.helpapp.R;
import com.example.helpapp.databinding.FragmentRecipientBinding;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class RecipientFragment extends Fragment {

    private FragmentRecipientBinding binding;
    private RecyclerView recyclerView;

    private Recipient recipient;
    private static int[] values = new int[7];
    private String[] fieldsToSave = new String[4];

    private EditText title;
    private EditText acPerson;
    private EditText email;
    private EditText password;
    private EditText street;
    private EditText houseNr;
    private EditText city;
    private EditText phone;
    private ImageView priorityImage;
    private EditText priorityText;

    private Button logout;
    private Button removeUser;
    private Button saveUser;
    private MainActivity activity;

    private int checkedId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRecipientBinding.inflate(inflater, container, false);

        activity = (MainActivity) getActivity();
        recipient = activity.getRecipient();

        recyclerView = binding.getRoot().findViewById(R.id.recycler_view_user);

        values[0] = recipient.getShoe_covers();
        values[1] = recipient.getCaps();
        values[2] = recipient.getGoggles();
        values[3] = recipient.getSuits();
        values[4] = recipient.getMasks();
        values[5] = recipient.getGloves();
        values[6] = recipient.getPriority();

        logout = binding.getRoot().findViewById(R.id.user_logout);
        logout.setOnClickListener(this::onClickLogout);

        removeUser = binding.getRoot().findViewById(R.id.user_remove);
        removeUser.setOnClickListener(this::onClickRemoveUser);

        saveUser = binding.getRoot().findViewById(R.id.user_edit);
        saveUser.setVisibility(View.INVISIBLE);
        saveUser.setOnClickListener(this::onClickSaveUser);

        title = binding.getRoot().findViewById(R.id.u_title);
        title.setText(recipient.getCompany_name());
        fieldsToSave[0] = recipient.getCompany_name();
        title.addTextChangedListener(new CustomTextWatcher(title, 0));

        acPerson = binding.getRoot().findViewById(R.id.u_ac_person);
        acPerson.setText(recipient.getAc_person());
        fieldsToSave[1] = recipient.getAc_person();
        acPerson.addTextChangedListener(new CustomTextWatcher(acPerson, 1));

        email = binding.getRoot().findViewById(R.id.u_email);
        email.setText(recipient.getEmail());

        password = binding.getRoot().findViewById(R.id.u_password);
        fieldsToSave[1] = recipient.getPassword();
        password.addTextChangedListener(new CustomTextWatcher(password, 3));

        street = binding.getRoot().findViewById(R.id.u_street);

        street.setText(recipient.getStreet());

        houseNr = binding.getRoot().findViewById(R.id.u_str_number);
        houseNr.setText("" + recipient.getHouse_nr());

        city = binding.getRoot().findViewById(R.id.u_city);
        city.setText(recipient.getCity());

        phone = binding.getRoot().findViewById(R.id.u_phone);
        phone.setText(recipient.getPhone());
        fieldsToSave[2] = recipient.getPhone();
        phone.addTextChangedListener(new CustomTextWatcher(phone, 2));

        priorityImage = binding.getRoot().findViewById(R.id.u_image_priority);
        priorityImage.setImageResource(MainActivity.getIcon(recipient.getPriority()));
        priorityText = binding.getRoot().findViewById(R.id.u_priority_user);
        priorityText.setText(MainActivity.itemDescription("" + values[6]));

        //paslepti klaviatura
        binding.getRoot().findViewById(R.id.recipient_main_layout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View currentFocusedView = activity.getCurrentFocus();
                if (currentFocusedView != null) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return true;
                }
                return false;
            }
        });
        LinearLayout priorityField = binding.getRoot().findViewById(R.id.u_priority_title);
        priorityField.setClickable(true);
        priorityField.setOnClickListener(this::onClickPriorityField);

        List<Rcp_data> sampleData = new ArrayList<>();
        sampleData.add(new Rcp_data(MainActivity.getIcon(1), values[0], MainActivity.itemDescription("shoe_covers")));
        sampleData.add(new Rcp_data(MainActivity.getIcon(2), values[1], MainActivity.itemDescription("caps")));
        sampleData.add(new Rcp_data(MainActivity.getIcon(3), values[2], MainActivity.itemDescription("goggles")));
        sampleData.add(new Rcp_data(MainActivity.getIcon(4), values[3], MainActivity.itemDescription("suits")));
        sampleData.add(new Rcp_data(MainActivity.getIcon(5), values[4], MainActivity.itemDescription("masks")));
        sampleData.add(new Rcp_data(MainActivity.getIcon(6), values[5], MainActivity.itemDescription("gloves")));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new UserAdapter(requireContext(), sampleData));

        IntListener.addMyBooleanListener(new ConnectionBooleanChangedListener() {
            @Override
            public void OnMyBooleanChanged() {
                if (IntListener.getMyBoolean()) {
                    saveUser.setVisibility(View.VISIBLE);

                }
            }
        });

        return binding.getRoot();
    }

    private void onClickPriorityField(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
        builder.setMessage("Ko labiausia reikia?");
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.priority_dialog, null);
        builder.setView(dialogView);
        RadioGroup myRadioGroup = dialogView.findViewById(R.id.priority_radiogroup);
        builder.setPositiveButton("Pasirinkti", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                prioritySelected(checkedId);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Atšaukti", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setSelectedPriority(checkedId);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
            }
        });
    }

    private void setSelectedPriority(int selectedItem) {
        checkedId = selectedItem;
    }

    private void prioritySelected(int checkedId) {
        IntListener.setMyBoolean(true);
        switch (checkedId) {
            case R.id.priority_shoe_covers:
                priorityImage.setImageResource(MainActivity.getIcon(1));
                priorityText.setText(MainActivity.itemDescription("" + 1));
                updateDataArray(6, 1);
                break;
            case R.id.priority_caps:
                priorityImage.setImageResource(MainActivity.getIcon(2));
                priorityText.setText(MainActivity.itemDescription("" + 2));
                updateDataArray(6, 2);
                break;
            case R.id.priority_goggles:
                priorityImage.setImageResource(MainActivity.getIcon(3));
                priorityText.setText(MainActivity.itemDescription("" + 3));
                updateDataArray(6, 3);
                break;
            case R.id.priority_suits:
                priorityImage.setImageResource(MainActivity.getIcon(4));
                priorityText.setText(MainActivity.itemDescription("" + 4));
                updateDataArray(6, 4);
                break;
            case R.id.priority_masks:
                priorityImage.setImageResource(MainActivity.getIcon(5));
                priorityText.setText(MainActivity.itemDescription("" + 5));
                updateDataArray(6, 5);
                break;
            case R.id.priority_gloves:
                priorityImage.setImageResource(MainActivity.getIcon(6));
                priorityText.setText(MainActivity.itemDescription("" + 6));
                updateDataArray(6, 6);
                break;
            case R.id.priority_ok:
                priorityImage.setImageResource(MainActivity.getIcon(7));
                priorityText.setText(MainActivity.itemDescription("" + 7));
                updateDataArray(6, 7);
                break;
        }
    }

    private void onClickSaveUser(View view) {
        if (IntListener.getMyBoolean()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());

            builder.setTitle("Dėmesio!");
            builder.setMessage("Ar tikrai norite išsaugoti pakeitimus?");

            builder.setPositiveButton("Išsaugoti", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    saveUser.setVisibility(View.INVISIBLE);
                    recipient.setCompany_name(fieldsToSave[0]);

                    if (!password.getText().toString().trim().isEmpty()) {
                        recipient.setPassword(fieldsToSave[3]);
                    }

                    recipient.setAc_person(fieldsToSave[1]);
                    recipient.setPhone(fieldsToSave[2]);
                    recipient.setShoe_covers(values[0]);
                    recipient.setCaps(values[1]);
                    recipient.setGoggles(values[2]);
                    recipient.setSuits(values[3]);
                    recipient.setMasks(values[4]);
                    recipient.setGloves(values[5]);
                    recipient.setPriority(values[6]);
                    onClickUpdate(binding.getRoot());
                    activity.saveRecipient(recipient);
                    ;
                    Toast.makeText(getContext(), "Pakeitimai išsaugoti!", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("Atšaukti", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alert = builder.create();
            alert.show();

        }
    }

    private void onClickRemoveUser(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());

        builder.setTitle("Dėmesio!");
        builder.setMessage("Ar tikrai norite ištrinti paskyrą?");

        builder.setPositiveButton("Ištrinti", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                deleteRecipient(recipient.getId());
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Atšaukti", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void onClickLogout(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());

        builder.setTitle("Dėmesio!");
        builder.setMessage("Ar tikrai norite atsijungti iš paskyros?");

        builder.setPositiveButton("Atsijungti", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                MainActivity activity = (MainActivity) requireActivity();
                activity.logoutUser();
                Toast.makeText(getContext(), "Išsiregistravote!", Toast.LENGTH_LONG).show();
                goToUserFragment(view);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Atšaukti", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void goToUserFragment(View view) {
        Navigation.findNavController(view).navigate(R.id.userFragment);
    }

    //Tikrina lauku pasikeitima
    private class CustomTextWatcher implements TextWatcher {
        private EditText editText;
        private int position;

        public CustomTextWatcher(EditText e, int i) {
            editText = e;
            position = i;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            IntListener.setMyBoolean(true);
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (editText.getText().toString().trim().isEmpty() && editText!=binding.getRoot().findViewById(R.id.u_password)) {
                editText.setError("Laukas tuščias!");
            } else {
                fieldsToSave[position] = editText.getText().toString().trim();
            }
        }
    }

    public static void updateDataArray(int position, int value) {
        values[position] = value;
    }

    private void onClickUpdate(View view) {
        Call<Recipient> call = activity.getService().updateRecipient(recipient.getId(),
                recipient.getEmail(), recipient.getPassword(), recipient.getCompany_name(),
                recipient.getAc_person(), recipient.getPhone(), recipient.getCaps(), recipient.getGloves(),
                recipient.getGoggles(), recipient.getMasks(), recipient.getShoe_covers(), recipient.getSuits(),
                recipient.getPriority());
        call.enqueue(new Callback<Recipient>() {
            @Override
            public void onResponse(@NotNull Call<Recipient> call, @NotNull Response<Recipient> response) {

                recipient = response.body();
                if (recipient == null) {
                } else {
                }
            }
            @Override
            public void onFailure(@NotNull Call<Recipient> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void deleteRecipient(int id) {

        MainActivity activity = (MainActivity) getActivity();
        Call<ResponseBody> call = activity.getService().delete(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {

                if (response.body() == null) {
                    Toast.makeText(getContext(), "Klaida. Ištrinti vartotojo nepavyko!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Vartotojas sėkmingai ištrintas!", Toast.LENGTH_LONG).show();
                    activity.logoutUser();
                    goToUserFragment(binding.getRoot());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
