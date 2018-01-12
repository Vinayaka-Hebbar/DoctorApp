package com.example.vinayakahebbar.doctorapp.utils.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.vinayakahebbar.doctorapp.R;


public class RateUsDialog {
    private Context context;

    public RateUsDialog(Context context) {
        this.context = context;
    }

    public void showRateUs(){
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.rateusdialog,null);
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context,"Thank you",Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.setTitle("Rate us");
        dialog.show();
    }
}
