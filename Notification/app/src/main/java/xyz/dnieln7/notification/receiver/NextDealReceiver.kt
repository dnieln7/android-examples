package xyz.dnieln7.notification.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import xyz.dnieln7.notification.DealsData
import xyz.dnieln7.notification.NotificationsUtils
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class NextDealReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            val dealsData = intent.getSerializableExtra(DATA_KEY) as DealsData
            val pos = dealsData.positionToShow.inc()

            MainScope().launch {
                NotificationsUtils(context).notifyDeals(
                    dealsData = dealsData.copy(positionToShow = pos),
                )
            }
        }
    }

    companion object {
        const val REQUEST_CODE = 997
        const val DATA_KEY = "DEALS_DATA"
    }
}
