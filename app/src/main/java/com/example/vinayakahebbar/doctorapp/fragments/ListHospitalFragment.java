package com.example.vinayakahebbar.doctorapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.adapter.HospitalListAdapter;
import com.example.vinayakahebbar.doctorapp.interfaces.JsonListener;
import com.example.vinayakahebbar.doctorapp.interfaces.ListListener;
import com.example.vinayakahebbar.doctorapp.interfaces.NetworkListener;
import com.example.vinayakahebbar.doctorapp.interfaces.OnListClickListener;
import com.example.vinayakahebbar.doctorapp.model.Hospital;
import com.example.vinayakahebbar.doctorapp.model.ModelView;
import com.example.vinayakahebbar.doctorapp.utils.HttpUtils;
import com.example.vinayakahebbar.doctorapp.utils.JsonIO;
import com.example.vinayakahebbar.doctorapp.utils.helper.SnackBarHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListHospitalFragment extends Fragment implements AdapterView.OnItemClickListener, NetworkListener, JsonListener,ListListener {

    private ListView listView;
    private HospitalListAdapter adapter;
    private View view;
    private List<Hospital> hospitalList;
    private TextView textViewHeader;
    private HttpUtils httpUtils;


    private OnListClickListener listClickListener;
    public ListHospitalFragment() {
        // Required empty public constructor
        hospitalList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_hospital, container, false);
        listView = (ListView)view.findViewById(R.id.lv_hospital);
        textViewHeader = (TextView)view.findViewById(R.id.tv_list_name);
        adapter = new HospitalListAdapter(view.getContext(),R.layout.list_hospital_item,hospitalList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        httpUtils = new HttpUtils(new String[]{"All"});
        httpUtils.setOnLoaded(this);
        httpUtils.getHospitalLocations();
        super.onStart();
    }

    public void setListClickListener(OnListClickListener listClickListener) {
        this.listClickListener = listClickListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listClickListener.OnClick(hospitalList.get(position));
    }



    @Override
    public void onLoaded(String response) {
        JsonIO jsonIO = new JsonIO(response);
        jsonIO.setOnLoaded(this);
        jsonIO.getHospitalDetailsFromJson();
    }

    @Override
    public void onNetworkError(String error) {
        new SnackBarHelper(view,error, Snackbar.LENGTH_SHORT).showError();
    }

    @Override
    public void onParsed(List<ModelView> lists) {
        hospitalList.clear();
        textViewHeader.setText(httpUtils.getParam()[0]);
        for (ModelView view :
                lists) {
            hospitalList.add((Hospital) view);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onParseError(String error) {

    }

    @Override
    public void updateList(List<ModelView> list, String[] params) {

    }
}
