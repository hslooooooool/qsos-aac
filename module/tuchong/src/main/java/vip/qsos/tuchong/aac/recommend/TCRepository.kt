package vip.qsos.tuchong.aac.recommend

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import qsos.module.common.aac.BaseModuleRepository
import qsos.module.common.api.TCRecommendEntity
import qsos.module.common.api.TCRecommendForm

/**
 * @author : 华清松
 * @date : 2019/1/22 16:12
 * @description : 图虫数据获取
 */
class TCRepository : TCModel, BaseModuleRepository() {

    override fun getRecommend(application: Application, recommendForm: TCRecommendForm) {
        addSubscribe(
                serviceTC.getRecommend(recommendForm.getMap())
                        .subscribeOn(schedulerIO)
                        .map {
                            // todo 可以做一些数据验证等操作
                            print(it)
                            it
                        }
                        .subscribeOn(schedulerIO)
                        .subscribe {
                            dataRecommend.postValue(it)
                        }
        )
    }

    /**推荐数据*/
    val dataRecommend = MutableLiveData<TCRecommendEntity>()
    private val schedulerIO: Scheduler = Schedulers.io()

}