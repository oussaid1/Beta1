package com.dev_bourheem.hadi;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import static com.dev_bourheem.hadi.MainActivity.*;

public class Alerdialogue extends AppCompatDialogFragment {
    MainActivity Mact;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_error);
        builder.setTitle("Alert");
        builder.setMessage("Are You Sure");
                builder.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Mact.LoadDatabase();
                        Mact.getTotal();
                        Mact.GetDbData();
                        Mact.TraficLight();*/
                    }
                });
        return builder.create();
    }
}
