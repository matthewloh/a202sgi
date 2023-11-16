package com.example.intisuperapp.LostAndFound;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Toast;


import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentLostAndFoundBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;
import java.util.ArrayList;

public class LostAndFoundFragment extends Fragment {

    private FragmentLostAndFoundBinding binding;
    private RecyclerView recyclerView;
    List<LostAndFoundItems> lostAndFoundItemsList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLostAndFoundBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        recyclerView = rootView.findViewById(R.id.itemlist);

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 1);
        recyclerView.setLayoutManager(layoutManager);

        lostAndFoundItemsList = new ArrayList<>();

        LostAndFoundAdapter adapter = new LostAndFoundAdapter(requireContext(), lostAndFoundItemsList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("LostAndFoundItems");

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lostAndFoundItemsList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    LostAndFoundItems lostAndFoundItems = itemSnapshot.getValue(LostAndFoundItems.class);
                    lostAndFoundItemsList.add(lostAndFoundItems);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Error handling
                Log.e("LostAndFoundFragment", "Error fetching data: " + error.getMessage());
                Toast.makeText(requireContext(), "Error fetching data. Please try again.", Toast.LENGTH_SHORT).show();
            }

        });


        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.itemfoundbtn.setOnClickListener(
                v -> NavHostFragment.findNavController(LostAndFoundFragment.this).navigate(R.id.action_lostAndFoundFragment_to_lostAndFoundReportFoundFragment)
        );
        binding.itemlostbtn.setOnClickListener(
                v -> NavHostFragment.findNavController(LostAndFoundFragment.this).navigate(R.id.action_lostAndFoundFragment_to_lostAndFoundReportLostFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}