package vip.qsos.tuchong.view.holder

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.tc_item_recommend.view.*
import qsos.library.base.base.BaseHolder
import qsos.library.base.utils.image.ImageLoaderUtils
import qsos.module.common.api.ApiTC
import qsos.module.common.api.TCRecommendEntity

/**
 * @author : 华清松
 * @date : 2018/10/9
 * @description : 公告、联系消息列表项
 */
class TCRecommendHolder(itemView: View) : BaseHolder<TCRecommendEntity.FeedListBean>(itemView) {

    @SuppressLint("SetTextI18n")
    override fun setData(data: TCRecommendEntity.FeedListBean, position: Int) {

        itemView.tv_item_tc_feed?.text = data.title
        itemView.tv_item_tc_feed?.visibility = if (TextUtils.isEmpty(data.title)) View.GONE else View.VISIBLE

        if (data.images != null && data.images!!.isNotEmpty()) {
            itemView.iv_item_tc_feed_photos.visibility = if (data.images!!.size > 1) View.VISIBLE else View.GONE
            ImageLoaderUtils.display(itemView.context, itemView.iv_item_tc_feed, ApiTC.getImgUrl(data.images!![0].user_id, data.images!![0].img_id))
        }

        itemView.setOnClickListener { v ->
            onClick(v)
        }

    }
}