package com.example.vinayakahebbar.doctorapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.adapter.DoctorListAdapter;
import com.example.vinayakahebbar.doctorapp.interfaces.JsonListener;
import com.example.vinayakahebbar.doctorapp.interfaces.NetworkListener;
import com.example.vinayakahebbar.doctorapp.model.Doctor;
import com.example.vinayakahebbar.doctorapp.model.ModelView;
import com.example.vinayakahebbar.doctorapp.utils.FragmentParam;
import com.example.vinayakahebbar.doctorapp.utils.HttpUtils;
import com.example.vinayakahebbar.doctorapp.utils.JsonIO;
import com.example.vinayakahebbar.doctorapp.utils.helper.SnackBarHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorsListFragment extends Fragment implements NetworkListener,AdapterView.OnItemClickListener, JsonListener {

    private List<Doctor> doctors;
    private View view;
    private ListView listView;
    private DoctorListAdapter adapter;
    private LayoutInflater inflater;


    public DoctorsListFragment() {
        String[] arg = FragmentParam.instance.args;
        HttpUtils httpUtils = new HttpUtils(arg);
        httpUtils.setOnLoaded(this);
        httpUtils.getDoctors();
        doctors = new ArrayList<>();
        // Required empty public constructor
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        view = inflater.inflate(R.layout.fragment_doctors_list, container, false);
        // Inflate the layout for this fragment
        listView = (ListView)view.findViewById(R.id.lv_doctor);
        listView.setOnItemClickListener(this);
        adapter = new DoctorListAdapter(view.getContext(),R.layout.doctor_info_item,doctors);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Doctor doctor = (Doctor) parent.getItemAtPosition(position);
        View popUpView = inflater.inflate(R.layout.popup_doctor,null);
        final PopupWindow popupWindow = new PopupWindow(popUpView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TextView textViewName = (TextView)popUpView.findViewById(R.id.tv_popup_doc_name);
        textViewName.setText(doctor.getName());
        ImageButton imageButtonClose = (ImageButton)popUpView.findViewById(R.id.btn_popup_doc_close);
        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        TextView textViewSpec = (TextView)popUpView.findViewById(R.id.tv_popup_doc_spec);
        textViewSpec.setText(doctor.getSpecialization());
        TextView textViewInfo = (TextView)popUpView.findViewById(R.id.tv_popup_doc_info);
        textViewInfo.setText(doctor.getInformation());
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
    }

    @Override
    public void onNetworkError(String error) {
        new SnackBarHelper(view,error, Snackbar.LENGTH_SHORT).showError();
    }

    @Override
    public void onLoaded(String response) {
        JsonIO jsonIO =  new JsonIO(response);
        jsonIO.setOnLoaded(this);
        jsonIO.getDoctorsInfo();
    }

    @Override
    public void onParsed(List<ModelView> lists) {
        for(ModelView view:lists)
            doctors.add((Doctor)view);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onParseError(String error) {

    }
}
