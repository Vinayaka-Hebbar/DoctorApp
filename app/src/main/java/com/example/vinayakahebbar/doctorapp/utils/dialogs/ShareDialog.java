package com.example.vinayakahebbar.doctorapp.utils.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class ShareDialog {
    private Context context;

    public ShareDialog(Context context) {
        this.context = context;
    }

    public void show(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setPositiveButton("Share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "My app name");
                    String strShareMessage = "\nLet me recommend you this application\n\n";
                    strShareMessage = strShareMessage + "https://play.google.com/store/apps/details?id=" + context.getPackageName();
                    Uri screenshotUri = Uri.parse("android.resource://packagename/drawable/image_name");
                    i.setType("image/png");
                    i.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                    i.putExtra(Intent.EXTRA_TEXT, strShareMessage);
                   context.startActivity(Intent.createChooser(i, "Share via"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog dialog=builder.create();
        dialog.setTitle("Share App");
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
