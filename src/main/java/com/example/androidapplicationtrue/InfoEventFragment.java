package com.example.androidapplicationtrue;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class InfoEventFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_event, container, false);

        // Access the TextView
        TextView infoTextView = view.findViewById(R.id.info_textview);

        // Set the text dynamically
        infoTextView.setText("This is the information you want to display.");

        return view;
    }
}
