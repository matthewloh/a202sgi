package com.example.intisuperapp.LostAndFound;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentLostAndFoundBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class LostAndFoundFragment extends Fragment {

    private FragmentLostAndFoundBinding binding;
    private Button filterbtn;
    private RecyclerView itemlist;
    private List<LostAndFoundItems> lostAndFoundItemsList;
    private CollectionReference lostAndFoundCollection;
    private ListenerRegistration eventListener;
    private SearchView itemsearch;
    private LostAndFoundAdapter adapter;
    private List<LostAndFoundItems> originalList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLostAndFoundBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        filterbtn = rootView.findViewById(R.id.itemfilterbtn);

        itemlist = rootView.findViewById(R.id.itemlist);

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 1);
        itemlist.setLayoutManager(layoutManager);

        lostAndFoundItemsList = new ArrayList<>();


        adapter = new LostAndFoundAdapter(requireContext(), lostAndFoundItemsList);
        itemlist.setAdapter(adapter);

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
                originalList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    LostAndFoundItems lostAndFoundItems = doc.toObject(LostAndFoundItems.class);
                    lostAndFoundItemsList.add(lostAndFoundItems);
                    originalList.add(lostAndFoundItems);
                }

                adapter.notifyDataSetChanged();
            }
        });

        itemsearch = rootView.findViewById(R.id.itemsearch);
        itemsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.resetList();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filterList(filter(newText));
                return true;
            }


        });


        return rootView;
    }



    private List<LostAndFoundItems> filter(String query) {
        List<LostAndFoundItems> filteredList = new ArrayList<>();

        for (LostAndFoundItems item : originalList) {
            if (item.getItemName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }

        return filteredList;
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

        filterbtn.setOnClickListener(this::showFilterOptions);

    }

    private void showFilterOptions(View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.filterpopup_laf, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            handleFilterItemClick(item.getItemId());
            return true;
        });

        popupMenu.show();
    }

    private void handleFilterItemClick(int itemId) {
        if (itemId == R.id.menu_lost_items) {
            // Handle lost items filter
            filterItems("Lost");
        } else if (itemId == R.id.menu_found_items) {
            // Handle found items filter
            filterItems("Found");
        }
    }


    private void filterItems(String filterStatus) {
        List<LostAndFoundItems> filteredList = new ArrayList<>();

        for (LostAndFoundItems item : originalList) {
            if (item.getItemStatus().equalsIgnoreCase(filterStatus)) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
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