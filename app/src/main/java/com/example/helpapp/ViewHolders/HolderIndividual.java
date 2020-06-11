package com.example.helpapp.ViewHolders;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.R;

public class HolderIndividual extends RecyclerView.ViewHolder{

    public ImageView image;
    public TextView status;
    public LinearLayout linearLayout;

    public HolderIndividual(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.r_image_individual);
        status = itemView.findViewById(R.id.r_status_individual);
        linearLayout = itemView.findViewById(R.id.r_item_individual);
    }
}
