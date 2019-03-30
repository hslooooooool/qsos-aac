package qsos.module.common.aac

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import qsos.module.common.api.TCRecommendEntity
import qsos.module.common.api.TCRecommendForm

/**
 * @author : 华清松
 * @date : 2019/1/22 16:12
 * @description : TODO 类说明，描述此类的类型和用途
 */
class TCRepository : TCModel, BaseRepository() {

    override fun getRecommend(application: Application, recommendForm: TCRecommendForm) {
        addSubscribe(
                serviceTC.getRecommend(recommendForm.getMap())
                        .subscribeOn(Schedulers.io())
                        .map {
                            it
                        }
                        .subscribeOn(Schedulers.io())
                        .subscribe {
                            dataRecommend.postValue(it)
                        }
        )
    }

    /**推荐数据*/
    val dataRecommend = MutableLiveData<TCRecommendEntity>()

}