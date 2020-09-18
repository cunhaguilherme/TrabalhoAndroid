package br.com.grupofgs.smartguide.fmc

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import br.com.grupofgs.smartguide.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


//FCM: Inicio na página 53
class CalculaFlexFCMService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        //selectNotification(p0)
        super.onMessageReceived(p0)
    }

    private fun sendNotification(intent: Intent, title: String?, message: String?) {

        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT)
        val channel = getString(R.string.default_notification_channel_id)

        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notification = NotificationCompat.Builder(this, channel)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setSound(sound)
            .setContentText(message) .setAutoCancel(true) .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
            channel,
            "SMART GUIDE AVISOS",
            NotificationManager.IMPORTANCE_DEFAULT
        )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, notification.build())
    }
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
}