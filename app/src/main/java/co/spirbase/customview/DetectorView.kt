package co.spirbase.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.max

class DetectorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paintBox = Paint()
    private var listRectBox = listOf<RectF>()
    private var scaleFactorX = 1f
    private var scaleFactorY = 1f

    init {
        paintBox.apply {
            color = Color.WHITE
            strokeWidth = 10f
            style = Paint.Style.STROKE
        }
    }

    fun setListBox(listRect: List<RectF>, frameWidth: Int, frameHeight: Int) {
        scaleFactorX = width.toFloat() / frameWidth
        scaleFactorY = height.toFloat() / frameHeight
        listRectBox = listRect
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (rectBox in listRectBox) {
            rectBox.apply {
                left *= scaleFactorX
                top *= scaleFactorY
                right *= scaleFactorX
                bottom *= scaleFactorY
            }
            canvas.drawRect(rectBox, paintBox)
        }
    }
}