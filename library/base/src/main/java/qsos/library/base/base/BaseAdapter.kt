package qsos.library.base.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import qsos.library.base.entity.BaseEntity

/**
 * @author : 华清松(姓名) 821034742@qq.com(邮箱) hslooooooool(微信)
 * @date : 2018/5/22 0022
 * @desc : BaseAdapter
 */
abstract class BaseAdapter<T : BaseEntity>(var data: ArrayList<T>) : RecyclerView.Adapter<BaseHolder<T>>(), BaseHolder.OnViewClickListener {
    var mOnItemClickListener: OnRecyclerViewItemClickListener<T>? = null

    private var mHolder: BaseHolder<T>? = null
    protected var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<T> {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(getLayoutId(viewType), parent, false)
        mHolder = getHolder(view, viewType)
        // 设置Item点击事件
        mHolder!!.setOnItemClickListener(this)
        return mHolder as BaseHolder<T>
    }

    /**绑定数据*/
    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int) {
        holder.setData(data[position], position)
    }

    /**返回数据个数*/
    override fun getItemCount(): Int {
        return data.size
    }

    /**获得某个 position 上的 item 的数据*/
    fun getItem(position: Int): T? {
        return data[position]
    }

    /**获得 data 的数据*/
    abstract fun getArrayList(): ArrayList<T>

    /**让子类实现用以提供 BaseHolder */
    abstract fun getHolder(view: View, viewType: Int): BaseHolder<T>

    /**提供用于 item 布局的 layoutId */
    abstract fun getLayoutId(viewType: Int): Int

    /**设置点击监听*/
    fun setOnItemClickListener(listener: OnRecyclerViewItemClickListener<T>) {
        this.mOnItemClickListener = listener
    }

    interface OnRecyclerViewItemClickListener<T> {
        /**子项点击监听*/
        fun onItemClick(view: View, viewType: Int, data: T, position: Int)
    }

    companion object {
        /**遍历所有 BaseHolder,释放他们需要释放的资源*/
        fun releaseAllHolder(recyclerView: RecyclerView?) {
            if (recyclerView == null) {
                return
            }
            (recyclerView.childCount - 1 downTo 0).forEach { i ->
                val view = recyclerView.getChildAt(i)
                val viewHolder = recyclerView.getChildViewHolder(view)
                if (viewHolder != null && viewHolder is BaseHolder<*>) {
                    viewHolder.onRelease()
                }
            }
        }
    }
}
