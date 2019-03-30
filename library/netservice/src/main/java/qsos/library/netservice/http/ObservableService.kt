package qsos.library.netservice.http

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import qsos.library.base.entity.BaseResult

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：统一请求处理方式
 */
object ObservableService {

    /**设置统一的请求处理*/
    fun <T> setObservable(observable: Observable<BaseResult<T>>): Observable<T> {
        return observable.map(ApiResponseFunc()).onErrorResumeNext(HttpErrorFunc<T>()).compose(RxSchedulers.switchThread())
    }

    fun <T> setObservableFlowable(observable: Flowable<T>): Flowable<T> {
        return observable.onErrorResumeNext(HttpErrorFunctionFlowable<T>()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> setObservableSingle(observable: Single<T>): Single<T> {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}