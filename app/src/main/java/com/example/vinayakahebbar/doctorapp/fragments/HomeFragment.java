package com.example.vinayakahebbar.doctorapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.activities.MainActivity;
import com.example.vinayakahebbar.doctorapp.adapter.ViewPageAdapter;
import com.example.vinayakahebbar.doctorapp.interfaces.FragmentListener;
import com.example.vinayakahebbar.doctorapp.utils.type.FragmentType;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private int[] resources;
    private int curView;
    private Timer timer;
    private int TIMER_DELAY = 4000;
    private int TIMER_PERIOD = 3000;
    private CardView cardViewHospital,cardViewDoctor;
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
        setUpCardView();
        return view;
    }

    private void setUpCardView() {
        cardViewHospital = (CardView) view.findViewById(R.id.cv_hospital);
        cardViewHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentListener.loadFragment(FragmentType.HOSPITAL_LIST);
            }
        });
        cardViewDoctor = (CardView)view.findViewById(R.id.cv_doctor);
        cardViewDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentListener.loadFragment(FragmentType.DOCTOR_LOC);
            }
        });

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
}
