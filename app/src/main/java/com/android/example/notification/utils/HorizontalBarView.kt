package com.android.example.notification.utils

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class HorizontalBarView : View {
    constructor(context: Context?) : super(context) {
        initPaint()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initPaint()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initPaint()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initPaint()
    }

    //-------------絵筆に関連-------------
    //Base Barの絵筆
    private var baseBarPaint: Paint? = null

    //進捗の絵筆
    private var currentBarPaint: Paint? = null

    private fun initPaint(){
        //Base Barの絵筆の初期化
        baseBarPaint = Paint()
        baseBarPaint?.color = Color.parseColor("#919191")
        baseBarPaint?.isAntiAlias = true
        baseBarPaint?.strokeWidth = 2f
        baseBarPaint?.style = Paint.Style.STROKE
        //進捗の絵筆の初期化
        currentBarPaint = Paint()
        currentBarPaint?.color = Color.parseColor("#FF018786")
        currentBarPaint?.isAntiAlias = true
        currentBarPaint?.strokeWidth = 1f
        currentBarPaint?.style = Paint.Style.FILL
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val rect = RectF(0f,0f,width.toFloat(),100f)
//        val path = Path()
        baseBarPaint?.let { canvas.drawRect(rect, it) }
//        baseBarPaint?.let { path.addRoundRect(rect,floatArrayOf(24f, 24f, 24f, 24f, 24f, 0f, 0f, 0f),Path.Direction.CCW) }
//        baseBarPaint?.let { canvas.drawPath(path, it) }
        currentBarPaint?.let{canvas.drawRect(0f,0f,200f*animProgress/100,100f, it)}

    }

    private var animProgress = 0
    private var mAnimtor: ValueAnimator? = null

    fun startAnim() {
        if (mAnimtor == null) {
            mAnimtor = ValueAnimator.ofInt(0, 100)
            mAnimtor?.duration = 800
            mAnimtor?.addUpdateListener { valueAnimator ->
                animProgress = valueAnimator.animatedValue as Int
                postInvalidate()
            }
        }
        mAnimtor!!.start()
    }
    private var currentPosition = 0f
    fun setPaintValue(currentValue:Float,totalValue:Float){
        currentPosition = (currentValue/totalValue)*width
    }

}