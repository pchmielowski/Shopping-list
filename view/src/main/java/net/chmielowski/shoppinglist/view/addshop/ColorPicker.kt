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
import android.view.animation.OvershootInterpolator


class ColorPicker(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var onClickListener: ((Pair<Int, Int>) -> Unit)? = null

    private var w: Int = 0
    private var h: Int = 0
    private var r = 0.0f

    private var selectedRadius = 0.0f;

    private val horizontalNumber = 8
    private val verticalNumber = 2

    private var space: Float = 0.0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        this.w = w
        this.h = h
        space = w.toFloat() / horizontalNumber
        r = space / 4
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

    val darkPaint = Paint().also { it.color = Color.BLACK } // TODO: read color from res

    private val saturationShift = 0.7f
    fun hue(color: Int) = color.toFloat() * 360 / horizontalNumber
    fun saturation(color: Int) = (color.toFloat() + saturationShift) / (verticalNumber.toFloat() + saturationShift)

    private fun drawColorCircle(x: Int, y: Int, canvas: Canvas) {
        val margin = (w - space * (horizontalNumber)) / 2 // TODO
        paint.color = Color.HSVToColor(
            floatArrayOf(hue(x), saturation(y), 1.0f)
        )
        val _x = margin + x.toFloat() * space + r
        val _y = y.toFloat() * space + r
        val _r = if (isSelected(x, y)) selectedRadius else r
        canvas.drawCircle(_x, _y, _r * 1.1f, darkPaint) // TODO: oval
        canvas.drawCircle(_x, _y, _r, paint)
    }

    private var selected: Pair<Int, Int>? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            ((event.x / space).toInt() to (event.y / space).toInt()).let {
                selected = it
                onClickListener?.invoke(it)
            }
            ValueAnimator.ofFloat(r, 2 * r)
                .run {
                    interpolator = OvershootInterpolator()
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