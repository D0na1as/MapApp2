package com.example.helpapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpapp.Data.Company;
import com.example.helpapp.InfoFragment;
import com.example.helpapp.MainActivity;
import com.example.helpapp.R;
import com.example.helpapp.ViewHolders.HolderDetailed;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<HolderDetailed> implements Filterable {

    private List<Company> recipients;
    private List<Company> recipientsName;
    private List<Company> recipientsCity;
    private Context context;

    public RecyclerAdapter(Context context, List<Company> recipients) {
        this.recipients = recipients;
        recipientsName = new ArrayList<>(recipients);
        recipientsCity = new ArrayList<>(recipients);
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

        Company sampleRecipient = recipients.get(position);
        holder.name.setText(sampleRecipient.getCompany_name());
        holder.description.setText(sampleRecipient.getCity());
        holder.phone.setText("Tel.: " + sampleRecipient.getPhone());
        holder.image.setImageResource(MainActivity.getIcon(sampleRecipient.getPriority()));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.viewedCompanyId = sampleRecipient.getId() - 1;
                InfoFragment.viewPager.setCurrentItem(2);
            }
        });
    }

    @Override
    public int getItemCount() {

        return recipients.size();
    }

    @Override
    public Filter getFilter() {

        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Company> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(recipientsName);
            } else {
                String filteredPattern = constraint.toString().toLowerCase().trim();

                for (Company item : recipientsName) {
                    if (item.getCompany_name().toLowerCase().contains(filteredPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            recipients.clear();
            recipientsName.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
