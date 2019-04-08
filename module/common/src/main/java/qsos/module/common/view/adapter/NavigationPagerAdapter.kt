package qsos.module.common.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @author : 华清松
 * @date : 2018/10/14
 * @description : 片段 适配器
 */
class NavigationPagerAdapter(fm: FragmentManager?, var list: List<Fragment>) : FragmentPagerAdapter(fm) {
    private var titleList: ArrayList<String>? = null

    constructor(fm: FragmentManager?, list: List<Fragment>, titleList: ArrayList<String>) : this(fm, list) {
        this.titleList = titleList
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (titleList == null) super.getPageTitle(position) else titleList!![position]
    }

}
