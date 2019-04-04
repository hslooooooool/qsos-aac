package qsos.library.widget.view.wave

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * @author : 华清松
 * @date : 2019/4/4
 * @description : 上涨的水波 View
 */
class WaveView : View {

    companion object {
        /**
         * 波浪从屏幕外开始，在屏幕外结束，这样效果更真实
         */
        private const val EXTRA_DISTANCE = 200f
    }

    private var mPath: Path? = null
    private var mPaint: Paint? = null
    /**
     * 控件宽高
     */
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    /**
     * 控制点坐标
     */
    private var mControlX: Float = 0.toFloat()
    private var mControlY: Float = 0.toFloat()
    /**
     * 波浪峰值
     */
    private var mWaveY: Float = 0.toFloat()
    /**
     * 是否移动控制点
     */
    private var mMoveControl = true

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        mControlY = (mHeight - (mHeight shr 3)).toFloat()
        mWaveY = (mHeight - (mHeight shr 5)).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPath!!.moveTo(-EXTRA_DISTANCE, mWaveY)
        mPath!!.quadTo(mControlX, mControlY, mWidth + EXTRA_DISTANCE, mWaveY)
        mPath!!.lineTo(mWidth.toFloat(), mHeight.toFloat())
        mPath!!.lineTo(0f, mHeight.toFloat())
        mPath!!.close()
        canvas.drawPath(mPath!!, mPaint!!)

        // 形成波浪
        if (mControlX <= -EXTRA_DISTANCE) {
            mMoveControl = true
        } else if (mControlX >= mWidth + EXTRA_DISTANCE) {
            mMoveControl = false
        }
        mControlX = if (mMoveControl) mControlX + 20 else mControlX - 20

        // 水面上升
        if (mControlY >= 0) {
            mControlY -= 2f
            mWaveY -= 2f
        }
        mPath!!.reset()

        invalidate()
    }

    private fun init(attrs: AttributeSet?) {
        mPath = Path()
        mPaint = Paint()
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        mPaint!!.color = Color.LTGRAY

        if (attrs != null) {

        }
    }
}