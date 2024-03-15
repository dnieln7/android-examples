package xyz.dnieln7.media.common

import android.content.ContentResolver
import android.content.Context
import android.graphics.ImageDecoder
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import java.io.IOException

fun Uri?.checkStatus(): UriStatus {
    return if (this != null) {
        UriStatus.Valid(this)
    } else {
        UriStatus.Invalid
    }
}

@Suppress("DEPRECATION")
fun Uri?.checkPictureStatus(contentResolver: ContentResolver): UriStatus {
    return if (this != null) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.createSource(contentResolver, this).let {
                    ImageDecoder.decodeBitmap(it)
                }
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, this)
            }

            UriStatus.Valid(this)
        } catch (_: IOException) {
            UriStatus.Invalid
        }
    } else {
        UriStatus.Invalid
    }
}

fun Uri?.checkVideoStatus(context: Context): UriStatus {
    return if (this != null) {
        val player = MediaPlayer.create(context, this)

        if (player != null) {
            player.reset()
            player.release()

            UriStatus.Valid(this)
        } else {
            UriStatus.Invalid
        }
    } else {
        UriStatus.Invalid
    }
}
