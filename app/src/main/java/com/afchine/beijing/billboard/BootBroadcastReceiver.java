/**
 * Created by simon on 30/01/2018.
 */
package com.afchine.beijing.billboard;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;



public class BootBroadcastReceiver extends BroadcastReceiver {

    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(ACTION))
        {
            Intent mainActivityIntent = new Intent(context, MainActivity.class);
            //Intent mainActivityIntent_2 = new Intent(context, NetWorkStateReceiver.class);
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //mainActivityIntent_2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainActivityIntent);
            //context.startActivity(mainActivityIntent_2);
        }
    }

}
