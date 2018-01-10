package com.example.vinayakahebbar.doctorapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.adapter.BloodBankListAdapter;
import com.example.vinayakahebbar.doctorapp.adapter.BloodBankLocationAdapter;
import com.example.vinayakahebbar.doctorapp.interfaces.OnListLoaded;
import com.example.vinayakahebbar.doctorapp.interfaces.OnLoaded;
import com.example.vinayakahebbar.doctorapp.model.BloodBank;
import com.example.vinayakahebbar.doctorapp.model.BloodBankLocation;
import com.example.vinayakahebbar.doctorapp.model.ModelView;
import com.example.vinayakahebbar.doctorapp.utils.HttpUtils;
import com.example.vinayakahebbar.doctorapp.utils.JsonIO;
import com.example.vinayakahebbar.doctorapp.views.LinearProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class BloodBankActivity extends AppCompatActivity implements OnLoaded, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner spinner;
    private BloodBankLocationAdapter adapter;
    private BloodBankListAdapter bankListAdapter;
    private ListView listView;
    private LinearProgressDialog dialog;
    private Toolbar toolbar;
    private List<BloodBank> banks;
    private List<BloodBankLocation> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);
        list = new ArrayList<>();
        banks = new ArrayList<>();
        list.add(new BloodBankLocation("All",""));
        toolbar = (Toolbar)findViewById(R.id.toolbar_blood_bank);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        spinner = (Spinner)findViewById(R.id.spinner_blood_bank);
        listView = (ListView)findViewById(R.id.lv_blood_bank);
        adapter = new BloodBankLocationAdapter(this,list);
        bankListAdapter = new BloodBankListAdapter(this,R.layout.blood_bank_list_item,banks);
        listView.setAdapter(bankListAdapter);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        showPopUpLoad();
        super.onResume();
    }

    private void showPopUpLoad() {
        dialog = new LinearProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.show();
    }

    @Override
    protected void onStart() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.setOnLoaded(this);
        httpUtils.getBloodBankLocations();
        super.onStart();
    }

    @Override
    public void Update(String text) {
        JsonIO jsonIO = new JsonIO(text);
        jsonIO.setOnLoaded(new OnListLoaded() {
            @Override
            public void UpdateList(List<ModelView> lists) {
                for (ModelView view :
                        lists) {
                    list.add((BloodBankLocation) view);
                }
                adapter.notifyDataSetChanged();
            }
        });
        jsonIO.getBloodBankLocations();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        banks.clear();
        bankListAdapter.notifyDataSetChanged();
        dialog.show();
        BloodBankLocation location = (BloodBankLocation)parent.getItemAtPosition(position);
        HttpUtils httpUtils = new HttpUtils(new String[]{location.getName()});
        httpUtils.setOnLoaded(new OnLoaded() {
            @Override
            public void Update(String text) {
                JsonIO jsonIO = new JsonIO(text);
                jsonIO.setOnLoaded(new OnListLoaded() {
                    @Override
                    public void UpdateList(List<ModelView> lists) {
                        for (ModelView view :
                                lists) {
                            banks.add((BloodBank) view);
                        }
                        bankListAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                jsonIO.getBloodBankInfo();
            }
        });
        httpUtils.getBloodBankInfo();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
