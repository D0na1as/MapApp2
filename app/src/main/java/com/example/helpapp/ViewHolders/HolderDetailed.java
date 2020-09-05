package com.example.helpapp.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.R;

public class HolderDetailed extends RecyclerView.ViewHolder{

    public ImageView image;
    public TextView name;
    public TextView description;
    public TextView phone;
    public LinearLayout linearLayout;

    public HolderDetailed(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.r_image_detailed);
        name = itemView.findViewById(R.id.r_title_detiled);
        description = itemView.findViewById(R.id.r_city_detailed);
        phone = itemView.findViewById(R.id.r_tel_detailed);
        linearLayout = itemView.findViewById(R.id.r_item_detailed);
    }
}
