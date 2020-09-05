package com.example.helpapp.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.Data.Rcp_data;
import com.example.helpapp.IntListener;
import com.example.helpapp.R;
import com.example.helpapp.UserFragment;
import com.example.helpapp.UserScreen.RecipientFragment;
import com.example.helpapp.ViewHolders.HolderIndividual;
import com.example.helpapp.ViewHolders.HolderUser;
import com.example.helpapp.databinding.FragmentRecipientBinding;

import java.util.List;

import kotlin.text.Regex;

public class UserAdapter extends RecyclerView.Adapter<HolderUser> {

    private List<Rcp_data> data;
    private Context context;

    public UserAdapter(Context context, List<Rcp_data> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_user, parent, false);
        return new HolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderUser holder, int position) {

        Rcp_data sampleData = data.get(position);

        holder.image.setImageResource(sampleData.image);
        holder.name.setText(sampleData.name + ": ");
        holder.status.setText("" + sampleData.status);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.status.requestFocus();
                holder.status.setSelection(holder.status.length());
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(holder.status, InputMethodManager.SHOW_IMPLICIT);
            }

        });
        holder.status.addTextChangedListener(new CustomTextWatcher(holder.status, position));
        holder.status.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = v.findViewById(holder.status.getId());
                String text = editText.getText().toString().trim();
                if (!hasFocus) {
                    if (text.isEmpty()) {
                        editText.setText("0");
                    } else if (!text.equals("0")){
                        text = removeZero(text);
                        if (text.isEmpty()) {
                            editText.setText("0");
                        } else {
                            editText.setText(text);
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class CustomTextWatcher implements TextWatcher {
        private EditText editText;
        private int position;

        public CustomTextWatcher(EditText e, int position) {
            editText = e;
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
            if (!editText.getText().toString().trim().isEmpty()) {
                RecipientFragment.updateDataArray(position, Integer.parseInt(editText.getText().toString().trim()));
            }

        }
    }
    public static String removeZero(String str)
    {
        int i = 0;
        while (i < str.length() && str.charAt(i) == '0')
            i++;
        StringBuffer sb = new StringBuffer(str);

        sb.replace(0, i, "");

        return sb.toString();
    }
}


