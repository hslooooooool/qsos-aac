package qsos.module.common.aac

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import qsos.library.base.BuildConfig
import qsos.library.netservice.http.ApiEngine
import qsos.module.common.ApiBase
import qsos.module.common.api.ApiTC

/**
 * @author : 华清松
 * @date : 2019/1/23
 * @description : 基础数据服务
 */
open class BaseRepository {

    /*用户接口*/
    val serviceUser = ApiEngine.getInstance(BuildConfig.SERVER_URL).create(ApiBase::class.java)!!
    /*图虫接口*/
    val serviceTC = ApiEngine.getInstance(BuildConfig.SERVER_URL).create(ApiTC::class.java)!!

    private var mCompositeSubscription: CompositeDisposable? = null

    protected fun addSubscribe(subscription: Disposable): Disposable {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = CompositeDisposable()
        }
        mCompositeSubscription?.add(subscription)
        return subscription
    }

    internal fun unSubscribe() {
        mCompositeSubscription?.clear()
    }

}