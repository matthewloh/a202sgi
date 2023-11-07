package com.example.intisuperapp.Bookings;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intisuperapp.OldNotes.NoteAdapter;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.BookingpageItemBinding;

import java.text.SimpleDateFormat;
import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.BookingsViewHolder>{

    private NoteAdapter.OnItemClickListener mListener;

    // Save a reference to the List of Bookings
    private List<Bookings> mBookingsList;

    // Create an interface for the OnItemClickListener
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Bookings bookings);
    }

    @NonNull
    @Override
    public BookingsAdapter.BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookingpageItemBinding binding = BookingpageItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BookingsViewHolder(binding);
    }

    public static class BookingsViewHolder extends RecyclerView.ViewHolder {
        private final BookingpageItemBinding binding;

        public BookingsViewHolder(BookingpageItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;


        }
    }

    public BookingsAdapter(List<Bookings> bookingsList, OnItemClickListener listener) {
        mBookingsList = bookingsList;
        mOnItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingsAdapter.BookingsViewHolder holder, int position) {
        Bookings currentBookings = mBookingsList.get(position);
        holder.binding.bookingTitle.setText("Booking "+currentBookings.getId());
        holder.binding.bookingVenue.setText("Venue: "+currentBookings.getVenue());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(currentBookings.getDate());

        holder.binding.bookingDate.setText("Date: "+strDate);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm a ");
        String strStartTime = timeFormatter.format(currentBookings.getStartTime());
        String strEndTime = timeFormatter.format(currentBookings.getEndTime());

        holder.binding.bookingTime.setText("Time: "+strStartTime + " - " + strEndTime);

        holder.binding.currentBookingRow1.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(mBookingsList.get(position));
            }
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
