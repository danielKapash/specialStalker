package daniel.kapash.specialstalker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class OutGoingCallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String phoneNumberToSendSmsTo = sp.getString(MainActivity.PHONE_NUMBER_KEY_SP, "");
        String smsPrefix = sp.getString(MainActivity.SMS_PREFIX_KEY_SP, "");

        String outgoingCallPhoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        String smsMessage = smsPrefix + outgoingCallPhoneNumber;

        SmsManager.getDefault().sendTextMessage(phoneNumberToSendSmsTo, null, smsMessage, null, null);
        Log.d("OutGoingCallReceiver", "onReceive");
    }
}
