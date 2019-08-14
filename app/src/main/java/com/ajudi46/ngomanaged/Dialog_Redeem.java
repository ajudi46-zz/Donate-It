package com.ajudi46.ngomanaged;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

public class Dialog_Redeem extends AppCompatDialogFragment {
    int unicode = 0x1F525;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        String emoji = new String(Character.toChars(unicode));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Redeem Rate")
                .setMessage("10 " + emoji + " = 1 Rupee")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });
        return builder.create();
    }
}
