package qsos.library.netservice.http

import android.net.ParseException
import com.google.gson.JsonParseException
import org.json.JSONException
import qsos.library.base.entity.http.ApiException
import qsos.library.base.entity.http.ErrorCode
import qsos.library.base.entity.http.ServerException
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：统一处理异常类
 */
object ApiExceptionServiceImpl {

    fun handleException(e: Throwable): ApiException {
        val ex: ApiException
        if (e is HttpException) {
            // HTTP错误
            when (e.code()) {
                ErrorCode.UNAUTHORIZED.value -> {
                    ex = ApiException(e, ErrorCode.UNAUTHORIZED)
                    ex.displayMessage = "登录失效，请重新登录"
                }
                ErrorCode.NOT_FOUND.value -> {
                    ex = ApiException(e, ErrorCode.NOT_FOUND)
                    ex.displayMessage = "访问地址错误"
                }
                else -> {
                    // 均视为网络错误
                    ex = ApiException(e, ErrorCode.HTTP_ERROR)
                    ex.displayMessage = "网络错误"
                }
            }
        } else if (e is ServerException) {
            // 服务器定义的错误
            when (e.code) {
                ErrorCode.UNAUTHORIZED.value -> {
                    ex = ApiException(e, ErrorCode.UNAUTHORIZED)
                    ex.displayMessage = "登录失效，请重新登录"
                }
                ErrorCode.NOT_FOUND.value -> {
                    ex = ApiException(e, ErrorCode.NOT_FOUND)
                    ex.displayMessage = "访问地址错误"
                }
                ErrorCode.RESULT_NULL.value -> {
                    ex = ApiException(e, ErrorCode.RESULT_NULL)
                    ex.displayMessage = "未查询到数据"
                }
                else -> {
                    // 均视为服务器异常
                    ex = ApiException(e, ErrorCode.HTTP_ERROR)
                    ex.displayMessage = e.msg ?: "服务器异常"
                }
            }
            Timber.tag("服务器异常").e(e.msg)
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException) {
            // 均视为解析错误
            ex = ApiException(e, ErrorCode.PARSE_ERROR)
            ex.displayMessage = "数据解析错误"
            Timber.tag("数据解析异常").e("$e")
        } else if (e is ConnectException) {
            // 均视为网络错误
            ex = ApiException(e, ErrorCode.NETWORK_ERROR)
            ex.displayMessage = "网络连接失败"
            Timber.tag("网络连接失败").e("$e")
        } else if (e is SocketTimeoutException) {
            // 访问超时
            ex = ApiException(e, ErrorCode.BACK_ERROR)
            ex.displayMessage = "服务器超时"
            Timber.tag("服务器超时").e("$e")
        } else if (e is NullPointerException) {
            // 接口不存在
            ex = ApiException(e, ErrorCode.NETWORK_ERROR)
            ex.displayMessage = "空指针异常"
            Timber.tag("空指针异常，访问路径对么？").e("$e")
        } else {
            // 未知错误
            ex = ApiException(e, ErrorCode.UNKNOWN)
            ex.displayMessage = "请求失败"
            Timber.tag("未知异常").e("$e")
        }
        return ex
    }
}
