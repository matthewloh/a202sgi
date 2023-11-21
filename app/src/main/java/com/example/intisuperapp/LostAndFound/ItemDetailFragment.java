package com.example.intisuperapp.LostAndFound;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.intisuperapp.R;


public class ItemDetailFragment extends Fragment {

    TextView detailitemname, detailcontactinfo, detaillastlocation, detailitemdesc;
    ImageView detailitemimage;


    public ItemDetailFragment() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        detailitemname = rootView.findViewById(R.id.detailitemname);
        detailitemimage = rootView.findViewById(R.id.detailitemimg);
        detailcontactinfo = rootView.findViewById(R.id.detailcontactinfo);
        detaillastlocation = rootView.findViewById(R.id.detaillastlocation);
        detailitemdesc = rootView.findViewById(R.id.detailitemdesc);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String itemName = bundle.getString("ItemName");
            String itemImage = bundle.getString("Image");
            String contactInfo = bundle.getString("ContactInfo");
            String lastLocation = bundle.getString("LastLocation");
            String itemDescription = bundle.getString("ItemDescription");

            detailitemname.setText(itemName);
            detailcontactinfo.setText(contactInfo);
            detaillastlocation.setText(lastLocation);
            detailitemdesc.setText(itemDescription);

            Glide.with(requireContext())
                    .load(itemImage)
                    .placeholder(R.drawable.baseline_image_24)
                    .error(R.drawable.ic_launcher)
                    .into(detailitemimage);
        }
        return rootView;
    }
}