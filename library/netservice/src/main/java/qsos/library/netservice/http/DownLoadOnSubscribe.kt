package qsos.library.netservice.http

import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.annotations.NonNull
import okhttp3.ResponseBody
import okio.*
import java.io.File
import java.io.IOException

/**
 * @author : 华清松
 * @date : 2018/12/5
 * @description : 下载监听
 */
class DownLoadOnSubscribe @Throws(IOException::class)
constructor(responseBody: ResponseBody, // 默认保存地址
            private val mPath: String, // 文件名
            private val mFileName: String) : FlowableOnSubscribe<Any> {

    private var mFlowableEmitter: FlowableEmitter<Any>? = null

    // 已上传
    private var mUploaded = 0L
    // 总长度
    private var mSumLength = 0L
    private var mPercent = 0

    private var mSource: Source? = null
    private var mProgressSource: Source? = null
    private var mSink: BufferedSink? = null

    init {
        init(responseBody)
    }

    override fun subscribe(@NonNull e: FlowableEmitter<Any>) {
        this.mFlowableEmitter = e
        try {
            mSink!!.writeAll(Okio.buffer(mProgressSource!!))
            mSink!!.close()
            mFlowableEmitter!!.onNext(mPath + mFileName)
            mFlowableEmitter!!.onComplete()
        } catch (exception: Exception) {
            mFlowableEmitter!!.onError(exception)
        }
    }


    @Throws(IOException::class)
    private fun init(responseBody: ResponseBody) {
        mSumLength = responseBody.contentLength()

        mSource = responseBody.source()

        mProgressSource = getProgressSource(mSource)

        mSink = Okio.buffer(Okio.sink(File(mPath + mFileName)))
    }

    private fun onProgress(percent: Int) {
        if (mFlowableEmitter == null) return
        if (percent == mPercent) return
        mPercent = percent
        if (percent >= 100) {
            mPercent = 100
            mFlowableEmitter!!.onNext(mPercent)
            return
        }
        mFlowableEmitter!!.onNext(mPercent)
    }

    private fun getProgressSource(source: Source?): ForwardingSource {
        return object : ForwardingSource(source!!) {
            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val read = super.read(sink, byteCount)
                onRead(read)
                return read
            }
        }
    }

    private fun onRead(read: Long) {
        mUploaded += if (read == -1L) 0L else read
        if (mSumLength <= 0) {
            onProgress(-1)
        } else {
            onProgress((100 * mUploaded / mSumLength).toInt())
        }
    }
}
