package com.example.helpapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.Data.Messages;
import com.example.helpapp.R;
import com.example.helpapp.ViewHolders.HolderFeedback;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<HolderFeedback> {

    private List<Messages> data;
    private Context context;

    public MessageAdapter(Context context, List<Messages> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderFeedback onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_feedback, parent, false);
        return new HolderFeedback(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderFeedback holder, int position) {

        Messages sampleMessage = data.get(position);
        holder.name.setText(sampleMessage.name);
        holder.message.setText(sampleMessage.massage);
        holder.image.setImageResource(sampleMessage.image);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Zinuciu fraamentas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return data.size();
    }
}
