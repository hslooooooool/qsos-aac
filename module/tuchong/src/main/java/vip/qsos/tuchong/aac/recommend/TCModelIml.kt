package vip.qsos.tuchong.aac.recommend

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import qsos.module.common.api.TCRecommendEntity
import qsos.module.common.api.TCRecommendForm

/**
 * @author : 华清松
 * @date : 2019/1/24
 * @description : 图虫数据接口实现
 */
class TCModelIml(
        private val tcRepository: TCRepository
) : ViewModel(), TCModel {

    override fun getRecommend(application: Application, recommendForm: TCRecommendForm) {
        tcRepository.getRecommend(application, recommendForm)
    }

    var dataRecommend: LiveData<TCRecommendEntity> = Transformations.map(tcRepository.dataRecommend) { it }

}