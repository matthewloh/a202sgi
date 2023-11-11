package com.example.intisuperapp.Appointments.InviteAppointment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.intisuperapp.Appointments.Appointment;
import com.example.intisuperapp.LoginAndRegistration.User;
import com.example.intisuperapp.databinding.AppointmentsInviteItemBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InviteAppointmentAdapter extends RecyclerView.Adapter<InviteAppointmentAdapter.InviteAppointmentHolder> {
    private OnItemClickListener mListener;

    private OnItemLongClickListener mLongListener;

    private AppointmentInvitationViewModel appointmentInvitationViewModel;
    private User appointmentCreator;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongListener = listener;
    }

    private List<AppointmentInvitation> mAppointmentList;

    private SimpleDateFormat originalDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    private SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    private SimpleDateFormat targetTimeFormat = new SimpleDateFormat("HH:mma", Locale.getDefault());


    public interface OnItemClickListener {
        void onItemClick(Appointment appointment);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Appointment appointment);
    }

    @NonNull
    @Override
    public InviteAppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AppointmentsInviteItemBinding binding = AppointmentsInviteItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new InviteAppointmentHolder(binding);
    }

    public static class InviteAppointmentHolder extends RecyclerView.ViewHolder {
        private AppointmentsInviteItemBinding binding;

        public InviteAppointmentHolder(AppointmentsInviteItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public InviteAppointmentAdapter(List<AppointmentInvitation> appointmentList, AppointmentInvitationViewModel appointmentInvitationViewModel) {
        mAppointmentList = appointmentList;
        this.appointmentInvitationViewModel = appointmentInvitationViewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull InviteAppointmentHolder holder, int position) {
        AppointmentInvitation appInv = mAppointmentList.get(position);
        Appointment currentAppointment = mAppointmentList.get(position).getAppointment();
        appointmentCreator = mAppointmentList.get(position).getAuthor();
        holder.binding.appointmentTitle.setText(currentAppointment.getTitle());
        holder.binding.appointmentDesc.setText(currentAppointment.getDescription());
        holder.binding.appointmentStartDate.setText(currentAppointment.getStartDate().toString());
        holder.binding.appointmentStartTimeEndTime.setText(currentAppointment.getStartDate().toString() + " - " + currentAppointment.getEndDate().toString());
        // Image url of currentAppointment is obtained by selecting image_url from photo_table where photo_id = currentAppointment.getPhotoId()
        Glide.with(holder.binding.getRoot()).load(currentAppointment.getImageUrl()).into(holder.binding.appointmentImage);
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
            holder.binding.appointmentStartDate.setText(formattedStartDate + " - " + formattedEndDate);
            holder.binding.appointmentStartTimeEndTime.setText(formattedStartTime + " - " + formattedEndTime);
        }
        holder.binding.appointmentLocation.setText(currentAppointment.getLocation());
        holder.binding.appointmentNotes.setText(currentAppointment.getNotes());
        holder.binding.invitedBy.setText("You were invited by " + appointmentCreator.getFullname());
        if (appInv.getInviteStatus().equals("accepted")) {
            holder.binding.acceptButton.setVisibility(View.GONE);
            holder.binding.declineButton.setVisibility(View.GONE);
            holder.binding.inviteStatus.setText("You have accepted this appointment at " + appInv.getStatusUpdateAt().toString() + "");
        } else if (appInv.getInviteStatus().equals("declined")) {
            holder.binding.acceptButton.setVisibility(View.GONE);
            holder.binding.declineButton.setVisibility(View.GONE);
            holder.binding.inviteStatus.setText("You have declined this appointment " + appInv.getStatusUpdateAt().toString() + "");
        } else {
            holder.binding.acceptButton.setVisibility(View.VISIBLE);
            holder.binding.declineButton.setVisibility(View.VISIBLE);
            holder.binding.inviteStatus.setText("You have not responded to this appointment");
        }
        holder.binding.acceptButton.setOnClickListener(
                v -> {
                    AppointmentInvitation appointmentInvitation = mAppointmentList.get(position);
                    appointmentInvitation.setInviteStatus("accepted");
                    appointmentInvitation.setStatusUpdateAt(new Date());
                    appointmentInvitationViewModel.update(appointmentInvitation);
                }
        );
        holder.binding.declineButton.setOnClickListener(
                v -> {
                    AppointmentInvitation appointmentInvitation = mAppointmentList.get(position);
                    appointmentInvitation.setInviteStatus("declined");
                    appointmentInvitation.setStatusUpdateAt(new Date());
                    appointmentInvitationViewModel.update(appointmentInvitation);
                }
        );
        holder.binding.inviteStatus.setOnClickListener(
                v -> {
                    AppointmentInvitation appointmentInvitation = mAppointmentList.get(position);
                    appointmentInvitation.setInviteStatus("pending");
                    appointmentInvitation.setStatusUpdateAt(new Date());
                    appointmentInvitationViewModel.update(appointmentInvitation);
                }
        );
    }

    @Override
    public int getItemCount() {
        return mAppointmentList.size();
    }
}


