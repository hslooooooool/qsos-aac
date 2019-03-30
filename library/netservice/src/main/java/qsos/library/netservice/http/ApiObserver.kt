package qsos.library.netservice.http

import qsos.library.base.entity.http.ApiException
import qsos.library.netservice.http.interceptor.ApiExceptionService
import timber.log.Timber

/**
 * @author 华清松
 * @email 821034742@qq.com
 * @weixin hslooooooool
 * @doc 类说明：请求回调处理
 */
open class ApiObserver<T> : ApiExceptionService<T>() {

    override fun onStart() {

        Timber.tag("网络请求：").e("onStart: 开始请求")
    }

    override fun onError(ex: ApiException) {

        Timber.tag("网络请求：").e("onError: 请求异常")
    }

    override fun onNext(data: T) {

        Timber.tag("网络请求：").i("onNext: 请求结果= ${data.toString()}")
    }

    override fun onComplete() {

        Timber.tag("网络请求：").e("onError: 请求完毕")
    }

}
