package xyz.dnieln7.media.screenshot

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.media.common.MediaType
import xyz.dnieln7.media.common.UriStatus
import xyz.dnieln7.media.common.checkStatus
import xyz.dnieln7.media.common.createMediaUri

class ScreenshotHelper(
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
) {
    private val coroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    private val _screenshotStatus = Channel<ScreenshotStatus>()
    val screenshotStatus = _screenshotStatus.receiveAsFlow()

    private val mediaType get() = MediaType.SCREENSHOT

    fun takeScreenShot(view: View, expand: Boolean) {
        val context = view.context

        try {
            view.isDrawingCacheEnabled = true

            val bitmap = loadBitmap(view, expand)

            if (bitmap != null) {
                val uri = context.createMediaUri(mediaType)

                when (val result = uri.checkStatus()) {
                    is UriStatus.Valid -> {
                        val outputStream = context.contentResolver.openOutputStream(
                            result.uri,
                            "rw"
                        )

                        if (outputStream != null) {
                            val quality = 95

                            bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream)

                            outputStream.flush()
                            outputStream.close()

                            coroutineScope.launch {
                                _screenshotStatus.send(ScreenshotStatus.Taken(result.uri))
                            }
                        }
                    }

                    UriStatus.Invalid -> coroutineScope.launch {
                        _screenshotStatus.send(ScreenshotStatus.Failed)
                    }
                }
            }
        } catch (e: Throwable) {
            Log.e("ScreenshotHelper", "Error taking screenshot", e)
        } finally {
            view.isDrawingCacheEnabled = false

            if (expand) {
                view.isVisible = false
                view.isVisible = true
            }
        }
    }

    fun resetStatus() {
        coroutineScope.launch {
            _screenshotStatus.send(ScreenshotStatus.Ready)
        }
    }

    private fun loadBitmap(view: View, expand: Boolean): Bitmap? {
        val displayMetrics = view.context.resources.displayMetrics

        view.measure(
            View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY)
        )

        if (expand) {
            view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        }

        view.buildDrawingCache(true)

        return view.drawingCache.copy(Bitmap.Config.ARGB_8888, false)
    }
}