package com.example.rent.firebaseapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by RENT on 2017-03-04.
 */
/*
{ "data": {
    "title": <TITLE>  ,
    "text": <TEXT>
  },
  "to" : <TOKEN>
}

 */
public class MyFireBaseMessagingService extends FirebaseMessagingService{

    private static final String TAG = MyFireBaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData()!=null){
          Map<String,String> data =   remoteMessage.getData();
            Log.d(TAG, "onMessageReceived: data: " +"title: "+data.get("title")+", text: "+data.get("text"));

            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(this)
                                                .setContentText(data.get("text"))
                                                .setSmallIcon(R.drawable.ic_drink)
                                                .setContentTitle(data.get("title"))
                                                .setColor(ContextCompat.getColor(this,R.color.colorAccent))
                                                .setContentIntent(pendingIntent)
                                                .build();
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(0,notification);
        }
        else{
            Log.d(TAG, "From: " + remoteMessage.getFrom());
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
