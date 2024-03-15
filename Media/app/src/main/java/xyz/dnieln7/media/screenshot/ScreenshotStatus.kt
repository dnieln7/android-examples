package xyz.dnieln7.media.screenshot

import android.net.Uri

sealed class ScreenshotStatus {
    data object Ready : ScreenshotStatus()
    data object Failed : ScreenshotStatus()
    class Taken(val uri: Uri) : ScreenshotStatus()
}