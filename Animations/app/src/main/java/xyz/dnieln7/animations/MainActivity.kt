package xyz.dnieln7.animations

import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import xyz.dnieln7.animations.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rotate.setOnClickListener {
            CommonAnimators.rotate(binding.waterDrop, 360F)
        }

        binding.translate.setOnClickListener {
            CommonAnimators.translateHorizontal(binding.waterDrop, 300F)
        }

        binding.scale.setOnClickListener {
            CommonAnimators.scale(binding.waterDrop, 3F)
        }

        binding.fade.setOnClickListener {
            CommonAnimators.fade(binding.waterDrop, 0F)
        }

        binding.changeColor.setOnClickListener {
            CommonAnimators.changeColor(
                binding.waterDrop,
                getColor(R.color.secondary),
                getColor(R.color.primary)
            )
        }

        binding.shower.setOnClickListener {
            CommonAnimators.shower(binding.waterContainer, newWaterDrop())
        }

        binding.tabs.tab1.setOnClickListener {
            CommonAnimators.translateHorizontalAndChangeColor(
                container = binding.tabs.root,
                selector = binding.tabs.tabSelector,
                startPosition = (binding.tabs.tabSelector.translationX),
                distance = 0F,
                fromColor = getColor(android.R.color.holo_red_light),
                toColor = getColor(android.R.color.holo_blue_dark),
            )
        }

        binding.tabs.tab2.setOnClickListener {
            CommonAnimators.translateHorizontalAndChangeColor(
                container = binding.tabs.root,
                selector = binding.tabs.tabSelector,
                distance = binding.tabs.tabLayout.width - (binding.tabs.tabSelector.width * 1F),
                toRight = true,
                fromColor = getColor(android.R.color.holo_blue_dark),
                toColor = getColor(android.R.color.holo_red_light),
            )
        }
    }

    private fun newWaterDrop(): ImageView {
        val water = ImageView(this)

        water.setImageResource(R.drawable.ic_water_drop)
        water.layoutParams = RelativeLayout.LayoutParams(
            50,
            50,
        )

        val color1 = getColor(R.color.secondary)
        val color2 = getColor(R.color.secondaryDark)
        val color3 = getColor(R.color.secondaryLight)

        val random = Math.random().toFloat()

        val waterColor = when {
            random <= 0.5F -> color1
            random > 0.5F -> color2
            else -> color3
        }

        water.setColorFilter(waterColor)

        water.translationX = random * (binding.waterContainer.width - 50F)

        return water
    }
}