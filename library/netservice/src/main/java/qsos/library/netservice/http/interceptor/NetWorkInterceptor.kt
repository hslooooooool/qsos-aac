package qsos.library.netservice.http.interceptor

import android.os.Environment
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import qsos.library.base.base.BaseApplication
import qsos.library.base.utils.NetUtil
import timber.log.Timber
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：网络请求前判断联网，进行数据获取模式选择的拦截器
 */
class NetWorkInterceptor : Interceptor {

    /**
     * 用来存储设备信息和异常信息
     */
    private val infos = HashMap<String, String>()
    /**
     * 用于格式化日期,作为日志文件名的一部分
     */
    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // 无网络时强制使用缓存
        if (!NetUtil.isConnected(BaseApplication.appContext!!)) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }
        val response = chain.proceed(request)

        if (NetUtil.isConnected(BaseApplication.appContext!!)) {
            // 有网络时，设置超时为12
            val maxStale = 12
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxStale")
                    // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .removeHeader("Pragma")
                    .build()
        } else {
            // 无网络时，设置超时为3周
            val maxStale = 60 * 60 * 24 * 21
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .removeHeader("Pragma")
                    .build()
        }
        return response
    }

    /**
     * 保存错误信息到文件中
     */
    @Throws(Exception::class)
    private fun saveCrashInfoFile(url: String): String? {
        val sb = StringBuffer()
        try {
            val sDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
            val date = sDateFormat.format(Date())
            sb.append("\r\n" + date + "\n")
            for ((key, value) in infos) {
                sb.append("$key=$value\n")
            }

            val writer = StringWriter()
            val printWriter = PrintWriter(writer)
            printWriter.append("\n" + url + "\n")
            printWriter.flush()
            printWriter.close()
            val result = writer.toString()
            sb.append(result)
            return writeFile(sb.toString())
        } catch (e: Exception) {
            Timber.e(e, "an error occured while writing file...")
            sb.append("an error occured while writing file...\r\n")
            writeFile(sb.toString())
        }

        return null
    }

    @Throws(Exception::class)
    private fun writeFile(sb: String): String {
        val time = formatter.format(Date())
        val fileName = "crash-$time.txt"
        val path = globalPath
        val dir = File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }

        val fos = FileOutputStream(path + fileName, true)
        fos.write(sb.toByteArray())
        fos.flush()
        fos.close()
        return fileName
    }

    companion object {
        /**获取缓存路径*/
        val globalPath: String = Environment.getExternalStorageDirectory().absolutePath + File.separator + "crash" + File.separator
    }
}
