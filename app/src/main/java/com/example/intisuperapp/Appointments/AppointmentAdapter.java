package com.example.intisuperapp.Appointments;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.intisuperapp.DBUtils.TimeConstants;
import com.example.intisuperapp.databinding.AppointmentsItemBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder> {
    private OnItemClickListener mListener;

    private OnItemLongClickListener mLongListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongListener = listener;
    }

    // DiffUtil
    public void updateAppointmentList(List<Appointment> newAppointmentList) {
        AppointmentDiffCallback diffCallback = new AppointmentDiffCallback(mAppointmentList, newAppointmentList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        mAppointmentList.clear();
        mAppointmentList.addAll(newAppointmentList);
        diffResult.dispatchUpdatesTo(this);
    }

    // 2023-11-16 15:16:48
    private SimpleDateFormat originalDateFormat = new SimpleDateFormat(TimeConstants.DATE_FORMAT, Locale.getDefault());
    private SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private SimpleDateFormat targetTimeFormat = new SimpleDateFormat("HH:mm");
    // Save a reference to the List of Appointments
    private List<Appointment> mAppointmentList;

    public interface OnItemClickListener {
        void onItemClick(Appointment appointment);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Appointment appointment);
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
        holder.binding.appointmentStartDate.setText(currentAppointment.getStartDate().toString());
        holder.binding.appointmentStartTimeEndTime.setText(currentAppointment.getStartDate().toString() + " - " + currentAppointment.getEndDate().toString());

        // Load the image into the ImageView using Glide
        Glide.with(holder.binding.getRoot()).load(currentAppointment.getImageUrl()).into(holder.binding.appointmentImage);

        // Format the date and time
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
            if (formattedStartDate.equals(formattedEndDate)) {
                holder.binding.appointmentStartDate.setText(formattedStartDate);
            } else {
                holder.binding.appointmentStartDate.setText(formattedStartDate + " - " + formattedEndDate);
            }
            holder.binding.appointmentStartTimeEndTime.setText(formattedStartTime + " - " + formattedEndTime);
        }
        holder.binding.appointmentLocation.setText(currentAppointment.getLocation());
        holder.binding.appointmentNotes.setText(currentAppointment.getNotes());
        holder.binding.appointmentStatus.setText(currentAppointment.getApptStatus());
        holder.binding.appointmentCardView.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onItemClick(currentAppointment);
            }
        });
        holder.binding.appointmentImage.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onItemClick(currentAppointment);
            }
        });
        holder.binding.appointmentImage.setOnLongClickListener(view -> {
            if (mLongListener != null) {
                mLongListener.onItemLongClick(currentAppointment);
            }
            return true;
        });
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
            return oldAppointment.getTitle().equals(newAppointment.getTitle()) &&
                    oldAppointment.getDescription().equals(newAppointment.getDescription()) &&
                    oldAppointment.getStartDate().equals(newAppointment.getStartDate()) &&
                    oldAppointment.getEndDate().equals(newAppointment.getEndDate()) &&
                    oldAppointment.getLocation().equals(newAppointment.getLocation()) &&
                    oldAppointment.getNotes().equals(newAppointment.getNotes());
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            // Implement method if you're going to use ItemAnimator
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }
}
