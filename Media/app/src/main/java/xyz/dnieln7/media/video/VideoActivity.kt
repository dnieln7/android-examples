package xyz.dnieln7.media.video

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.dnieln7.media.R
import xyz.dnieln7.media.databinding.ActivityVideoBinding
import xyz.dnieln7.media.common.toastShort

class VideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoBinding

    private val videoHelper = VideoHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoHelper.registerVideoFromGalleryListener(
            componentActivity = this,
            onSuccess = { loadVideo(it) },
            onFailure = { toastShort(R.string.video_error) }
        )

        videoHelper.registerVideoFromCameraListener(
            componentActivity = this,
            onSuccess = { loadVideo(it) },
            onFailure = { toastShort(R.string.video_error) }
        )

        binding.selectFromGallery.setOnClickListener {
            videoHelper.getVideoFromGallery()
        }

        binding.recordAVideo.setOnClickListener {
            videoHelper.getVideoFromCamera(this)
        }
    }

    override fun onDestroy() {
        videoHelper.onDestroy()
        super.onDestroy()
    }

    private fun loadVideo(uri: Uri) {
        if (binding.video.isPlaying) {
            binding.video.stopPlayback()
        }

        binding.video.setVideoURI(uri)
        binding.video.setOnPreparedListener { it.isLooping = true }
        binding.video.start()
    }
}