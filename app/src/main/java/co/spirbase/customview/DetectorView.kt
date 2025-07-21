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
import org.tensorflow.lite.task.vision.detector.Detection
import kotlin.math.max

class DetectorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paintBox = Paint()
    private val textPaint = Paint()

    private var scaleFactor = 1f
    private var listDetection = listOf<Detection>()

    init {
        paintBox.apply {
            color = Color.WHITE
            strokeWidth = 10f
            style = Paint.Style.STROKE
            isAntiAlias = true
        }

        textPaint.apply {
            color = Color.WHITE
            textSize = 48f
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }

    fun setListDetection(
        listDetection: List<Detection>,
        frameWidth: Int,
        frameHeight: Int
    ) {
        scaleFactor = max(width.toFloat() / frameWidth, height.toFloat() / frameHeight)
        this.listDetection = listDetection
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBox(canvas)
        drawText(canvas)
    }

    private fun drawBox(canvas: Canvas) {
        for (detection in listDetection) {
            val boundingBox = detection.boundingBox.apply {
                left *= scaleFactor
                top *= scaleFactor
                right *= scaleFactor
                bottom *= scaleFactor
            }
            canvas.drawRect(boundingBox, paintBox)
        }
    }

    private fun drawText(canvas: Canvas) {
        for (detection in listDetection) {
            val text = detection.categories.firstOrNull()?.let {
                it.label
//                it.label + it.score
            }

            canvas.drawText(
                text ?: "Unknown",
                detection.boundingBox.left,
                detection.boundingBox.top,
                textPaint
            )
        }
    }
}