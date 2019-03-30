package qsos.library.base.utils

import timber.log.Timber

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：日志打印工具
 */
object LogUtil {

    var isOpen = true
    var TAG = "系统日志"

    fun e(msg: String) {
        if (isOpen) {
            Timber.e(msg)
        }
    }

    fun w(msg: String) {
        if (isOpen) {
            Timber.w(TAG, msg)
        }
    }

    fun i(msg: String) {
        if (isOpen) {
            Timber.i(TAG, msg)
        }
    }

    fun d(msg: String) {
        if (isOpen) {
            Timber.d(TAG, msg)
        }
    }

    fun v(msg: String) {
        if (isOpen) {
            Timber.v(TAG, msg)
        }
    }
}
