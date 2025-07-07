package com.appnapps.fireworkbutton

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin
import kotlin.random.Random

class FireworkButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

    private val particles = mutableListOf<Particle>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private var extraClick: (() -> Unit)? = null

    fun setOnFireworkClickListener(listener: () -> Unit) {
        extraClick = listener
    }

    init {
        super.setOnClickListener {
            if (width > 0 && height > 0) {
                triggerFireworks()
                extraClick?.invoke()
            } else {
                Log.w("FireworkButton", "View size is not ready: width=$width height=$height")
            }
        }
    }

    private fun triggerFireworks() {
        particles.clear()

        val centerX = width / 2f
        val centerY = height / 2f
        val count = 20
        val radius = 150f

        for (i in 0 until count) {
            val angle = i * (2 * Math.PI / count)
            val dx = (cos(angle) * radius).toFloat()
            val dy = (sin(angle) * radius).toFloat()
            val color = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))

            particles.add(
                Particle(centerX, centerY, dx, dy, color)
            )
        }

        val animatorSet = AnimatorSet()
        val animators = particles.map { p ->
            ObjectAnimator.ofFloat(p, "progress", 0f, 1f).apply {
                duration = 800
                addUpdateListener { invalidate() }
            }
        }

        animatorSet.playTogether(animators)
        animatorSet.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (p in particles) {
            val alpha = ((1f - p.progress) * 255).toInt().coerceIn(0, 255)
            paint.color = p.color
            paint.alpha = alpha
            canvas.drawCircle(
                p.x + p.dx * p.progress,
                p.y + p.dy * p.progress,
                max(4f, 12f * (1 - p.progress)),
                paint
            )
        }
    }

    class Particle(
        val x: Float,
        val y: Float,
        val dx: Float,
        val dy: Float,
        val color: Int
    ) {
        var progress: Float = 0f
    }
}
