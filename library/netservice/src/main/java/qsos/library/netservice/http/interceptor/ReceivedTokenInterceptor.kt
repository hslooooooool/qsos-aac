package qsos.library.netservice.http.interceptor

import android.content.Context
import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Response
import qsos.library.base.utils.db.SharedPreUtils
import timber.log.Timber
import java.io.IOException

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：保存 TOKEN 拦截器
 */
class ReceivedTokenInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        Timber.tag("网络拦截器").i("获取请求返回的 header 并保存")
        val originalResponse = chain.proceed(chain.request())
        // 获取请求返回的 Token 并保存
        val cookie = originalResponse.header("token")
        if (!TextUtils.isEmpty(cookie)) {
            Timber.tag("网络拦截器").i("保存 cookie 进 HttpHeader COOKIE= $cookie")
            SharedPreUtils.saveCookie(context, cookie!!)
        } else {
            Timber.tag("网络拦截器").i("服务器没有返回 cookie")
        }
        // 获取用户 ID 并保存
        val userId = originalResponse.header("user_id")
        if (!TextUtils.isEmpty(userId)) {
            Timber.tag("网络拦截器").i("保存 userId 进 HttpHeader userId= $userId")
            SharedPreUtils.saveUserId(context, userId!!)
        } else {
            Timber.tag("网络拦截器").i("服务器没有返回 userId")
        }
        return originalResponse
    }
}