package com.example.helpapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.Data.Recipient;
import com.example.helpapp.InfoFragment;
import com.example.helpapp.R;
import com.example.helpapp.ViewHolders.HolderDetailed;
import com.example.helpapp.ViewHolders.HolderIndividual;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<HolderDetailed> {

    private List<Recipient> recipients;
    private Context context;

    public RecyclerAdapter(Context context, List<Recipient> recipients) {
        this.recipients = recipients;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderDetailed onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_detailed, parent, false);
        return new HolderDetailed(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderDetailed holder, int position) {

        Recipient sampleRecipient = recipients.get(position);
        holder.name.setText(sampleRecipient.recipientName);
        holder.description.setText(sampleRecipient.recipientDescription);
        holder.image.setImageResource(sampleRecipient.recipientImage);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoFragment.imageIndividual = sampleRecipient.recipientImage;
                Toast.makeText(context, InfoFragment.imageIndividual, Toast.LENGTH_SHORT).show();
                InfoFragment.viewPager.setCurrentItem(2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipients.size();
    }
}
