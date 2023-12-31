package com.example.intisuperapp.LostAndFound;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.intisuperapp.R;

import java.util.ArrayList;
import java.util.List;

public class LostAndFoundAdapter extends RecyclerView.Adapter<LAFViewHolder>{

    private Context context;
    private List<LostAndFoundItems> lostAndFoundItemsList;
    private List<LostAndFoundItems> originalList;

    public LostAndFoundAdapter(Context context, List<LostAndFoundItems> lostAndFoundItemsList) {
        this.context = context;
        this.lostAndFoundItemsList = lostAndFoundItemsList;
        this.originalList = new ArrayList<>(lostAndFoundItemsList);
    }

    @NonNull
    @Override
    public LAFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lafitems, parent, false);
        return new LAFViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LAFViewHolder holder, int position) {
        Glide.with(context)
                .load(lostAndFoundItemsList.get(position).getItemImageURL())
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.ic_launcher)
                .into(holder.cardimage);

        holder.itemname1.setText(lostAndFoundItemsList.get(position).getItemName());
        holder.lastlocation1.setText(lostAndFoundItemsList.get(position).getLastLocation());
        holder.itemstatus.setText(lostAndFoundItemsList.get(position).getItemStatus());

        holder.itemcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment);

                // Create a bundle to pass data to the ItemDetailFragment
                Bundle bundle = new Bundle();
                bundle.putString("ItemName", lostAndFoundItemsList.get(holder.getAdapterPosition()).getItemName());
                bundle.putString("Image", lostAndFoundItemsList.get(holder.getAdapterPosition()).getItemImageURL());
                bundle.putString("ContactInfo", lostAndFoundItemsList.get(holder.getAdapterPosition()).getContactInfo());
                bundle.putString("LastLocation", lostAndFoundItemsList.get(holder.getAdapterPosition()).getLastLocation());
                bundle.putString("ItemDescription", lostAndFoundItemsList.get(holder.getAdapterPosition()).getItemDescription());

                // Navigate to the ItemDetailFragment with the bundle
                navController.navigate(R.id.action_lostAndFoundFragment_to_itemDetailFragment, bundle);
            }
        });


    }

    @Override
    public int getItemCount() {
        return lostAndFoundItemsList.size();
    }

    public void filterList(List<LostAndFoundItems> filteredList) {
        lostAndFoundItemsList.clear();
        lostAndFoundItemsList.addAll(filteredList);
        notifyDataSetChanged(); // Notify the adapter that the data set has changed
    }

    public void resetList() {
        lostAndFoundItemsList.clear();
        lostAndFoundItemsList.addAll(originalList);
        notifyDataSetChanged();
    }




}

class LAFViewHolder extends RecyclerView.ViewHolder {

    CardView itemcard;
    TextView itemname1, lastlocation1, itemstatus;
    ImageView cardimage;

    public LAFViewHolder(@NonNull View itemView) {
        super(itemView);

        itemcard = itemView.findViewById(R.id.itemcard);
        itemname1 = itemView.findViewById(R.id.itemname1);
        lastlocation1 = itemView.findViewById(R.id.lastlocation1);
        itemstatus = itemView.findViewById(R.id.itemstatus);
        cardimage = itemView.findViewById(R.id.cardimage);
    }

}