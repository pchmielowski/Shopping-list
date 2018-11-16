package net.chmielowski.shoppinglist.view.addshop

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator
import androidx.core.content.ContextCompat
import net.chmielowski.shoppinglist.view.R


class ColorPicker(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var onClickListener: (Pair<Int, Int>) -> Unit = {}

    private var radius = 0.0f

    private var selectedRadius = 0.0f

    private val horizontalNumber = 8
    private val verticalNumber = 2

    private var space: Float = 0.0f
    val maxRadiusScale = 2

    val diameter: Float
        get() = 2 * radius

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = w.toFloat() / horizontalNumber / 4
        space = (w.toFloat() - ((horizontalNumber - 1) * diameter + maxRadiusScale * diameter)) /
                (horizontalNumber - 1)
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private val paint = Paint().also { it.isAntiAlias = true }

    override fun onDraw(canvas: Canvas) {
        for (y in 0 until verticalNumber) {
            for (x in 0 until horizontalNumber) {
                drawColorCircle(x, y, canvas)
            }
        }
    }

    private val darkPaint =
        Paint().apply { color = ContextCompat.getColor(context!!, R.color.grayDark); }

    private val saturationShift = 0.7f
    fun hue(color: Int) = color.toFloat() * 360 / horizontalNumber
    fun saturation(color: Int) = (color.toFloat() + saturationShift) / (verticalNumber.toFloat() + saturationShift)

    private fun drawColorCircle(x: Int, y: Int, canvas: Canvas) {
        paint.color = Color.HSVToColor(
            floatArrayOf(hue(x), saturation(y), 1.0f)
        )
        val _x = toPositionInPx(x)
        val _y = toPositionInPx(y)
        val _r = if (isSelected(x, y)) selectedRadius else radius
//        canvas.drawCircle(_x, _y, _r * 1.1f, darkPaint) // TODO: oval
        canvas.drawCircle(_x, _y, _r, paint)
    }

    fun margin() = radius // difference between max radius and min radius
    fun toPositionInPx(n: Int) = margin() + n * (space + 2 * radius) + radius
    fun fromPositionInPx(px: Float) = ((px - radius) / (space + 2 * radius)).toInt()

    private var selected: Pair<Int, Int>? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            (fromPositionInPx(event.x) to fromPositionInPx(event.y)).let {
                selected = it
                onClickListener(it)
            }
            ValueAnimator.ofFloat(radius, maxRadiusScale * radius)
                .run {
                    interpolator = BounceInterpolator()
                    addUpdateListener {
                        selectedRadius = it.animatedValue as Float
                        invalidate()
                    }
                    start()
                }
            return true
        }
        return false
    }

    private fun isSelected(x: Int, y: Int) = selected?.let { it.first == x && it.second == y } ?: false
}