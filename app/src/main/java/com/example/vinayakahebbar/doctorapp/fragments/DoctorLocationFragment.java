package com.example.vinayakahebbar.doctorapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.activities.MainActivity;
import com.example.vinayakahebbar.doctorapp.adapter.DoctorsLocationAdapter;
import com.example.vinayakahebbar.doctorapp.interfaces.OnListLoaded;
import com.example.vinayakahebbar.doctorapp.interfaces.OnLoaded;
import com.example.vinayakahebbar.doctorapp.model.DoctorLocation;
import com.example.vinayakahebbar.doctorapp.model.ModelView;
import com.example.vinayakahebbar.doctorapp.utils.FragmentParam;
import com.example.vinayakahebbar.doctorapp.utils.HttpUtils;
import com.example.vinayakahebbar.doctorapp.utils.JsonIO;
import com.example.vinayakahebbar.doctorapp.utils.type.FragmentType;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorLocationFragment extends Fragment implements OnLoaded, OnListLoaded, AdapterView.OnItemClickListener {

    private View view;
    private ListView listView;
    private DoctorsLocationAdapter adapter;
    private List<DoctorLocation> locations;
    public DoctorLocationFragment() {
        // Required empty public constructor
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.setOnLoaded(this);
        httpUtils.getDoctorLocation();
        locations = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_doctor_location, container, false);
        TextView textViewLocation = (TextView)view.findViewById(R.id.tv_doc_loc_name);
        SpannableString content = new SpannableString("Locations");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textViewLocation.setText(content);
        listView = (ListView)view.findViewById(R.id.lv_doctor_loc);
        listView.setOnItemClickListener(this);
        adapter = new DoctorsLocationAdapter(locations,view.getContext(),R.layout.doctor_location_item);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void Update(String text) {
        JsonIO jsonIO = new JsonIO(text);
        jsonIO.setOnLoaded(this);
        jsonIO.getDoctorLocation();
    }

    @Override
    public void UpdateList(List<ModelView> lists) {
        for (ModelView view :
                lists) {
            locations.add((DoctorLocation) view);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DoctorLocation location= (DoctorLocation)parent.getItemAtPosition(position);
        new FragmentParam(new String[]{location.getName(),location.getPath()});
        MainActivity.fragmentListener.loadFragment(FragmentType.DOC_LIST);
    }
}
