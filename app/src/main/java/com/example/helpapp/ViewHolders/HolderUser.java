package com.example.helpapp.ViewHolders;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.R;

public class HolderUser extends RecyclerView.ViewHolder{

    public ImageView image;
    public EditText status;
    public TextView name;
    public LinearLayout linearLayout;

    public HolderUser(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.r_img_user);
        name = itemView.findViewById(R.id.r_name_user);
        status = itemView.findViewById(R.id.r_status_user);
        linearLayout = itemView.findViewById(R.id.r_item_user);
    }
}
