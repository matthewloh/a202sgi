package com.example.intisuperapp.Events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.intisuperapp.R;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder> {
    private List<String> imageUrls;
    private Context context;

    public ImageSliderAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventimgslider, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);
        int targetWidth = convertDpToPx(context, 390);
        int targetHeight = convertDpToPx(context, 160);

        Glide.with(context)
                .load(imageUrl)
                .override(targetWidth, targetHeight) // Set the size here
                .into(holder.imageView);
    }

    // Helper method to convert dp to pixels
    private int convertDpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.eventimgslider);
        }
    }
}

