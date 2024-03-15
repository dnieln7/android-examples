package xyz.dnieln7.media.common

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File


fun Context.createMediaUri(mediaType: MediaType): Uri? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val path = StringBuilder()
            .append("DCIM")
            .append("/")
            .append(mediaType.directory)
            .append("/")
            .toString()

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, mediaType.buildName())
            put(MediaStore.MediaColumns.MIME_TYPE, mediaType.mimeType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, path)
        }

        if (mediaType == MediaType.VIDEO) {
            contentResolver.insert(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
        } else {
            contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
        }
    } else {
        val path = StringBuilder()
            .append(getExternalFilesDir(Environment.DIRECTORY_DCIM).toString())
            .append("/")
            .append(mediaType.directory)
            .append("/")
            .toString()

        val filePath = File(path)

        if (!filePath.exists()) {
            filePath.mkdir()
        }

        FileProvider.getUriForFile(
            applicationContext,
            "${packageName}.provider",
            File(filePath, mediaType.buildName())
        )
    }
}