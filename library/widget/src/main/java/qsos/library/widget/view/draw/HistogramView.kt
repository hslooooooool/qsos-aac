package qsos.library.widget.view.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import qsos.library.widget.R

/**
 * @author : 华清松
 * @date : 2019/4/4
 * @description : 自定义柱状图 View
 */
class HistogramView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mPaint: Paint? = null
    private var mCount: Int = 0
    private var mWidth: Int = 0
    private var mRectHeight: Int = 0
    private var mRectWidth: Int = 0

    init {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.color = Color.GREEN
        mPaint!!.style = Paint.Style.FILL
        val ta = context.obtainStyledAttributes(attrs, R.styleable.HistogramView)
        if (ta != null) {
            mCount = ta.getInt(R.styleable.HistogramView_count, 6)
            ta.recycle()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = measuredWidth
        mRectHeight = measuredHeight
        mRectWidth = (mWidth * 0.6 / mCount).toInt()
        val mLinearGradient = LinearGradient(0f, 0f, mRectWidth.toFloat(), mRectHeight.toFloat(), Color.BLACK, Color.GRAY, Shader.TileMode.CLAMP)
        mPaint!!.shader = mLinearGradient

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until mCount) {
            val mRandom = Math.random()
            val mCurrentHeight = (mRectHeight * mRandom).toFloat()
            val width = (mWidth * 0.4 / 2 + OFFSET).toFloat()
            canvas.drawRect(width + i * mRectWidth, mCurrentHeight, width + (i + 1) * mRectWidth, mRectHeight.toFloat(), mPaint!!)
        }
        postInvalidateDelayed(300)
    }

    companion object {
        const val OFFSET = 5
    }
}
