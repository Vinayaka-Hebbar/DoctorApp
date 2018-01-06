package com.example.vinayakahebbar.doctorapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.adapter.TabViewAdapter;
import com.example.vinayakahebbar.doctorapp.interfaces.ListListener;
import com.example.vinayakahebbar.doctorapp.interfaces.OnListClickListener;
import com.example.vinayakahebbar.doctorapp.model.ModelView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HospitalFragment extends Fragment {

    private int[] icons;
    private TabLayout tabLayout;
    private View view;
    private ViewPager viewPager;
    private TabViewAdapter adapter;

    public HospitalFragment() {
        // Required empty public constructor
        icons = new int[]{
                R.drawable.ic_action_map,
                R.drawable.ic_action_list
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hospital, container, false);
        tabLayout = (TabLayout)view.findViewById(R.id.tab_hospital);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager_hospital);
        setUpTabs();
        return view;
    }

    private void setUpTabs() {
        adapter = new TabViewAdapter(getFragmentManager());
        final ListHospitalFragment listHospitalFragment = new ListHospitalFragment();
        final MapHospitalFragment mapHospitalFragment = new MapHospitalFragment();
        mapHospitalFragment.setListListener(new ListListener() {
            @Override
            public void updateList(List<ModelView> list,String[] params) {
                listHospitalFragment.updateList(list,params);
            }
        });
        listHospitalFragment.setListClickListener(new OnListClickListener() {
            @Override
            public void OnClick(ModelView view) {
                viewPager.setCurrentItem(0);
                mapHospitalFragment.showPopUp(view);
            }
        });
        adapter.addFragment(mapHospitalFragment,"Map");
        adapter.addFragment(listHospitalFragment,"List");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
