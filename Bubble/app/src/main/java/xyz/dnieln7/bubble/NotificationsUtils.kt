package xyz.dnieln7.bubble

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class NotificationsUtils(private val context: Context) {

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        val serviceChannel = NotificationChannel(
            BUBBLE_CHANNEL_ID,
            BUBBLE_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        serviceChannel.enableVibration(true)
        serviceChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

        notificationManager.createNotificationChannel(serviceChannel)
    }

    fun buildBubbleNotification(): Notification {
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(context, BUBBLE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_power)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentTitle(context.getString(R.string.battery_service))
            .setContentText(context.getString(R.string.battery_bubble_running))
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .build()

    }
}

private const val BUBBLE_CHANNEL_NAME = "Bubble notification"
private const val BUBBLE_CHANNEL_ID = "xyz.dnieln7.BUBBLE"
const val BUBBLE_NOTIFICATION_ID = 888
