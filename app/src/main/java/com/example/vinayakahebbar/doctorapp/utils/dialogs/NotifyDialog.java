package com.example.vinayakahebbar.doctorapp.utils.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

/**
 * Created by Vinayaka Hebbar on 11-01-2018.
 */

public class NotifyDialog {
    private Context context;
    private DialogInterface.OnClickListener clickListener;

    public NotifyDialog(Context context) {
        this.context = context;
    }

    public void show(String message,String positiveMessage){
        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setPositiveButton(positiveMessage, clickListener);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog dialog=builder.create();
        dialog.setTitle(message);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void setClickListener(DialogInterface.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
