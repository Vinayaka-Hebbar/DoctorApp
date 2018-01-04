package com.example.vinayakahebbar.doctorapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.adaptor.DoctorListAdaptor;
import com.example.vinayakahebbar.doctorapp.interfaces.OnListLoaded;
import com.example.vinayakahebbar.doctorapp.interfaces.OnLoaded;
import com.example.vinayakahebbar.doctorapp.model.Doctor;
import com.example.vinayakahebbar.doctorapp.model.ModelView;
import com.example.vinayakahebbar.doctorapp.utils.HttpUtils;
import com.example.vinayakahebbar.doctorapp.utils.JsonIO;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorsListFragment extends Fragment implements OnLoaded {


    private List<Doctor> doctors;
    private ListView listView;
    private View view;
    private  DoctorListAdaptor adaptor;
    public DoctorsListFragment() {
        doctors = new ArrayList<>();
       HttpUtils httpUtils =  new HttpUtils();
        httpUtils.setOnLoaded(this);
        httpUtils.getDoctors();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doctors_list, container, false);
        // Inflate the layout for this fragment
        listView = (ListView) view.findViewById(R.id.list_doctor);
        adaptor = new DoctorListAdaptor(view.getContext(),R.layout.doctor_list_item,doctors);
        listView.setAdapter(adaptor);
        return view;
    }

    @Override
    public void Update(String text) {
        JsonIO jsonIO =  new JsonIO(text);
        jsonIO.setOnLoaded(new OnListLoaded() {
            @Override
            public void Update(List<ModelView> lists) {
                for(ModelView view:lists)
                    doctors.add((Doctor)view);
                adaptor.notifyDataSetChanged();
            }
        });
        jsonIO.getDoctorsInfo();
    }

    private void loadList() {

    }
}
