package com.example.intisuperapp.Appointments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intisuperapp.OldNotes.NoteAdapter;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.AppointmentsItemBinding;
import com.example.intisuperapp.databinding.FragmentAppointmentsBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder> {
    private OnItemClickListener mListener;

    private SimpleDateFormat originalDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    private SimpleDateFormat targetTimeFormat = new SimpleDateFormat("HH:mma", Locale.getDefault());

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    // Save a reference to the List of Appointments
    private List<Appointment> mAppointmentList;

    public interface OnItemClickListener {
        void onItemClick(Appointment appointment);
    }

    @NonNull
    @Override
    public AppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AppointmentsItemBinding binding = AppointmentsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AppointmentHolder(binding);
    }

    public static class AppointmentHolder extends RecyclerView.ViewHolder {
        private final AppointmentsItemBinding binding;

        public AppointmentHolder(AppointmentsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public AppointmentAdapter(List<Appointment> appointmentList) {
        mAppointmentList = appointmentList;
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.AppointmentHolder holder, int position) {
        Appointment currentAppointment = mAppointmentList.get(position);
        holder.binding.appointmentTitle.setText(currentAppointment.getTitle());
        holder.binding.appointmentDesc.setText(currentAppointment.getDescription());
        Date tempStartDate = currentAppointment.getStartDate();
        Date tempEndDate = currentAppointment.getEndDate();
        Date tempStartTime = currentAppointment.getStartDate();
        Date tempEndTime = currentAppointment.getEndDate();
        try {
            tempStartDate = originalDateFormat.parse(currentAppointment.getStartDate().toString());
            tempEndDate = originalDateFormat.parse(currentAppointment.getEndDate().toString());
            tempStartTime = originalDateFormat.parse(currentAppointment.getStartDate().toString());
            tempEndTime = originalDateFormat.parse(currentAppointment.getEndDate().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tempStartDate != null && tempEndDate != null && tempStartTime != null && tempEndTime != null) {
            String formattedStartDate = targetDateFormat.format(tempStartDate);
            String formattedEndDate = targetDateFormat.format(tempEndDate);
            String formattedStartTime = targetTimeFormat.format(tempStartTime);
            String formattedEndTime = targetTimeFormat.format(tempEndTime);
            holder.binding.appointmentDate.setText(formattedStartDate + " - " + formattedEndDate);
            holder.binding.appointmentStartTimeEndTime.setText(formattedStartTime + " - " + formattedEndTime);
        }
        holder.binding.appointmentImage.setImageResource(R.drawable.intilogo);
        holder.binding.appointmentLocation.setText(currentAppointment.getLocation());
        holder.binding.appointmentNotes.setText(currentAppointment.getNotes());

        holder.binding.appointmentImage.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onItemClick(currentAppointment);
            }
        });
        holder.binding.appointmentImage.setOnLongClickListener(
                view -> {
                    Toast.makeText(view.getContext(), "Long Clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
        );
    }

    @Override
    public int getItemCount() {
        return mAppointmentList.size();
    }

    public class AppointmentDiffCallback extends DiffUtil.Callback {
        private final List<Appointment> mOldAppointmentList;
        private final List<Appointment> mNewAppointmentList;

        public AppointmentDiffCallback(List<Appointment> oldAppointmentList, List<Appointment> newAppointmentList) {
            this.mOldAppointmentList = oldAppointmentList;
            this.mNewAppointmentList = newAppointmentList;
        }

        @Override
        public int getOldListSize() {
            return mOldAppointmentList.size();
        }

        @Override
        public int getNewListSize() {
            return mNewAppointmentList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return mOldAppointmentList.get(oldItemPosition).getId() == mNewAppointmentList.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Appointment oldAppointment = mOldAppointmentList.get(oldItemPosition);
            Appointment newAppointment = mNewAppointmentList.get(newItemPosition);
            return oldAppointment.getTitle().equals(newAppointment.getTitle()) && oldAppointment.getDescription().equals(newAppointment.getDescription());
        }

        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            // Implement method if you're going to use ItemAnimator
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }
}
