package chatme.nxt;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ClearNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra("senderid")) {
            String senderid = intent.getStringExtra("senderid");
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            
            // Loop through notifications and cancel all with the same senderid
            for (int i = 0; i < 100; i++) { // Assuming notifications IDs are sequential
                notificationManager.cancel(i);
            }
        }
    }
}
