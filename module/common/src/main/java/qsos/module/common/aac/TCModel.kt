package qsos.module.common.aac

import android.app.Application
import qsos.module.common.api.TCRecommendForm

/**
 * @author : 华清松
 * @date : 2018/9/6 16:46
 * @description :图虫数据接口
 */
interface TCModel {
    /**获取推荐数据*/
    fun getRecommend(application: Application, recommendForm: TCRecommendForm)

}