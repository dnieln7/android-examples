package xyz.dnieln7.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.dnieln7.notification.receiver.NextDealReceiver
import xyz.dnieln7.notification.receiver.PreviousDealReceiver
import java.io.IOException
import java.net.URL

class NotificationsUtils(private val context: Context) {

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val packageName by lazy { context.packageName }

    init {
        val channel = NotificationChannel(
            DEALS_CHANNEL_ID,
            DEALS_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        channel.enableLights(true)
        channel.enableVibration(true)
        channel.importance = NotificationManager.IMPORTANCE_DEFAULT
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

        notificationManager.createNotificationChannel(channel)
    }

    suspend fun notifyDeals(dealsData: DealsData) {
        val prev = dealsData.positionToShow.dec()
        val next = dealsData.positionToShow.inc()

        val deal = dealsData.deals[dealsData.positionToShow]

        val notification: Notification
        val builder = NotificationCompat.Builder(context, DEALS_CHANNEL_ID)

        val viewShrunk = RemoteViews(packageName, R.layout.notification_custom_shrunk)
        val viewExpanded = RemoteViews(packageName, R.layout.notification_custom_expanded)

        val intent = Intent(context, DealDetailActivity::class.java).apply {
            putExtra(Deal.INTENT_KEY, deal)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (prev < 0) {
            viewExpanded.setViewVisibility(R.id.previous_deal, View.INVISIBLE)
        } else {
            val prevIntent = Intent(context, PreviousDealReceiver::class.java).apply {
                putExtra(PreviousDealReceiver.DATA_KEY, dealsData)
            }
            val prevPendingIntent = PendingIntent.getBroadcast(
                context,
                PreviousDealReceiver.REQUEST_CODE,
                prevIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            viewExpanded.setOnClickPendingIntent(R.id.previous_deal, prevPendingIntent)
        }

        if (next == dealsData.deals.size) {
            viewExpanded.setViewVisibility(R.id.next_deal, View.INVISIBLE)
        } else {

            val nextIntent = Intent(context, NextDealReceiver::class.java).apply {
                putExtra(NextDealReceiver.DATA_KEY, dealsData)
            }

            val nextPendingIntent = PendingIntent.getBroadcast(
                context,
                NextDealReceiver.REQUEST_CODE,
                nextIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            viewExpanded.setOnClickPendingIntent(R.id.next_deal, nextPendingIntent)
        }

        val oldPrice = deal.oldPrice.to2DecimalsString()
        val newPrice = deal.newPrice.to2DecimalsString()
        val getTheseFor = ContextCompat.getString(context, R.string.get_these_for)
        val dealString = "$getTheseFor $oldPrice $newPrice"

        val spannableDealString = SpannableString(dealString).apply {
            setSpan(
                StrikethroughSpan(),
                getTheseFor.indices.last + 2,
                getTheseFor.indices.last + 2 + oldPrice.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(Color.GRAY),
                getTheseFor.indices.last + 2,
                getTheseFor.indices.last + 2 + oldPrice.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
        }

        viewExpanded.setTextViewText(R.id.title, deal.name)
        viewExpanded.setTextViewText(R.id.deal_price, spannableDealString)
        viewExpanded.setTextViewText(R.id.description, deal.description)

        urlToBitmap(deal.image)?.let {
            viewExpanded.setImageViewBitmap(R.id.image, it)
        }

        notification = builder
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.ic_deal)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(viewShrunk)
            .setCustomBigContentView(viewExpanded)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(DEALS_NOTIFICATION_ID, notification)
    }

    private suspend fun urlToBitmap(url: String): Bitmap? {
        return try {
            val image = withContext(Dispatchers.IO) {
                BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
            }

            return image
        } catch (e: IOException) {
            Log.e("urlToBitmap", "Error", e)

            null
        }
    }

    fun clearDealsNotification() {
        notificationManager.cancel(DEALS_NOTIFICATION_ID)
    }
}

private const val DEALS_CHANNEL_NAME = "Deals"
private const val DEALS_CHANNEL_ID = "xyz.dnieln7.DEALS"
private const val DEALS_NOTIFICATION_ID = 777
