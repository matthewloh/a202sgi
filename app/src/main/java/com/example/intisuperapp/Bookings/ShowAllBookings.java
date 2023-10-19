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
            "Booking 1: ","Booking 2: ",
            "Booking 3: ","Booking 4: ",

    };
    String[] venues = {
            "Venue: Discussion Room 2", "Venue: Music Room",
            "Venue: Discussion Room 1", "Venue: Pool Table 1",

    };
    String[] dates = {
            "Date: 12/12/2023", "Date: 13/12/2023",
            "Date: 14/12/2023", "Date: 14/12/2023",
    };
    String[] times = {
            "Time: 12:00PM - 1:00PM", "Time: 10:00AM - 11:30AM",
            "Time: 2:00PM - 3:00PM", "Time: 3:00PM - 4:00PM",
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
