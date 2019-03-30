package qsos.library.base.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：网络链接判断工具
 */
object NetUtil {

    /**判断网络是否连接*/
    fun isConnected(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.activeNetworkInfo
        if (null != info && info.isConnected) {
            if (info.detailedState == NetworkInfo.DetailedState.CONNECTED) {
                return true
            }
        }
        return false
    }

    /**判断是否是wifi连接*/
    fun isWifi(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo.subtype == 1
    }

}
