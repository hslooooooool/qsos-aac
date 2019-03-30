package qsos.library.base.base

import android.content.Context
import qsos.library.base.entity.http.ApiException

/**
 * @author : 华清松
 * @date : 2019/3/30
 * @description : View 接口
 */
interface BaseView {
    /**默认界面ID*/
    val defLayoutId: Int

    /**获取上下文*/
    val mContext: Context?

    /** UI 是否存活*/
    val isActive: Boolean

    /**显示消息*/
    fun showToast(msg: String?)

    /*网络请求错误友好提示*/
    fun showHttpError(httpException: ApiException)

    /**获取当前时间毫秒数*/
    fun getNowTime(): Long

    /**消息弹窗*/
    fun showDialog(msg: String?)

    /**显示加载进度*/
    fun showProgress(msg: String)

    /**关闭加载进度*/
    fun closeProgress()

    /**结束当前页面*/
    fun finishThis()

    enum class STATE(key: String) {
        NOT_NET("没有网络"),
        LOADING("加载中"),
        NET_ERROR("服务器错误"),
        NOT_FOUND("接口错误"),
        RESULT_NULL("没有数据"),
        OK("正常");

        val state = key
    }


}