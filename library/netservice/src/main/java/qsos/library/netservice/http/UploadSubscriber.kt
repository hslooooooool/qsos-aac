package qsos.library.netservice.http

import android.content.Context
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import qsos.library.base.entity.Result

/**
 * @author : 华清松
 * @date : 2018/10/25 11:21
 * @description : 文件上传 观察者
 */
abstract class UploadSubscriber<T>(protected var mContext: Context) : Subscriber<Any> {

    private var mSubscription: Subscription? = null

    override fun onSubscribe(s: Subscription) {
        this.mSubscription = s
        mSubscription!!.request(1)
    }

    override fun onNext(o: Any) {
        if (o is Int) {
            progress(o)
        } else if (o is Result<*>) {
            if (o.isError()) {
                error(o.getErrorCode(), o.getMessage())
            } else {
                if (o.getResult() != null) {
                    next(o.getResult() as T)
                }
            }
        }
        mSubscription!!.request(1)
    }

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        error(100, "错误 ${e.message}")
    }

    protected abstract fun next(result: T)

    protected abstract fun progress(percent: Int?)

    protected abstract fun error(errorCode: Int, msg: String?)


}
