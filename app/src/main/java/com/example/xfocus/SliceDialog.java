package com.example.xfocus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.xfocus.HomePages.Dashboard;

import java.text.NumberFormat;
import java.util.Locale;

public class SliceDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(Dashboard.SliceLabel);
                if (Dashboard.SliceValue.equals("1.0")){
                    builder.setMessage("Value : " + 0);
                }
                else{
                    builder.setMessage("Value : " + NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(Dashboard.SliceValue)));
                }
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
