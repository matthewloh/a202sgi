package com.example.intisuperapp.Appointments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intisuperapp.OldNotes.NoteAdapter;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.AppointmentsItemBinding;
import com.example.intisuperapp.databinding.FragmentAppointmentsBinding;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder> {
    private NoteAdapter.OnItemClickListener mListener;

    // Save a reference to the List of Appointments
    private List<Appointment> mAppointmentList;

    public interface OnItemClickListener {
        void onItemClick(Appointment appointment);
    }

    @NonNull
    @Override
    public AppointmentAdapter.AppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        holder.binding.appointmentDate.setText(currentAppointment.getStartDate().toString());
        holder.binding.appointmentStartTimeEndTime.setText(currentAppointment.getStartDate().toString() + " - " + currentAppointment.getEndDate().toString());
        holder.binding.appointmentImage.setImageResource(R.drawable.ic_launcher_foreground);
        holder.binding.appointmentLocation.setText(currentAppointment.getLocation());
        holder.binding.appointmentNotes.setText(currentAppointment.getNotes());
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
