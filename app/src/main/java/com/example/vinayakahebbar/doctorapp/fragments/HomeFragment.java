package com.example.vinayakahebbar.doctorapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.activities.MainActivity;
import com.example.vinayakahebbar.doctorapp.adapter.GridViewAdapter;
import com.example.vinayakahebbar.doctorapp.adapter.ViewPageAdapter;
import com.example.vinayakahebbar.doctorapp.model.GridModel;
import com.example.vinayakahebbar.doctorapp.utils.ActivityManager;
import com.example.vinayakahebbar.doctorapp.utils.type.FragmentType;
import com.example.vinayakahebbar.doctorapp.utils.type.GridType;
import com.example.vinayakahebbar.doctorapp.utils.type.ViewType;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener, AdapterView.OnItemClickListener {

    private int[] resources;
    private int curView;
    private Timer timer;
    private int TIMER_DELAY = 4000;
    private int TIMER_PERIOD = 3000;
    private GridView gridView;
    private View previousView;
    private int previousPosition;
    private GridViewAdapter gridViewAdapter;
    private List<GridModel> list;
    private TextView[] dots;
    private View view;
    private LinearLayout layoutDots;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
        resources = new int[]{
                R.drawable.doctors,
                R.drawable.medical,
                R.drawable.research,
                R.drawable.clinic
        };
        list = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        layoutDots = (LinearLayout) view.findViewById(R.id.layout_dots);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager_main);
        adapter = new ViewPageAdapter(view.getContext(),resources);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        initializeDots();
        setUpAnimation();
        setUpGridView();
        return view;
    }

    private void setUpGridView() {
        gridView=(GridView)view.findViewById(R.id.grid_view_main);
        list.add(new GridModel("Find Hospital",R.mipmap.ic_hospital, GridType.FRAGMENT, FragmentType.HOSPITAL));
        list.add(new GridModel("Find Doctor",R.mipmap.ic_doctor,GridType.FRAGMENT, FragmentType.DOC));
        list.add(new GridModel("Find Blood Bank",R.mipmap.ic_blood_bank,GridType.ACTIVITY, ViewType.BLOOD_BANK));
        list.add(new GridModel("Self Care",R.mipmap.ic_self_care,GridType.ACTIVITY, ViewType.SELF_CARE));
        gridViewAdapter = new GridViewAdapter(view.getContext(),R.layout.grid_view_item,list);
        gridView.setOnItemClickListener(this);
        gridView.setAdapter(gridViewAdapter);
    }


    private void setUpAnimation() {
        final Handler handler =  new Handler();
        final Runnable runnableUpdate = new Runnable() {
            @Override
            public void run() {
                if(curView == adapter.getCount())
                    curView = 0;
                viewPager.setCurrentItem(curView++,true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnableUpdate);
            }
        },TIMER_DELAY,TIMER_PERIOD);
    }

    private void initializeDots() {
        dots = new TextView[resources.length];
        for(int i=0;i<resources.length;i++){
            dots[i] = new TextView(view.getContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextColor(ContextCompat.getColor(view.getContext(),R.color.dim_white));
            dots[i].setTextSize(30);
            layoutDots.addView(dots[i]);
        }
        dots[0].setTextColor(ContextCompat.getColor(view.getContext(),R.color.colorAccent));

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        curView = position;
        for(int i=0;i<resources.length;i++){
            dots[i].setTextColor(ContextCompat.getColor(view.getContext(),R.color.dim_white));
        }
        dots[position].setTextColor(ContextCompat.getColor(view.getContext(),R.color.colorAccent));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setBackgroundColor(ContextCompat.getColor(this.view.getContext(),R.color.colorAccent));
        GridModel model = (GridModel) parent.getItemAtPosition(position);
        switch (model.getType()){
            case FRAGMENT:
                MainActivity.fragmentListener.loadFragment(model.getViewType());
                break;
            case ACTIVITY:
                new ActivityManager(getActivity()).startActivity(model.getViewType());
                break;
        }
        if(previousPosition != position) {
            if (previousView != null)
                previousView.setBackgroundColor(ContextCompat.getColor(this.view.getContext(), R.color.white));
        }

        previousView = view;
        previousPosition = position;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (previousView != null)
            previousView.setBackgroundColor(ContextCompat.getColor(this.view.getContext(), R.color.white));
    }
}
