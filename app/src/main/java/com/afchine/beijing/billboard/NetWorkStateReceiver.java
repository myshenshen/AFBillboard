/**
 * Created by simon on 06/02/2018.
 */
package com.afchine.beijing.billboard;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Carson_Ho on 16/10/31.
 */
public class NetWorkStateReceiver extends BroadcastReceiver {

    Dialog dia;
    @Override
    public void onReceive(Context context, Intent intent) {
        dia = new Dialog(context, R.style.edit_AlertDialog_style);
        dia.setContentView(R.layout.activity_nonetwork);
        ImageView imageView = (ImageView) dia.findViewById(R.id.nonetwork_img);
        System.out.println("网络状态发生变化");
        //检测API是不是小于21，因为到了API21之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
           // NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            /*if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
            }*/
            if(wifiNetworkInfo.isConnected())
            {
                Toast.makeText(context,"网络已连接",Toast.LENGTH_SHORT).show();
                dia.dismiss();
            }
            else
            {
                Toast.makeText(context,"网络已断开",Toast.LENGTH_SHORT).show();
                dia.show();
            }
           /* if(wifiNetworkInfo.isConnected())
            {
                Toast.makeText(context,"网络已连接",Toast.LENGTH_SHORT).show();
                dia.dismiss();
            }
            else
            {
                Toast.makeText(context,"网络已断开",Toast.LENGTH_SHORT).show();
                dia.show();
            }*/
        }else {
            //android5.0以上用这一段
            System.out.println("API level 大于21");
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            //用于存放网络连接信息
            StringBuilder sb = new StringBuilder();
            //通过循环将网络信息逐个取出来
            for (int i=0; i < networks.length; i++){
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
            }
            Toast.makeText(context, sb.toString(),Toast.LENGTH_SHORT).show();
        }
    }

}
