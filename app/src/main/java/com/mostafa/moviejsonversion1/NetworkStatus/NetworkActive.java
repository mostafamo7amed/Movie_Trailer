package com.mostafa.moviejsonversion1.NetworkStatus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkActive {

  public static String getNetworkStatus (Context context){
      String status = null;
      ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
      if(networkInfo != null)
      {


          
          if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
              status = "Wifi connected";
              return  status;
          }
          else if(networkInfo.getType()== ConnectivityManager.TYPE_MOBILE){
              status = "Mobile data connected";
              return status;
          }



      }else {
          status = "No internet";
          return status;
      }
      return status;

  }




}
