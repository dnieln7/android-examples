package xyz.dnieln7.media

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.dnieln7.media.databinding.ActivityMainBinding
import xyz.dnieln7.media.picture.PictureActivity
import xyz.dnieln7.media.screenshot.ScreenshotActivity
import xyz.dnieln7.media.video.VideoActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.picture.setOnClickListener {
            startActivity(Intent(this, PictureActivity::class.java))
        }

        binding.video.setOnClickListener {
            startActivity(Intent(this, VideoActivity::class.java))
        }

        binding.screenshot.setOnClickListener {
            startActivity(Intent(this, ScreenshotActivity::class.java))
        }
    }
}