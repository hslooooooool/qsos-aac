package qsos.library.netservice.http

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.annotations.NonNull
import okhttp3.ResponseBody
import org.reactivestreams.Publisher

/**
 * @author : 华清松
 * @date : 2018/12/5
 * @description : 下载监听
 */
class DownLoadTransformer(
        // 默认保存地址
        private val mPath: String,
        // 文件名
        private val mFileName: String) : FlowableTransformer<ResponseBody, Any> {

    override fun apply(@NonNull upstream: Flowable<ResponseBody>): Publisher<Any> {
        return upstream.flatMap { responseBody ->
            val downLoadOnSubscribe = DownLoadOnSubscribe(responseBody, mPath, mFileName)
            return@flatMap Flowable.create(downLoadOnSubscribe, BackpressureStrategy.BUFFER)
        }
    }

}
