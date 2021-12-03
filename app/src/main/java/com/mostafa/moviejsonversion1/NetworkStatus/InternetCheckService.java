package com.mostafa.moviejsonversion1.NetworkStatus;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import com.mostafa.moviejsonversion1.Activity.MainActivity;
import com.mostafa.moviejsonversion1.R;

public class InternetCheckService extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkActive.getNetworkStatus(context);
        Dialog dialog = new Dialog(context , android.R.style.Theme_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.no_internet);
        Button retry = dialog.findViewById(R.id.retry);
        retry.setOnClickListener(v -> {
            ((Activity)context).finish();
            Intent intent1 = new Intent(context, MainActivity.class);
            context.startActivity(intent1);

        });


        if(status.equals("No internet") || status.isEmpty()){
            if(!((Activity) context).isFinishing())
            {
                //show dialog
                dialog.show();
                //Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
            }

        }

    }



}
