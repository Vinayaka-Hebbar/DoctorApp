package com.example.vinayakahebbar.doctorapp.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.vinayakahebbar.doctorapp.R;
import com.example.vinayakahebbar.doctorapp.adapter.BloodBankListAdapter;
import com.example.vinayakahebbar.doctorapp.adapter.BloodBankLocationAdapter;
import com.example.vinayakahebbar.doctorapp.interfaces.JsonListener;
import com.example.vinayakahebbar.doctorapp.interfaces.NetworkListener;
import com.example.vinayakahebbar.doctorapp.model.BloodBank;
import com.example.vinayakahebbar.doctorapp.model.BloodBankLocation;
import com.example.vinayakahebbar.doctorapp.model.ModelView;
import com.example.vinayakahebbar.doctorapp.utils.HttpUtils;
import com.example.vinayakahebbar.doctorapp.utils.JsonIO;
import com.example.vinayakahebbar.doctorapp.utils.dialogs.NotifyDialog;
import com.example.vinayakahebbar.doctorapp.utils.helper.SnackBarHelper;
import com.example.vinayakahebbar.doctorapp.views.LinearProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class BloodBankActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener, NetworkListener, JsonListener {

    private Spinner spinner;
    private BloodBankLocationAdapter adapter;
    private BloodBankListAdapter bankListAdapter;
    private ListView listView;
    private LinearProgressDialog dialog;
    private Toolbar toolbar;
    private List<BloodBank> banks;
    private List<BloodBankLocation> list;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);
        list = new ArrayList<>();
        banks = new ArrayList<>();
        list.add(new BloodBankLocation("All", ""));
        toolbar = (Toolbar) findViewById(R.id.toolbar_blood_bank);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        layout = (LinearLayout)findViewById(R.id.layout_blood_bank);
        spinner = (Spinner) findViewById(R.id.spinner_blood_bank);
        listView = (ListView) findViewById(R.id.lv_blood_bank);
        adapter = new BloodBankLocationAdapter(this, list);
        bankListAdapter = new BloodBankListAdapter(this, R.layout.blood_bank_list_item, banks);
        listView.setAdapter(bankListAdapter);
        listView.setOnItemClickListener(this);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        showPopUpLoad();
    }

    private void showPopUpLoad() {
        dialog = new LinearProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.show();
    }

    @Override
    protected void onStart() {
        HttpUtils httpUtils = new HttpUtils()
                .setOnLoaded(this);
        httpUtils.getBloodBankLocations();
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        banks.clear();
        bankListAdapter.notifyDataSetChanged();
        dialog.show();
        BloodBankLocation location = (BloodBankLocation) parent.getItemAtPosition(position);
        HttpUtils httpUtils = new HttpUtils(new String[]{location.getName()});
        httpUtils.setOnLoaded(new NetworkListener() {
            @Override
            public void onLoaded(String response) {
                JsonIO jsonIO = new JsonIO(response);
                jsonIO.setOnLoaded(new JsonListener() {
                    @Override
                    public void onParsed(List<ModelView> lists) {
                        for (ModelView view :
                                lists) {
                            banks.add((BloodBank) view);
                        }
                        bankListAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                    @Override
                    public void onParseError(String error) {
                        dialog.dismiss();
                    }
                });
                jsonIO.getBloodBankInfo();
            }

            @Override
            public void onNetworkError(String error) {
                dialog.dismiss();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final BloodBank bank = (BloodBank) parent.getItemAtPosition(position);
        NotifyDialog dialog = new NotifyDialog(view.getContext());
        dialog.setClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + bank.getPhoneNo()));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });
        dialog.show("Do you want to call " + bank.getName(),"Call");
    }

    @Override
    public void onLoaded(String response) {
        JsonIO jsonIO = new JsonIO(response);
        jsonIO.setOnLoaded(this);
        jsonIO.getBloodBankLocations();
    }

    @Override
    public void onNetworkError(String error) {
        dialog.dismiss();
        new SnackBarHelper(layout,error, Snackbar.LENGTH_SHORT).showError();
    }

    @Override
    public void onParsed(List<ModelView> lists) {
        for (ModelView view :
                lists) {
            list.add((BloodBankLocation) view);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onParseError(String error) {
        dialog.dismiss();
    }
}
