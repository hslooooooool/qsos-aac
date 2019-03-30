package qsos.library.widget.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import qsos.library.base.callback.OnTListener
import qsos.library.widget.R

/**
 * @author : 华清松
 * @date : 2018/9/13 17:58
 * @description : PopupWindow 工具类
 */
@SuppressLint("InflateParams", "SetTextI18n")
object PopupWindowUtils {

    private var textOkWindow: PopupWindow? = null

    /**通用确认弹窗*/
    fun showTextOk(mContext: Context, text: String?, listener: OnTListener<Boolean>?): PopupWindow {
        var tv: TextView? = null
        var btnOk: Button? = null
        var contentView: View? = null
        if (textOkWindow == null) {
            contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_text_ok, null, false)
            tv = contentView!!.findViewById(R.id.tv_pop_ok)
            btnOk = contentView.findViewById(R.id.btn_pop_ok_sure)
            tv!!.freezesText = true
            tv.text = text ?: ""
            textOkWindow = PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true)
            textOkWindow!!.setBackgroundDrawable(ColorDrawable(Color.argb(50, 195, 194, 193)))
            textOkWindow!!.isOutsideTouchable = true
            textOkWindow!!.isTouchable = true
            textOkWindow!!.showAtLocation(contentView, Gravity.CENTER, 0, 0)
        } else {
            tv?.text = text ?: ""
            if (textOkWindow!!.isShowing) {
                textOkWindow!!.update()
            } else {
                textOkWindow!!.showAtLocation(contentView, Gravity.CENTER, 0, 0)
            }
        }
        btnOk!!.setOnClickListener {
            textOkWindow!!.dismiss()
            listener?.getItem(true)
            textOkWindow = null
        }
        return textOkWindow!!
    }

}
