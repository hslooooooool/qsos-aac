package qsos.library.netservice.http

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import java.io.IOException

/**
 * @author : 华清松
 * @date : 2018/10/24
 * @description : 扩展OkHttp的请求体，实现上传时的进度提示
 */
class FileRequestBody(
        /**实际请求体*/
        private val requestBody: RequestBody,
        /**上传进度监听*/
        private val callback: RetrofitCallback?) : RequestBody() {

    private var bufferedSink: BufferedSink? = null

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return requestBody.contentLength()
    }

    override fun contentType(): MediaType? {
        return requestBody.contentType()
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        if (bufferedSink == null) {
            // 包装
            bufferedSink = Okio.buffer(sink(sink))
        }
        // 写入
        requestBody.writeTo(bufferedSink!!)
        // 必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink!!.flush()
    }

    /**
     * 写入，回调进度接口
     *
     * @param sink Sink
     * @return Sink
     */
    private fun sink(sink: Sink): Sink {
        return object : ForwardingSink(sink) {
            // 当前写入字节数
            var bytesWritten = 0L
            // 总字节长度，避免多次调用contentLength()方法
            var contentLength = 0L

            @Throws(IOException::class)
            override fun write(source: Buffer, byteCount: Long) {
                super.write(source, byteCount)
                if (contentLength == 0L) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength()
                }
                // 增加当前写入的字节数
                bytesWritten += byteCount
                // 回调
                callback?.onLoading(contentLength, bytesWritten)
            }
        }
    }

    interface RetrofitCallback {
        fun onLoading(total: Long, progress: Long)
    }
}