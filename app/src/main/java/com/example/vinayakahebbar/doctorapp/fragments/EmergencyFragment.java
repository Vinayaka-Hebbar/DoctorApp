package com.example.vinayakahebbar.doctorapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.adapter.EmergencyContactAdapter;
import com.example.vinayakahebbar.doctorapp.model.EmergencyContact;
import com.example.vinayakahebbar.doctorapp.storage.EmergencyStorage;

import java.util.List;

public class EmergencyFragment extends Fragment {
    private List<EmergencyContact> emergencyContacts;
    private View view;
    private ListView listView;
    private EmergencyContactAdapter adapter;
    public EmergencyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_emergency, container, false);
        listView = (ListView)view.findViewById(R.id.lv_emergency);
        emergencyContacts = new EmergencyStorage().getContactList();
        adapter = new EmergencyContactAdapter(view.getContext(),R.layout.emergency_contact_item,emergencyContacts);
        listView.setAdapter(adapter);
        return view;
    }

}
