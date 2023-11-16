package com.example.intisuperapp.Venues;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.VenuesListItemBinding;

import java.util.List;

public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.VenuesHolder> {
    private OnItemClickListener mListener;

    private OnLongItemClickListener mLongListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener listener) {
        mLongListener = listener;
    }

    private List<Venues> mVenuesList;

    public void updateVenuesList(List<Venues> newVenuesList) {
        VenuesDiffCallback diffCallback = new VenuesDiffCallback(mVenuesList, newVenuesList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        mVenuesList.clear();
        mVenuesList.addAll(newVenuesList);
        diffResult.dispatchUpdatesTo(this);
    }
    public interface OnItemClickListener {
        void onItemClick(Venues venues);
    }

    public interface OnLongItemClickListener {
        void onLongItemClick(Venues venues);
    }

    @NonNull
    @Override
    public VenuesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VenuesListItemBinding binding = VenuesListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VenuesHolder(binding);
    }

    public static class VenuesHolder extends RecyclerView.ViewHolder {
        private final VenuesListItemBinding binding;

        public VenuesHolder(VenuesListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public VenuesAdapter(List<Venues> venuesList) {
        mVenuesList = venuesList;
    }

    @Override
    public void onBindViewHolder(@NonNull VenuesAdapter.VenuesHolder holder, int position) {
        Venues currentVenues = mVenuesList.get(position);
        holder.binding.title.setText(currentVenues.getVenueName());

        //load image
        Glide.with(holder.binding.getRoot())
                .load(currentVenues.getVenueImageURL())
//                .error(R.drawable.no_image)
                .into(holder.binding.venueImage);


        holder.binding.venueImage.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(currentVenues);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVenuesList.size();
    }

    public class VenuesDiffCallback extends DiffUtil.Callback {

        private final List<Venues> mOldVenuesList;
        private final List<Venues> mNewVenuesList;

        public VenuesDiffCallback(List<Venues> oldVenuesList, List<Venues> newVenuesList) {
            this.mOldVenuesList = oldVenuesList;
            this.mNewVenuesList = newVenuesList;
        }

        @Override
        public int getOldListSize() {
            return mOldVenuesList.size();
        }

        @Override
        public int getNewListSize() {
            return mNewVenuesList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return mOldVenuesList.get(oldItemPosition).getVenueId() == mNewVenuesList.get(newItemPosition).getVenueId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Venues oldVenues = mOldVenuesList.get(oldItemPosition);
            Venues newVenues = mNewVenuesList.get(newItemPosition);
            return mOldVenuesList.get(oldItemPosition).equals(mNewVenuesList.get(newItemPosition));

        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }
}
