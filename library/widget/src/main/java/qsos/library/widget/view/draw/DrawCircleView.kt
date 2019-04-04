package qsos.library.widget.view.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import qsos.library.widget.R

/**
 * @author : 华清松
 * @date : 2019/4/4
 * @description :自定义圆形 View
 */
class DrawCircleView : View {

    private var mPaint: Paint? = null
    private var c = 10
    private var r = 10
    private var color = Color.RED

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(c.toFloat(), c.toFloat(), r.toFloat(), mPaint!!)
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val type = this.context.obtainStyledAttributes(attrs, R.styleable.DrawCircleView)
            color = type.getColor(R.styleable.DrawCircleView_color, Color.RED)
            c = type.getInt(R.styleable.DrawCircleView_c, 10)
            r = type.getInt(R.styleable.DrawCircleView_r, 10)
            type.recycle()
        }

        mPaint = Paint()
        mPaint!!.color = color
    }
}
