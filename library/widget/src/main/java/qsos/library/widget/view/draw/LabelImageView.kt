package qsos.library.widget.view.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import qsos.library.widget.R

/**
 * @author : 华清松
 * @date : 2019/4/4
 * @description : 自定义图片角标 View
 */
class LabelImageView : AppCompatImageView {
    /**
     * 标签文字画笔
     */
    private var mTextPaint: Paint? = null
    /**
     * 标签背景画笔
     */
    private var mBackgroundPaint: Paint? = null
    /**
     * 标签文字绘制路线
     */
    private var mPathText: Path? = null
    /**
     * 标签背景绘制路线
     */
    private var mPathBackground: Path? = null
    /**
     * 梯形的宽度
     */
    private var a = 100
    /**
     * 梯形到右上角的距离
     */
    private var b = 50
    /**
     * 标签文字
     */
    private var mText: String? = "HOT"
    /**
     * 标签文字大小
     */
    private var mTextSize = 12f
    /**
     * 标签文字颜色
     */
    private var mTextColor = Color.BLACK
    /**
     * 标签背景颜色
     */
    private var mBackgroundColor = Color.RED

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 计算标签绘制路径
        calculatePath(measuredWidth)
        // 绘制标签背景
        canvas.drawPath(mPathBackground!!, mBackgroundPaint!!)
        // 绘制标签文字
        canvas.drawTextOnPath(mText!!, mPathText!!, 70f, -20f, mTextPaint!!)
    }

    /**
     * ----------------------| a |  b |
     * 计算路径              x1    x2
     * ................................ measuredWidth  a=x2-x2=y2-y1 b=y1
     * .                      .    .  .
     * .                        .    ..
     * .                          .   . y1
     * .                            . .
     * .                              . y2
     * .                              .
     * ................................ measuredHeight
     */
    private fun calculatePath(measuredWidth: Int) {


        val x1 = (measuredWidth - a - b).toFloat()
        val x2 = (measuredWidth - b).toFloat()
        val y1 = b.toFloat()
        val y2 = (b + a).toFloat()

        // 绘制文字的路线
        mPathText!!.reset()
        mPathText!!.moveTo(x1, 0f)
        mPathText!!.lineTo(measuredWidth.toFloat(), y2)
        mPathText!!.close()

        // 绘制背景的路线
        mPathBackground!!.reset()
        mPathBackground!!.moveTo(x1, 0f)
        mPathBackground!!.lineTo(x2, 0f)
        mPathBackground!!.lineTo(measuredWidth.toFloat(), y1)
        mPathBackground!!.lineTo(measuredWidth.toFloat(), y2)
        mPathBackground!!.close()
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val type = this.context.obtainStyledAttributes(attrs, R.styleable.LabelImageView)
            mText = type.getString(R.styleable.LabelImageView_text)
            mTextSize = type.getDimension(R.styleable.LabelImageView_textSize, 16f)
            mTextColor = type.getColor(R.styleable.LabelImageView_textColor, Color.WHITE)
            mBackgroundColor = type.getColor(R.styleable.LabelImageView_backgroundColor, Color.RED)
            a = type.getInt(R.styleable.LabelImageView_a, 100)
            b = type.getInt(R.styleable.LabelImageView_b, 50)
            type.recycle()
        }

        // 初始化路径
        mPathText = Path()
        mPathBackground = Path()

        // 初始化画笔
        mTextPaint = Paint()
        mTextPaint!!.textSize = mTextSize
        mTextPaint!!.color = mTextColor

        mBackgroundPaint = Paint()
        mBackgroundPaint!!.color = mBackgroundColor
        mBackgroundPaint!!.style = Paint.Style.FILL
    }
}
