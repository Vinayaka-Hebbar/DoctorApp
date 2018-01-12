package com.example.vinayakahebbar.doctorapp.fragments;


import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.adapter.EmergencyContactAdapter;
import com.example.vinayakahebbar.doctorapp.model.EmergencyContact;
import com.example.vinayakahebbar.doctorapp.storage.EmergencyStorage;
import com.example.vinayakahebbar.doctorapp.utils.dialogs.NotifyDialog;

import java.util.List;

public class EmergencyFragment extends Fragment implements AdapterView.OnItemClickListener {
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
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final EmergencyContact contact = (EmergencyContact)parent.getItemAtPosition(position);
        NotifyDialog dialog = new NotifyDialog(view.getContext());
        dialog.setClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+ contact.getPhNo()));
                startActivity(callIntent);
            }
        });
        dialog.show("Do you want to call " + contact.getName(),"Call");
    }
}
