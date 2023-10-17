package com.example.intisuperapp.Bookings;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.intisuperapp.MainActivity;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentRegistrationBinding;
import com.example.intisuperapp.databinding.FragmentShowAllBookingsBinding;

import java.util.ArrayList;
import java.util.HashMap;


public class ShowAllBookings extends Fragment {

    private FragmentShowAllBookingsBinding binding;
    ListView listView;
    String[] maintitle ={
            "Booking 1","Booking 2",
            "Booking 3","Booking 4",
            "Booking 5","Booking 6",
            "Booking 7","Booking 8",
            "Booking 9","Booking 10",
    };
    String[] venues = {
            "Venue 1", "Venue 2",
            "Venue 3", "Venue 4",
            "Venue 5", "Venue 6",
            "Venue 7", "Venue 8",
            "Venue 9", "Venue 10",
    };
    String[] dates = {
            "Date 1", "Date 2",
            "Date 3", "Date 4",
            "Date 5", "Date 6",
            "Date 7", "Date 8",
            "Date 9", "Date 10",
    };
    String[] times = {
            "Time 1", "Time 2",
            "Time 3", "Time 4",
            "Time 5", "Time 6",
            "Time 7", "Time 8",
            "Time 9", "Time 10",
    };




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShowAllBookingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();

        actionBar.setTitle("");

        listView = (ListView) view.findViewById(R.id.allBookingsListView);

        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < maintitle.length; i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maintitle", maintitle[i]);
            hashMap.put("venue", venues[i]);
            hashMap.put("date", dates[i]);
            hashMap.put("time", times[i]);
            list.add(hashMap);
        }

        String from[] = {"maintitle", "venue", "date", "time"};
        int to[] = {R.id.firstRow, R.id.secondRow, R.id.thirdRow, R.id.fourthRow};

        SimpleAdapter customAdapter = new SimpleAdapter(requireContext(), list, R.layout.listview_show_all_bookings, from, to);
        listView.setAdapter(customAdapter);

        binding.doneBtn.setOnClickListener(
                v -> {
                    NavHostFragment.findNavController(ShowAllBookings.this)
                            .navigate(R.id.action_showAllBookings_to_bookingsFragment);
                }
        );


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
