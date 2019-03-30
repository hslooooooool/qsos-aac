package qsos.module.common.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * @author : 华清松
 * @date : 2018/10/24
 * @description : 图虫 接口 https://github.com/jokermonn/-Api/blob/master/Tuchong.md
 */
interface ApiTC {

    companion object {
        /**图片预览地址*/
        fun getImgUrl(userId: Int, imgId: Int): String {
            return "http://photo.tuchong.com/$userId/f/$imgId.jpg"
        }
    }

    /**推荐数据获取*/
    @GET("feed-app")
    fun getRecommend(
            @QueryMap form: @JvmSuppressWildcards Map<String, Any?>
    ): Observable<TCRecommendEntity>

}