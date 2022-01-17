package com.stebakov.homework

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.lang.Math.cos
import java.lang.Math.sin
import java.util.*

class Clock @JvmOverloads constructor(
    context: Context,
    private var attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var padding = 50
    private var handTruncation = 0
    private var hourHandTruncation = 0
    private var radius = 0
    private lateinit var paintCircle: Paint
    private lateinit var paintSecondHand: Paint
    private lateinit var paintMinuteHand: Paint
    private lateinit var paintHourHand: Paint
    private lateinit var textPaint: Paint
    private var isInit = false
    private var circleColor: Int = Color.WHITE
    private var secondHandColor: Int = Color.WHITE
    private var minuteHandColor: Int = Color.WHITE
    private var hourHandColor: Int = Color.WHITE
    private var circleStrokeWidth: Float = 5f
    private var numeralSpacing: Int = 50
    private var numeralsFontSize: Int = 20
    private var secondHandWidth: Float = 5f
    private var otherHandsWidth: Float = 5f
    private var numeralsFontWidth: Float = 5f
    private val arabicNumerals = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private var rect = Rect()

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Clock, 0, 0)
            circleColor = typedArray.getColor(R.styleable.Clock_circleColor, 0)
            secondHandColor = typedArray.getColor(R.styleable.Clock_secondHandColor, 0)
            minuteHandColor = typedArray.getColor(R.styleable.Clock_minuteHandColor, 0)
            hourHandColor = typedArray.getColor(R.styleable.Clock_hourHandColor, 0)
            circleStrokeWidth =
                typedArray.getFloat(R.styleable.Clock_circleStrokeWidth, 0f)
            numeralSpacing = typedArray.getInt(R.styleable.Clock_numeralSpacing, 0)
            numeralsFontSize = typedArray.getInt(R.styleable.Clock_numeralsFontSize, 0)
            secondHandWidth = typedArray.getFloat(R.styleable.Clock_secondHandWidth, 0f)
            otherHandsWidth = typedArray.getFloat(R.styleable.Clock_otherHandsWidth, 0f)
            numeralsFontWidth =
                typedArray.getFloat(R.styleable.Clock_numeralsFontWidth, 0f)

            typedArray.recycle()
        }
    }

    private fun initAnalogClock(attrs: AttributeSet) {
        numeralsFontSize =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                numeralsFontSize.toFloat(),
                resources.displayMetrics
            ).toInt()
        val min = height.coerceAtMost(width)
        radius = min / 2 - padding
        handTruncation = min / 8
        hourHandTruncation = min / 7

        paintCircle = Paint().apply {
            color = circleColor
            strokeWidth = circleStrokeWidth
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
        paintSecondHand = Paint().apply {
            color = secondHandColor
            style = Paint.Style.FILL
            strokeWidth = secondHandWidth
            isAntiAlias = true
        }
        paintMinuteHand = Paint().apply {
            color = minuteHandColor
            style = Paint.Style.FILL
            strokeWidth = otherHandsWidth
            isAntiAlias = true
        }
        paintHourHand = Paint().apply {
            color = hourHandColor
            style = Paint.Style.FILL
            strokeWidth = otherHandsWidth
            isAntiAlias = true
        }
        textPaint = Paint().apply {
            color = circleColor
            strokeWidth = numeralsFontWidth
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
        isInit = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (!isInit) {
            attrs?.let { initAnalogClock(it) }
        }
        drawCircle(canvas)
        drawHands(canvas)
        drawNumerals(canvas)
        drawCenter(canvas)
        invalidate()
    }

    private fun drawCenter(canvas: Canvas?) {
        canvas?.drawCircle(width / 2f, height / 2f, 20f, paintSecondHand)
    }

    private fun drawNumerals(canvas: Canvas?) {
        for (number in arabicNumerals) {
            val temp = number.toString()
            textPaint.getTextBounds(temp, 0, temp.length, rect)
            textPaint.textSize = numeralsFontSize.toFloat()
            val angle = Math.PI / 6 * (number - 3)
            val x =
                (width / 2 + kotlin.math.cos(angle) * (radius - numeralSpacing) - rect.width() / 2).toFloat()
            val y =
                (height / 2 + kotlin.math.sin(angle) * (radius - numeralSpacing) + rect.height() / 2).toFloat()
            canvas?.drawText(temp, x, y, textPaint)
        }
    }

    private fun drawHand(canvas: Canvas?, loc: Float, isHour: Boolean, isSecond: Boolean) {
        val angle = Math.PI * loc / 30 - Math.PI / 2
        val handRadius = if (isHour) {
            radius - handTruncation - hourHandTruncation
        } else {
            radius - handTruncation
        }
        when {
            isHour -> {
                canvas?.drawLine(
                    width / 2f,
                    height / 2f,
                    (width / 2 + cos(angle) * handRadius).toFloat(),
                    (height / 2 + sin(angle) * handRadius).toFloat(),
                    paintHourHand
                )
            }
            isSecond -> {
                canvas?.drawLine(
                    width / 2f,
                    height / 2f,
                    (width / 2 + cos(angle) * handRadius).toFloat(),
                    (height / 2 + sin(angle) * handRadius).toFloat(),
                    paintSecondHand
                )
            }
            else -> {
                canvas?.drawLine(
                    width / 2f,
                    height / 2f,
                    (width / 2 + cos(angle) * handRadius).toFloat(),
                    (height / 2 + sin(angle) * handRadius).toFloat(),
                    paintMinuteHand
                )
            }
        }
    }

    private fun drawHands(canvas: Canvas?) {
        val calendar = Calendar.getInstance()
        var hour = calendar.get(Calendar.HOUR_OF_DAY).toFloat()
        val minute = calendar.get(Calendar.MINUTE).toFloat()
        val second = calendar.get(Calendar.SECOND).toFloat()
        hour = if (hour > 12) hour - 12 else hour
        drawHand(canvas, (hour + minute / 60) * 5, true, isSecond = false)
        drawHand(canvas, (minute + second / 60), false, isSecond = false)
        drawHand(canvas, second, false, isSecond = true)
    }

    private fun drawCircle(canvas: Canvas?) {
        canvas?.drawCircle(width / 2f, height / 2f, radius.toFloat(), paintCircle)
    }
}