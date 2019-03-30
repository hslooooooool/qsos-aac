package qsos.library.netservice.http

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import qsos.library.base.BuildConfig
import qsos.library.base.base.BaseApplication
import qsos.library.netservice.http.interceptor.AddCookiesInterceptor
import qsos.library.netservice.http.interceptor.NetWorkInterceptor
import qsos.library.netservice.http.interceptor.ReceivedTokenInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：使用Retrofit+OkHttp搭建网路请求框架
 */
class ApiEngine private constructor(host: String) {
    private val retrofit: Retrofit
    private var receivedCookiesInterceptor = ReceivedTokenInterceptor(BaseApplication.appContext!!)
    private var addCookiesInterceptor = AddCookiesInterceptor(BaseApplication.appContext!!)

    // 链接超时时间设置 FIXME 正式环境修改时长
    private var outTime = if (BuildConfig.DEBUG) 20L else 8L

    private var client = OkHttpClient.Builder()
            .connectTimeout(outTime, TimeUnit.SECONDS)
            .writeTimeout(outTime, TimeUnit.SECONDS)
            // 网络拦截器
            .addNetworkInterceptor(NetWorkInterceptor())
            // 日志拦截器
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            // 植入 COOKIE 拦截器
            .addInterceptor(receivedCookiesInterceptor)
            // 获取 COOKIE 拦截器
            .addInterceptor(addCookiesInterceptor)
            // 将结果加入缓存
            //            .cache(cache)
            .build()

    /**设置JSON转换时间格式 */
    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

    init {
        retrofit = Retrofit.Builder()
                .baseUrl(host)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object {
        var isUnitTest = false
        @Volatile
        private var apiEngine: ApiEngine? = null

        fun getInstance(host: String): Retrofit {
            if (apiEngine == null) {
                synchronized(ApiEngine::class.java) {
                    if (apiEngine == null) {
                        apiEngine = ApiEngine(host)
                    }
                }
            } else {
                if (apiEngine!!.retrofit.baseUrl().host() == host) {
                    return apiEngine!!.retrofit
                } else {
                    synchronized(ApiEngine::class.java) {
                        apiEngine = ApiEngine(host)
                    }
                }
            }
            return apiEngine!!.retrofit
        }
    }
}
