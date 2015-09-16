package com.androidyug.nitin.sociallogin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by IAMONE on 9/16/2015.
 */
public class PushReceiver extends ParsePushBroadcastReceiver {
    @Override
    public void onPushOpen(Context context, Intent intent) {
        Log.d("Push", "Clicked");
        Intent i = new Intent(context, MainActivity.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
