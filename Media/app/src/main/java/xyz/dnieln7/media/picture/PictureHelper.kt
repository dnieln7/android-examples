package xyz.dnieln7.media.picture

import android.content.Context
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import xyz.dnieln7.media.common.MediaType
import xyz.dnieln7.media.common.UriStatus
import xyz.dnieln7.media.common.checkPictureStatus
import xyz.dnieln7.media.common.createMediaUri

class PictureHelper {

    private var cameraLauncher: ActivityResultLauncher<Uri>? = null
    private var galleryLauncher: ActivityResultLauncher<String>? = null

    private var cameraUri: Uri? = null

    private val mediaType get() = MediaType.PICTURE

    fun registerPictureFromCameraListener(
        componentActivity: ComponentActivity,
        onSuccess: (picture: Uri) -> Unit,
        onFailure: () -> Unit,
    ) {
        cameraLauncher = componentActivity.registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) {
            val result = cameraUri.checkPictureStatus(componentActivity.contentResolver)

            if (it) {
                when (result) {
                    is UriStatus.Valid -> onSuccess(result.uri)
                    UriStatus.Invalid -> {
                        releaseCameraUri()
                        onFailure()
                    }
                }
            } else {
                releaseCameraUri()
                onFailure()
            }
        }
    }

    fun registerPictureFromCameraListener(
        fragment: Fragment,
        onSuccess: (picture: Uri) -> Unit,
        onFailure: () -> Unit,
    ) {
        cameraLauncher = fragment.registerForActivityResult(ActivityResultContracts.TakePicture()) {
            val result = cameraUri.checkPictureStatus(fragment.requireContext().contentResolver)

            if (it) {
                when (result) {
                    is UriStatus.Valid -> onSuccess(result.uri)
                    UriStatus.Invalid -> {
                        releaseCameraUri()
                        onFailure()
                    }
                }
            } else {
                releaseCameraUri()
                onFailure()
            }
        }
    }

    fun getPictureFromCamera(context: Context) {
        cameraUri = context.createMediaUri(mediaType)

        cameraLauncher?.launch(cameraUri)
    }

    fun registerPictureFromGalleryListener(
        componentActivity: ComponentActivity,
        onSuccess: (picture: Uri) -> Unit,
        onFailure: () -> Unit,
    ) {
        galleryLauncher = componentActivity.registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            when (val result = it.checkPictureStatus(componentActivity.contentResolver)) {
                is UriStatus.Valid -> onSuccess(result.uri)
                UriStatus.Invalid -> onFailure()
            }
        }
    }

    fun registerPictureFromGalleryListener(
        fragment: Fragment,
        onSuccess: (picture: Uri) -> Unit,
        onFailure: () -> Unit,
    ) {
        galleryLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            when (val result = it.checkPictureStatus(fragment.requireContext().contentResolver)) {
                is UriStatus.Valid -> onSuccess(result.uri)
                UriStatus.Invalid -> onFailure()
            }
        }
    }

    fun getPictureFromGallery() {
        galleryLauncher?.launch(mediaType.mimeType)
    }

    fun releaseCameraUri() {
        cameraUri = null
    }

    fun onDestroy() {
        cameraLauncher?.unregister()
        cameraLauncher = null

        galleryLauncher?.unregister()
        galleryLauncher = null
    }
}