package daniel.kapash.specialstalker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsManager;
import android.util.Log;

public class OutGoingCallReceiver extends BroadcastReceiver {

    private static final String STALKER_NOTIFICATION_CHANNEL_ID = "notification_channel_id";
    public static final int NOTIFICATION_ID = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String phoneNumberToSendSmsTo = sp.getString(MainActivity.PHONE_NUMBER_KEY_SP, "");
        String smsPrefix = sp.getString(MainActivity.SMS_PREFIX_KEY_SP, "");

        String outgoingCallPhoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        String smsMessage = smsPrefix + outgoingCallPhoneNumber;

        createNotificationChannel(context);

        Notification notification = getSendingSmsNotification(context);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(NOTIFICATION_ID, notification);


        SmsManager.getDefault().sendTextMessage(phoneNumberToSendSmsTo, null, smsMessage, null, null);
        Log.d("OutGoingCallReceiver", "onReceive");
    }

    private Notification getSendingSmsNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, STALKER_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Stalker Activated")
                .setContentText("Sending message...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder.build();
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(STALKER_NOTIFICATION_CHANNEL_ID, "Notification", importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
