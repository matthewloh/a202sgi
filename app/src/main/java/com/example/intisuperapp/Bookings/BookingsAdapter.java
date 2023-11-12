package com.example.intisuperapp.Bookings;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intisuperapp.databinding.BookingpageItemBinding;

import java.text.SimpleDateFormat;
import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.BookingsViewHolder> {

    private OnItemClickListener mListener;

    private OnItemLongClickListener mLongListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Bookings appointment);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Bookings appointment);
    }

    // Save a reference to the List of Bookings
    private List<Bookings> mBookingsList;


    @NonNull
    @Override
    public BookingsAdapter.BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookingpageItemBinding binding = BookingpageItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BookingsViewHolder(binding);
    }

    public static class BookingsViewHolder extends RecyclerView.ViewHolder {
        private final BookingpageItemBinding binding;

        public BookingsViewHolder(BookingpageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public BookingsAdapter(List<Bookings> bookingsList) {
        mBookingsList = bookingsList;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingsViewHolder holder, int position) {
        Bookings currentBookings = mBookingsList.get(position);
        holder.binding.bookingTitle.setText("Booking " + currentBookings.getId());
        holder.binding.bookingVenue.setText("Venue: " + currentBookings.getVenue());
        holder.binding.bookingDate.setText("Date: " + currentBookings.getDate());
        holder.binding.bookingTime.setText("Time: " + currentBookings.getStartTime() + " - " + currentBookings.getEndTime());

        holder.binding.currentBookingRow1.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(mBookingsList.get(position));
            }
        });
        holder.binding.currentBookingRow1.setOnLongClickListener(v -> {
            if (mLongListener != null) {
                mLongListener.onItemLongClick(mBookingsList.get(position));
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mBookingsList.size();
    }

    public class BookingsDiffCallback extends DiffUtil.Callback {
        private final List<Bookings> mOldBookingsList;
        private final List<Bookings> mNewBookingsList;

        public BookingsDiffCallback(List<Bookings> oldBookingsList, List<Bookings> newBookingsList) {
            this.mOldBookingsList = oldBookingsList;
            this.mNewBookingsList = newBookingsList;
        }

        @Override
        public int getOldListSize() {
            return mOldBookingsList.size();
        }

        @Override
        public int getNewListSize() {
            return mNewBookingsList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return mOldBookingsList.get(oldItemPosition).getId() == mNewBookingsList.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Bookings oldBookings = mOldBookingsList.get(oldItemPosition);
            Bookings newBookings = mNewBookingsList.get(newItemPosition);
            return mOldBookingsList.get(oldItemPosition).equals(mNewBookingsList.get(newItemPosition));
        }

        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }
}
