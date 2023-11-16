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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ListenerRegistration;
import java.util.List;
import java.util.ArrayList;

public class LostAndFoundFragment extends Fragment {

    private FragmentLostAndFoundBinding binding;
    private RecyclerView recyclerView;
    List<LostAndFoundItems> lostAndFoundItemsList;
    CollectionReference lostAndFoundCollection;
    ListenerRegistration eventListener;

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        lostAndFoundCollection = db.collection("lostAndFoundItems");

        eventListener = lostAndFoundCollection.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("Firestore", "Error in snapshot listener", error);
                Toast.makeText(requireContext(), "Error fetching data. Please try again.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (value != null) {
                lostAndFoundItemsList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    LostAndFoundItems lostAndFoundItems = doc.toObject(LostAndFoundItems.class);
                    lostAndFoundItemsList.add(lostAndFoundItems);
                }
                adapter.notifyDataSetChanged();
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
        if (eventListener != null) {
            eventListener.remove();
        }
        binding = null;
    }
}