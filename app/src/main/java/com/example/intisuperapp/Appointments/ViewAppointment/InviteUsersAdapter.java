package com.example.intisuperapp.Appointments.ViewAppointment;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intisuperapp.Appointments.Appointment;
import com.example.intisuperapp.Appointments.InviteAppointment.AppointmentInvitation;
import com.example.intisuperapp.Appointments.InviteAppointment.AppointmentInvitationViewModel;
import com.example.intisuperapp.LoginAndRegistration.User;
import com.example.intisuperapp.databinding.InviteeItemBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InviteUsersAdapter extends RecyclerView.Adapter<InviteUsersAdapter.InviteUserHolder> {

    private AppointmentInvitationViewModel appointmentInvitationViewModel;
    private List<User> mUserList;
    private Appointment appointment;
    private User appointmentCreator;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public InviteUsersAdapter(List<User> userList, AppointmentInvitationViewModel appointmentInvitationViewModel, Appointment appointment, User appointmentCreator) {
        mUserList = userList;
        this.appointmentInvitationViewModel = appointmentInvitationViewModel;
        this.appointment = appointment;
        this.appointmentCreator = appointmentCreator;
    }

    @NonNull
    @Override
    public InviteUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InviteeItemBinding binding = InviteeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new InviteUserHolder(binding);
    }

    public static class InviteUserHolder extends RecyclerView.ViewHolder {
        private InviteeItemBinding binding;

        public InviteUserHolder(InviteeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull InviteUserHolder holder, int position) {
        User currentUser = mUserList.get(position);
        holder.binding.inviteeName.setText(currentUser.getFullname());
        LiveData<AppointmentInvitation> invitationLiveData = appointmentInvitationViewModel.getAppointmentInvitationByAppointmentIdAndUserId(appointment.getId(), currentUser.getId());
        invitationLiveData.observe((LifecycleOwner) holder.itemView.getContext(), appointmentInvitation -> {
            if (appointmentInvitation != null) {
                holder.binding.updateInvitee.setText("Undo");
                holder.binding.updateInvitee.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Undo Invitation");
                    builder.setMessage("Are you sure you want to undo this invitation?");
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        appointmentInvitationViewModel.deleteByAppointmentIdAndUserId(appointment.getId(), currentUser.getId());
                        Toast.makeText(v.getContext(), "Invitation Undone", Toast.LENGTH_SHORT).show();
                    });
                    builder.setNegativeButton("No", (dialog, which) -> {
                    });
                    builder.create().show();
                });
                holder.binding.inviteeStatusUpdatedAt.setText("Invitation Sent: " + appointmentInvitation.getInviteStatus());
            } else {
                holder.binding.updateInvitee.setText("Invite");
                holder.binding.inviteeStatusUpdatedAt.setText("Invitation Not Sent");
                holder.binding.updateInvitee.setOnClickListener(v -> {
                    Date updateAt = new Date();
                    String updateAtString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(updateAt);
                    Date updateAtDate = new Date();
                    try {
                        updateAtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(updateAtString);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    AppointmentInvitation appInv = new AppointmentInvitation(appointment, appointmentCreator, "pending", updateAtDate, currentUser.getId());
                    appointmentInvitationViewModel.insert(appInv);
                    Toast.makeText(v.getContext(), "Invitation Sent", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
