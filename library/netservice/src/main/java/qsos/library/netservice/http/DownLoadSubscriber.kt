package qsos.library.netservice.http

import android.content.Context

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * @author : 华清松
 * @date : 2018/10/25
 * @description : 下载 观察者
 */
abstract class DownLoadSubscriber(protected var mContext: Context) : Subscriber<Any> {

    private var mSubscription: Subscription? = null

    override fun onSubscribe(s: Subscription) {
        this.mSubscription = s
        mSubscription!!.request(1)
    }

    override fun onComplete() {

    }

    override fun onNext(o: Any) {
        if (o is Int) {
            progress(o)
        }

        if (o is String) {
            next(o)
        }
        mSubscription!!.request(1)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        //todo 错误统一处理
        error(100, "错误 ${e.message}")
    }

    protected abstract fun next(result: String)

    protected abstract fun progress(percent: Int?)

    protected abstract fun error(errorCode: Int, msg: String)

}
