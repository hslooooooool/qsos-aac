package qsos.library.netservice.http.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import qsos.library.base.utils.db.SharedPreUtils
import timber.log.Timber
import java.io.IOException

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：添加 COOKIE 拦截器
 */
class AddCookiesInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val cookie = SharedPreUtils.getCookie(context)
        Timber.tag("网络拦截器").i("添加 cookie 进 HttpHeader cookie=$cookie")
        builder.addHeader("token", cookie)
        val userId = SharedPreUtils.getUserId(context)
        Timber.tag("网络拦截器").i("添加 userId 进 HttpHeader userId=$userId")
        builder.addHeader("user_id", userId)
        return chain.proceed(builder.build())
    }
}