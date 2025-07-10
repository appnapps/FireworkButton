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

enum class Shape {
    CIRCLE, STAR, HEART
}


class FireworkButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

    private val particles = mutableListOf<Particle>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private var extraClick: (() -> Unit)? = null

    private var particleShape: Shape = Shape.CIRCLE
    private var particleSize: Float = 12f
    private var animationDuration: Long = 1600L

    fun setParticleShape(shape: Shape) {
        this.particleShape = shape
    }

    fun setParticleSize(size: Float) {
        particleSize = size
    }

    fun setAnimationDuration(durationMillis: Long) {
        animationDuration = durationMillis
    }

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
                Particle(centerX, centerY, dx, dy, color, particleShape)
            )
        }

        val animatorSet = AnimatorSet()
        val animators = particles.map { p ->
            ObjectAnimator.ofFloat(p, "progress", 0f, 1f).apply {
                duration = animationDuration
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

            val cx = p.x + p.dx * p.progress
            val cy = p.y + p.dy * p.progress
            val size = max(12f, particleSize * (1 - p.progress))

            when (p.shape) {
                Shape.CIRCLE -> {
                    canvas.drawCircle(cx, cy, size, paint)
                }
                Shape.STAR -> {
                    drawStar(canvas, cx, cy, size, paint)
                }
                Shape.HEART -> {
                    drawHeart(canvas, cx, cy, size, paint)
                }
            }
        }
    }

    private fun drawStar(canvas: Canvas, cx: Float, cy: Float, size: Float, paint: Paint) {
        val path = android.graphics.Path()
        val spikes = 5
        val outerRadius = size
        val innerRadius = size / 2.5f
        val angle = Math.PI / spikes

        path.reset()
        for (i in 0 until spikes * 2) {
            val r = if (i % 2 == 0) outerRadius else innerRadius
            val theta = i * angle
            val x = (cx + r * cos(theta)).toFloat()
            val y = (cy + r * sin(theta)).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawHeart(canvas: Canvas, cx: Float, cy: Float, size: Float, paint: Paint) {
        val path = android.graphics.Path()
        val half = size / 2
        path.moveTo(cx, cy + half)
        path.cubicTo(cx + size, cy - half, cx + half, cy - size, cx, cy - half)
        path.cubicTo(cx - half, cy - size, cx - size, cy - half, cx, cy + half)
        path.close()
        canvas.drawPath(path, paint)
    }


    class Particle(
        val x: Float,
        val y: Float,
        val dx: Float,
        val dy: Float,
        val color: Int,
        val shape: Shape
    ) {
        var progress: Float = 0f
    }
}
