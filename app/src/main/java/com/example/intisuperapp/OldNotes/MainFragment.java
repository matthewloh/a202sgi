package com.example.intisuperapp.OldNotes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentMainBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    public static final int ADD_NOTE_REQUEST = 1;

    private FragmentMainBinding binding;

    private NoteViewModel noteViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // insert code here
        final NoteAdapter adapter = new NoteAdapter();
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setHasFixedSize(true);

        // If we were using Fragments instead of Activities, we would use the following line:
        // Still, we would need to use the ViewModelProvider constructor that takes a ViewModelProvider.Factory
        // The object returned by the ViewModelProvider.Factory would be the same for both the Activity and the Fragment
        // noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        // getActivity() could also be used instead of "this" to
        // the ViewModel will be scoped to the lifecycle of the Activity instead of the Fragment
        // to allow the ViewModel to be shared between the Activity and the Fragment
        noteViewModel = new ViewModelProvider(getActivity()).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        binding.buttonAddNote.setOnClickListener(new View.OnClickListener() {
            // TODO: Replace this activity based code with a fragment based code
            @Override
            public void onClick(View v) {
                int REQUEST_CODE = 1;
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_mainFragment_to_addNoteFragment);

//                Intent intent = new Intent(getActivity(), AddEditNoteActivity.class);
//                startActivityForResult(intent, ADD_NOTE_REQUEST);
//                someActivityResultLauncher.launch(intent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Note deleted", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getViewLifecycleOwner(), "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.recyclerView);

//        adapter.setOnItemClickListener(note -> {
//            Intent intent = new Intent(getActivity(), AddEditNoteActivity.class);
//            intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
//            intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
//            intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
//            intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
            //            startActivityForResult(intent, EDIT_NOTE_REQUEST);
//            editActivityResultLauncher.launch(intent);
//        });
    }

//    ActivityResultLauncher<Intent> editActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//        if (result.getResultCode() == RESULT_OK) {
//            Intent data = result.getData();
//            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
//            if (id == -1) {
//                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
//            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
//            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
//
//            Note note = new Note(title, description, priority);
//            note.setId(id);
//            noteViewModel.update(note);
//            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Note not updated", Toast.LENGTH_SHORT).show();
//        }
//    });
//    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//        if (result.getResultCode() == RESULT_OK) {
//            Intent data = result.getData();
//            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
//            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
//            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
//
//            Note note = new Note(title, description, priority);
//            noteViewModel.insert(note);
//
//            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
//        }
//    });

}