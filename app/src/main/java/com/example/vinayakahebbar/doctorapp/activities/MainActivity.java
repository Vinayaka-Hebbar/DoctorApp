package com.example.vinayakahebbar.doctorapp.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.fragments.DoctorLocationFragment;
import com.example.vinayakahebbar.doctorapp.fragments.DoctorsListFragment;
import com.example.vinayakahebbar.doctorapp.fragments.HelpFragment;
import com.example.vinayakahebbar.doctorapp.fragments.HomeFragment;
import com.example.vinayakahebbar.doctorapp.fragments.HospitalFragment;
import com.example.vinayakahebbar.doctorapp.fragments.MapHospitalFragment;
import com.example.vinayakahebbar.doctorapp.fragments.ProfileFragment;
import com.example.vinayakahebbar.doctorapp.interfaces.FragmentListener;
import com.example.vinayakahebbar.doctorapp.utils.UserManager;
import com.example.vinayakahebbar.doctorapp.utils.type.FragmentType;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,FragmentListener {
    private DrawerLayout drawerLayout;
    private DrawerArrowDrawable arrowDrawable;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private LinearLayout layout;
    private String CURRENT_TAG = "Home";
    private FragmentManager fragmentManager;
    private FragmentType currentType;
    private View navHeader;
    public static FragmentListener fragmentListener;
    private boolean isBackPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        navigationView =(NavigationView)findViewById(R.id.nav_view);
        layout = (LinearLayout)findViewById(R.id.layout_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        arrowDrawable = new DrawerArrowDrawable(this);
        toolbar.setNavigationIcon(arrowDrawable);
        fragmentListener = this;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerOpen(navigationView))
                    drawerLayout.closeDrawer(Gravity.START);
                else
                    drawerLayout.openDrawer(Gravity.START);
            }
        });
        setUpDrawer();
        setUpFragment();
        setUpUser();
    }

    private void setUpUser() {
        navHeader = navigationView.getHeaderView(0);
        TextView textViewUser = (TextView)navHeader.findViewById(R.id.tv_header_user);
        textViewUser.setText(UserManager.getUserName());
    }

    private void setUpFragment() {
        fragmentManager = getFragmentManager();
        loadFragment(FragmentType.HOME);
    }

    private Fragment getFragment(FragmentType type) {
        currentType = type;
        switch (type){
            case HOME:
                CURRENT_TAG = "Home";
                return new  HomeFragment();
            case PROFILE:
                CURRENT_TAG = "Profile";
                return new ProfileFragment();
            case DOCTOR_LIST:
                CURRENT_TAG = "Doctors";
                return new DoctorsListFragment();
            case HELP:
                CURRENT_TAG = "Help";
                return new HelpFragment();
            case HOSPITAL_LIST:
                CURRENT_TAG = "Hospitals";
                return new HospitalFragment();
            case DOCTOR_LOC:
                CURRENT_TAG="Doctors";
                return new DoctorLocationFragment();
        }
        return null;
    }

    public void setUpDrawer(){
            drawerLayout.setScrimColor(Color.TRANSPARENT);
            drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    layout.setX(navigationView.getWidth() * slideOffset);
                    arrowDrawable.setProgress(slideOffset);
                }

                @Override
                public void onDrawerOpened(View drawerView) {

                }

                @Override
                public void onDrawerClosed(View drawerView) {

                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_logout:
                UserManager.sigOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.menu_home:
                loadFragment(FragmentType.HOME);
                break;
            case R.id.menu_help:
                loadFragment(FragmentType.HELP);
                break;
            case R.id.menu_profile:
                loadFragment(FragmentType.PROFILE);
                break;
        }
        drawerLayout.closeDrawers();
        item.setChecked(true);
        return false;
    }

    @Override
    public void loadFragment(final FragmentType type) {
        if(currentType == type){
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getFragment(type);
                setPageTitle();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                fragmentTransaction.replace(R.id.fragment_main,fragment,CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });
    }

    private void setPageTitle() {
        getSupportActionBar().setTitle(CURRENT_TAG);
    }

    @Override
    public void onBackPressed() {
        if(!isBackPress)
        {
            switch (currentType){
                case DOCTOR_LOC:
                    loadFragment(FragmentType.HOME);
                    break;
                case DOCTOR_LIST:
                    loadFragment(FragmentType.DOCTOR_LOC);
                    break;
                case HOSPITAL_LIST:
                    loadFragment(FragmentType.HOME);
                    break;
                case PROFILE:
                    loadFragment(FragmentType.HOME);
                    break;
                case HELP:
                    loadFragment(FragmentType.HOME);
                    break;
                case HOME:
                    Snackbar.make(getCurrentFocus(),"Press again to close",Snackbar.LENGTH_SHORT).show();
                    isBackPress = true;
                    break;
            }
            return;
        }
        super.onBackPressed();
    }
}


