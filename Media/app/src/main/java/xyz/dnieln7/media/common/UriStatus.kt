package xyz.dnieln7.media.common

import android.net.Uri

sealed class UriStatus {
    data object Invalid : UriStatus()
    class Valid(val uri: Uri) : UriStatus()
}