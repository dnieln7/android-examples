package xyz.dnieln7.media.picture

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import xyz.dnieln7.media.R
import xyz.dnieln7.media.common.toastShort
import xyz.dnieln7.media.databinding.ActivityPictureBinding

class PictureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPictureBinding

    private val pictureHelper = PictureHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pictureHelper.registerPictureFromGalleryListener(
            componentActivity = this,
            onSuccess = { loadImage(it) },
            onFailure = { toastShort(R.string.picture_error) }
        )

        pictureHelper.registerPictureFromCameraListener(
            componentActivity = this,
            onSuccess = { loadImage(it) },
            onFailure = { toastShort(R.string.picture_error) }
        )

        binding.selectFromGallery.setOnClickListener {
            pictureHelper.getPictureFromGallery()
        }

        binding.takeAPicture.setOnClickListener {
            pictureHelper.getPictureFromCamera(this)
        }
    }

    private fun loadImage(uri: Uri) {
        binding.picture.load(uri) {
            crossfade(true)
            error(R.drawable.ic_broken_image)
        }
    }
}