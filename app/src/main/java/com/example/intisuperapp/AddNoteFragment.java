package com.example.intisuperapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.intisuperapp.databinding.FragmentAddNoteBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNoteFragment extends Fragment {

    private FragmentAddNoteBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public static final String EXTRA_ID = "com.example.architecture.example.EXTRA_ID";
    private static final String EXTRA_TITLE = "com.example.intisuperapp.EXTRA_TITLE";
    private static final String EXTRA_DESCRIPTION = "com.example.intisuperapp.EXTRA_DESCRIPTION";
    private static final String EXTRA_PRIORITY = "com.example.intisuperapp.EXTRA_PRIORITY";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private NoteViewModel noteViewModel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddNoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters  .
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNoteFragment newInstance(String param1, String param2) {
        AddNoteFragment fragment = new AddNoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.add_note_menu, menu);

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.save_note) {
                    saveNote();
                    return true;
                } else {
                    return false;
                }
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
        noteViewModel = new ViewModelProvider(getActivity()).get(NoteViewModel.class);
        binding.numberPickerPriority.setMinValue(1);
        binding.numberPickerPriority.setMaxValue(10);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Setting Home As Up Indicator in Fragment
        ((MainActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        // The intent checking here as fragment navigation is different from activity navigation
        if (getActivity().getIntent().hasExtra(EXTRA_ID)) {
            getActivity().setTitle("Edit Note");
            binding.editTextTitle.setText(getActivity().getIntent().getStringExtra(EXTRA_TITLE));
            binding.editTextDescription.setText(getActivity().getIntent().getStringExtra(EXTRA_DESCRIPTION));
            binding.numberPickerPriority.setValue(getActivity().getIntent().getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            getActivity().setTitle("Add Note");
        }

        getActivity().setTitle("Add Note");
    }

    private void saveNote() {
        String title = binding.editTextTitle.getText().toString();
        String description = binding.editTextDescription.getText().toString();
        int priority = binding.numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        NavHostFragment.findNavController(AddNoteFragment.this)
                .navigate(R.id.action_addNoteFragment_to_mainFragment);

        // Directly save the note to the database

        Note note = new Note(title, description, priority);
        noteViewModel.insert(note);

        // Navigate back to the main fragment
        // We need another intent to pass the data back to the MainActivity
        // Which receives an intent in the onActivityResult() method
        // TODO: Probably change this to a bundle
        //        Intent data = new Intent();
        //        data.putExtra(EXTRA_TITLE, title);
        //        data.putExtra(EXTRA_DESCRIPTION, description);
        //        data.putExtra(EXTRA_PRIORITY, priority);
        // Bundle Implementation
//        Bundle data = new Bundle();
//        data.putString(EXTRA_TITLE, title);
//        data.putString(EXTRA_DESCRIPTION, description);
//        data.putInt(EXTRA_PRIORITY, priority);


//        NavHostFragment.findNavController(AddNoteFragment.this)
//                .navigate(R.id.action_addNoteFragment_to_mainFragment);

//        int id = getIntent().getIntExtra(EXTRA_ID, -1);
//        // If the intent has an extra with the key EXTRA_ID, then we know that we are editing an existing note
//        // and we can set the title and description accordingly
//        if (id != -1) {
//            data.putExtra(EXTRA_ID, id);
//        }
//        // A result code of RESULT_OK indicates that the Activity succeeded, which in the onActivityResult() method override
//        // we can use to check if the Activity succeeded or not, with the resultCode parameter corresponding to a request code
//        setResult(RESULT_OK, data);
//        finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Called to inflate the menu resource

    // For our one menu item, we can just check the ID of the item and call the saveNote() method if it matches
}