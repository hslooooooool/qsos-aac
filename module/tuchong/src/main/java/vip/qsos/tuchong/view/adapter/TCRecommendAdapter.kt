package vip.qsos.tuchong.view.adapter

import android.view.View
import qsos.library.base.base.BaseAdapter
import qsos.library.base.base.BaseHolder
import qsos.module.common.api.TCRecommendEntity
import vip.qsos.tuchong.R
import vip.qsos.tuchong.view.holder.TCRecommendHolder

/**
 * @author : 华清松
 * @date : 2019/1/24
 * @description : 图虫推荐列表
 */
class TCRecommendAdapter(list: ArrayList<TCRecommendEntity.FeedListBean>) : BaseAdapter<TCRecommendEntity.FeedListBean>(list) {

    override fun getArrayList(): ArrayList<TCRecommendEntity.FeedListBean> {
        return data
    }

    override fun getHolder(view: View, viewType: Int): BaseHolder<TCRecommendEntity.FeedListBean> {
        return TCRecommendHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.tc_item_recommend
    }

    override fun getLayoutId(viewType: Int): Int {
        return viewType
    }

    override fun onViewClick(view: View, position: Int) {

    }

}