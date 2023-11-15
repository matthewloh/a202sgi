package com.example.intisuperapp.LostAndFound;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.intisuperapp.R;
import com.bumptech.glide.Glide;

public class LostAndFoundAdapter extends RecyclerView.Adapter<LAFViewHolder>{

    private Context context;
    private List<LostAndFoundItems> lostAndFoundItemsList;

    public LostAndFoundAdapter(Context context, List<LostAndFoundItems> lostAndFoundItemsList) {
        this.context = context;
        this.lostAndFoundItemsList = lostAndFoundItemsList;
    }

    @NonNull
    @Override
    public LAFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lafitems, parent, false);
        return new LAFViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LAFViewHolder holder, int position) {
        Glide.with(context).load(lostAndFoundItemsList.get(position).getItemImageURL()).into(holder.cardimage);
        holder.itemname1.setText(lostAndFoundItemsList.get(position).getItemName());
        holder.lastlocation1.setText(lostAndFoundItemsList.get(position).getLastLocation());
        holder.itemstatus.setText(lostAndFoundItemsList.get(position).getItemStatus());

        holder.itemcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemDetailFragment.class);
                intent.putExtra("ItemName", lostAndFoundItemsList.get(holder.getAdapterPosition()).getItemName());
                intent.putExtra("Image", lostAndFoundItemsList.get(holder.getAdapterPosition()).getItemImageURL());
                intent.putExtra("ContactInfo", lostAndFoundItemsList.get(holder.getAdapterPosition()).getContactInfo());
                intent.putExtra("LastLocation", lostAndFoundItemsList.get(holder.getAdapterPosition()).getLastLocation());
                intent.putExtra("ItemDescription", lostAndFoundItemsList.get(holder.getAdapterPosition()).getItemDescription());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lostAndFoundItemsList.size();
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