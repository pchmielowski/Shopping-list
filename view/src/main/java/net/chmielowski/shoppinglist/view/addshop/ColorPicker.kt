package net.chmielowski.shoppinglist.view.addshop

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ColorPicker(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var w: Int = 0
    private var h: Int = 0
    private val r = 40.0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        this.w = w
        this.h = h
        super.onSizeChanged(w, h, oldw, oldh)
        invalidate()
    }

    val paint = Paint().also { it.color = Color.RED }

    override fun onDraw(canvas: Canvas) {
        val horizontalNumber = 8
        val verticalNumber = 3
        val space = w.toFloat() / horizontalNumber
        val margin = (w - space * (horizontalNumber)) / 2 // TODO
        val saturationShift = 1
        for (y in 0 until verticalNumber) {
            for (x in 0 until horizontalNumber) {
                paint.color = Color.HSVToColor(
                    floatArrayOf(
                        x.toFloat() * 360 / horizontalNumber,
                        (y + saturationShift).toFloat() / (verticalNumber + saturationShift),
                        1.0f
                    )
                )
                canvas.drawCircle(margin + x.toFloat() * space + r, y.toFloat() * space + r, r, paint)
            }
        }
    }
}