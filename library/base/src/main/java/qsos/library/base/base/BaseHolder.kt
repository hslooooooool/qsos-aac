package qsos.library.base.base

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import qsos.library.base.entity.BaseEntity

/**
 * @author : 华清松(姓名) 821034742@qq.com(邮箱) hslooooooool(微信)
 * @date : 2018/5/22 0022 18:37
 * @desc : 子项布局 Holder
 */
@SuppressLint("SetTextI18n")
abstract class BaseHolder<T : BaseEntity>(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
    private val t: T? = null
    private var mOnViewClickListener: OnViewClickListener? = null

    init {
        // 点击监听
        itemView.setOnClickListener(this)
    }

    /**设置数据 */
    abstract fun setData(data: T, position: Int)

    /** 释放资源 */
    open fun onRelease() {
        this.mOnViewClickListener = null
    }

    internal interface OnViewClickListener {
        /**子项点击监听 */
        fun onViewClick(view: View, position: Int)
    }

    internal fun setOnItemClickListener(listener: OnViewClickListener) {
        this.mOnViewClickListener = listener
    }

    private var mOnDeleteListener: OnDeleteListener? = null

    interface OnDeleteListener {
        fun delete(view: View, itemPosition: Int)
    }

    fun setDeleteListener(listener: OnDeleteListener): BaseHolder<T> {
        this.mOnDeleteListener = listener
        return this
    }

    override fun onLongClick(view: View): Boolean {
        if (mOnDeleteListener != null) {
            mOnDeleteListener!!.delete(view, adapterPosition)
        }
        return true
    }

    override fun onClick(view: View) {
        if (mOnViewClickListener != null) {
            mOnViewClickListener!!.onViewClick(view, adapterPosition)
        }
    }

}
