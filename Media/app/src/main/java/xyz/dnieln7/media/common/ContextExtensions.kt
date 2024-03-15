package xyz.dnieln7.media.common

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toastShort(@StringRes messageId: Int) {
    Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(@StringRes messageId: Int) {
    Toast.makeText(this, messageId, Toast.LENGTH_LONG).show()
}
