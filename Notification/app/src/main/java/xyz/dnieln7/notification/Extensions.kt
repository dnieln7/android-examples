package xyz.dnieln7.notification

import java.util.Locale

fun Double.to2DecimalsString(locale: Locale = Locale.getDefault()): String {
    return String.format(locale, "%.2f", this)
}
