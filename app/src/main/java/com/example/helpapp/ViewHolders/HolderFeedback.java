package com.example.helpapp.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.R;


public class HolderFeedback extends RecyclerView.ViewHolder{

    public ImageView image;
    public TextView name;
    public TextView message;
    public LinearLayout linearLayout;


    public HolderFeedback(@NonNull View itemView) {
        super(itemView);

        this.name = itemView.findViewById(R.id.name_feedback);
        this.message = itemView.findViewById(R.id.message_feedback);
        this.image = itemView.findViewById(R.id.image_feedback);
        this.linearLayout = itemView.findViewById(R.id.r_item_feedback);
    }
}
