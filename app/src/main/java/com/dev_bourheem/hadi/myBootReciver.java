package com.dev_bourheem.hadi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class myBootReciver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED") ) {
            // on device boot compelete, reset the alarm
            Intent notifyIntent = new Intent(context, mineAlarmReciever.class);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 18);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 1);
            PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                    (context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            long repeatInterval = AlarmManager.INTERVAL_HALF_DAY;
            long triggerTime = SystemClock.elapsedRealtime()+repeatInterval;

//If the Toggle is turned on, set the repeating alarm with a 15 minute interval
            if (alarmManager != null) {
                alarmManager.setInexactRepeating
                        (AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                triggerTime, repeatInterval, notifyPendingIntent);
            }

        }
    }

}
