package vip.qsos.tuchong.view.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.tc_frg_recommend.*
import qsos.module.common.aac.BaseModuleFragment
import qsos.module.common.api.TCRecommendEntity
import qsos.module.common.api.TCRecommendForm
import vip.qsos.tuchong.R
import vip.qsos.tuchong.aac.recommend.TCModelIml
import vip.qsos.tuchong.aac.recommend.TCRepository
import vip.qsos.tuchong.view.adapter.TCRecommendAdapter

/**
 * @author : 华清松
 * @date : 2018/12/6
 * @description : 首页-多标签示例
 */
class TCRecommendFragment : BaseModuleFragment() {

    override val layoutId = R.layout.tc_frg_recommend

    override val reload = true

    private lateinit var mTCModel: TCModelIml
    private lateinit var mAdapter: TCRecommendAdapter
    private var feedList = arrayListOf<TCRecommendEntity.FeedListBean>()
    private val mTCRecommendForm = TCRecommendForm()
    private var refresh = true

    override fun initData(savedInstanceState: Bundle?) {
        mTCModel = getViewModel(TCModelIml(TCRepository()))
    }

    override fun initView(view: View) {
        mTCRecommendForm.page = 1
        mAdapter = TCRecommendAdapter(feedList)
        rv_home_frg_tc_recommend.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_home_frg_tc_recommend.adapter = mAdapter

        srl_home_frg_tc_recommend?.setOnRefreshListener {
            refresh = true
            mTCRecommendForm.type = "refresh"
            mTCRecommendForm.page = 1
            mTCRecommendForm.post_id = null

            getData()
        }?.setOnLoadMoreListener {
            refresh = false
            mTCRecommendForm.type = "loadmore"
            mTCRecommendForm.page = mTCRecommendForm.page ?: 1 + 1
            if (mAdapter.data.isNotEmpty()) {
                mTCRecommendForm.post_id = mAdapter.data.last().post_id
            }

            getData()
        }?.autoLoadMore()

    }

    private fun isSlideToBottom(recyclerView: RecyclerView?): Boolean {
        if (recyclerView == null) return false
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()
    }

    override fun bindView(view: View) {
        mTCModel.dataRecommend.observe(this, Observer<TCRecommendEntity> {
            srl_home_frg_tc_recommend?.finishRefresh()
            srl_home_frg_tc_recommend?.finishLoadMore()

            if (refresh) {
                feedList.clear()
            }

            feedList.addAll(it?.feedList ?: arrayListOf())

            mAdapter.notifyDataSetChanged()
        })
    }

    override fun getData() {
        mTCModel.getRecommend(activity!!.application, mTCRecommendForm)
    }

}