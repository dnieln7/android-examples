package xyz.dnieln7.bubble

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.updateLayoutParams
import androidx.core.widget.TextViewCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import xyz.dnieln7.bubble.databinding.BubbleBinding
import kotlin.math.roundToInt
import kotlin.random.Random

class BatteryBubbleService : Service() {

    private lateinit var bubbleBinding: BubbleBinding
    private lateinit var layoutParams: WindowManager.LayoutParams
    private lateinit var windowManager: WindowManager

    private var lastTap: Long = 0L

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        _isActive = true

        val layoutInflater = baseContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        bubbleBinding = BubbleBinding.inflate(layoutInflater)

        layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.CENTER
            x = 0
            y = 0
        }

        windowManager.addView(bubbleBinding.root, layoutParams)

        enableDraggable()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        coroutineScope.launch {
            while (isActive) {
                updateBubble(Random.nextInt(100))
                delay(500)
            }
        }

        val notificationsUtils = NotificationsUtils(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                BUBBLE_NOTIFICATION_ID,
                notificationsUtils.buildBubbleNotification(),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            )
        } else {
            startForeground(
                BUBBLE_NOTIFICATION_ID,
                notificationsUtils.buildBubbleNotification(),
            )
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        windowManager.removeView(bubbleBinding.root)
        _isActive = false
    }

    private fun updateBubble(level: Int) {
        val color = when {
            level <= 30 -> Color.RED
            level <= 80 -> Color.YELLOW
            else -> Color.GREEN
        }

        val height = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            level.toFloat(),
            resources.displayMetrics
        ).roundToInt()

        bubbleBinding.power.text = level.toString()

        TextViewCompat.setCompoundDrawableTintList(
            bubbleBinding.power,
            ColorStateList.valueOf(getColor(R.color.white))
        )

        bubbleBinding.power.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_power,
            0
        )

        bubbleBinding.base.setBackgroundColor(color)
        bubbleBinding.base.updateLayoutParams { this.height = height }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun enableDraggable() {
        bubbleBinding.root.setOnTouchListener(object : View.OnTouchListener {
            val updatedLayoutParams = layoutParams
            var x = 0.0
            var y = 0.0
            var px = 0.0
            var py = 0.0

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                event?.also {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            x = updatedLayoutParams.x.toDouble()
                            y = updatedLayoutParams.y.toDouble()

                            px = event.rawX.toDouble()
                            py = event.rawY.toDouble()
                        }

                        MotionEvent.ACTION_MOVE -> {
                            updatedLayoutParams.x = (x + event.rawX - px).toInt()
                            updatedLayoutParams.y = (y + event.rawY - py).toInt()

                            windowManager.updateViewLayout(bubbleBinding.root, updatedLayoutParams)
                        }

                        MotionEvent.ACTION_UP -> {
                            val currentTap = System.currentTimeMillis()

                            if ((currentTap - lastTap) in 100..200) {
                                Toast.makeText(
                                    applicationContext,
                                    "Double tap click",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }

                            lastTap = currentTap
                        }
                    }
                }

                return false
            }
        })
    }

    companion object {
        private var _isActive = false
        val isActive get() = _isActive
    }
}