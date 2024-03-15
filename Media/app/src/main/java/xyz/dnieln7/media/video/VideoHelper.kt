package xyz.dnieln7.media.video

import android.content.Context
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import xyz.dnieln7.media.common.MediaType
import xyz.dnieln7.media.common.UriStatus
import xyz.dnieln7.media.common.checkVideoStatus
import xyz.dnieln7.media.common.createMediaUri

class VideoHelper {

    private var cameraLauncher: ActivityResultLauncher<Uri>? = null
    private var galleryLauncher: ActivityResultLauncher<String>? = null

    private var cameraUri: Uri? = null

    private val mediaType get() = MediaType.VIDEO

    fun registerVideoFromCameraListener(
        componentActivity: ComponentActivity,
        onSuccess: (picture: Uri) -> Unit,
        onFailure: () -> Unit,
    ) {
        cameraLauncher = componentActivity.registerForActivityResult(
            ActivityResultContracts.CaptureVideo()
        ) {
            val result = cameraUri.checkVideoStatus(componentActivity)

            if (it) {
                when (result) {
                    is UriStatus.Valid -> onSuccess(result.uri)
                    UriStatus.Invalid -> {
                        cameraUri = null
                        onFailure()
                    }
                }
            } else {
                cameraUri = null
                onFailure()
            }
        }
    }

    fun registerVideoFromCameraListener(
        fragment: Fragment,
        onSuccess: (picture: Uri) -> Unit,
        onFailure: () -> Unit,
    ) {
        cameraLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.CaptureVideo()
        ) {
            val result = cameraUri.checkVideoStatus(fragment.requireContext())

            if (it) {
                when (result) {
                    is UriStatus.Valid -> onSuccess(result.uri)
                    UriStatus.Invalid -> {
                        cameraUri = null
                        onFailure()
                    }
                }
            } else {
                cameraUri = null
                onFailure()
            }
        }
    }

    fun getVideoFromCamera(context: Context) {
        cameraUri = context.createMediaUri(mediaType)

        cameraLauncher?.launch(cameraUri)
    }

    fun registerVideoFromGalleryListener(
        componentActivity: ComponentActivity,
        onSuccess: (picture: Uri) -> Unit,
        onFailure: () -> Unit,
    ) {
        galleryLauncher = componentActivity.registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            when (val result = it.checkVideoStatus(componentActivity)) {
                is UriStatus.Valid -> onSuccess(result.uri)
                UriStatus.Invalid -> onFailure()
            }
        }
    }

    fun registerVideoFromGalleryListener(
        fragment: Fragment,
        onSuccess: (picture: Uri) -> Unit,
        onFailure: () -> Unit,
    ) {
        galleryLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            when (val result = it.checkVideoStatus(fragment.requireContext())) {
                is UriStatus.Valid -> onSuccess(result.uri)
                UriStatus.Invalid -> onFailure()
            }
        }
    }

    fun getVideoFromGallery() {
        galleryLauncher?.launch(mediaType.mimeType)
    }

    fun onDestroy() {
        cameraLauncher?.unregister()
        cameraLauncher = null

        galleryLauncher?.unregister()
        galleryLauncher = null
    }
}