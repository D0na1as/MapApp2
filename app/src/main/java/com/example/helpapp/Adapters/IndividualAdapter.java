package com.example.helpapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.Data.Rcp_data;
import com.example.helpapp.R;
import com.example.helpapp.ViewHolders.HolderIndividual;

import java.util.List;

public class IndividualAdapter extends RecyclerView.Adapter<HolderIndividual> {

    private List<Rcp_data> data;
    private Context context;

    public IndividualAdapter(Context context, List<Rcp_data> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderIndividual onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_individual, parent, false);
        return new HolderIndividual(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderIndividual holder, int position) {

        Rcp_data sampleData = data.get(position);

        holder.image.setImageResource(sampleData.image);
        holder.name.setText(sampleData.name +": " );
        holder.status.setText("" + sampleData.status );
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

