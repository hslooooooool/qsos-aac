package qsos.library.netservice.http.upload

import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.annotations.NonNull

/**
 * @author : 华清松
 * @date : 2018/12/5
 * @description : 上传监听
 */
class UploadOnSubscribe(sumLength: Long) : FlowableOnSubscribe<Int> {

    private var mObservableEmitter: FlowableEmitter<Int>? = null
    private var mSumLength = sumLength
    private var uploaded = 0L

    fun onRead(read: Long) {
        uploaded += read
        if (mSumLength <= 0) {
            onProgress(-1)
        } else {
            onProgress((100 * uploaded / mSumLength).toInt())
        }
    }

    private fun onProgress(percent: Int) {
        if (mObservableEmitter == null) return
        if (percent >= 100) {
            mObservableEmitter!!.onNext(100)
            mObservableEmitter!!.onComplete()
            return
        }
        mObservableEmitter!!.onNext(percent)
    }

    @Throws(Exception::class)
    override fun subscribe(@NonNull e: FlowableEmitter<Int>) {
        this.mObservableEmitter = e
    }

}
