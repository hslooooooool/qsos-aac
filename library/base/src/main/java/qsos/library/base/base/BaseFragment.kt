package qsos.library.base.base

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_base.*
import qsos.library.base.R
import qsos.library.base.utils.LogUtil

/**
 * @author : 华清松
 * @date : 2019/1/24
 * @description : Base Fragment
 */
abstract class BaseFragment : Fragment(), BaseView, View.OnClickListener {

    override var isActive: Boolean = false
        protected set(value) {
            field = value
        }

    override var mContext: Context? = null
        protected set(value) {
            field = value
        }

    private var mainView: View? = null

    /**设置视图ID*/
    abstract val layoutId: Int?

    /**视图重载是否重新加载数据*/
    abstract val reload: Boolean

    override val defLayoutId: Int
        get() = R.layout.fragment_default

    private var isFirst: Boolean = false

    /*注意调用顺序*/

    /**初始化数据*/
    abstract fun initData(savedInstanceState: Bundle?)

    /**初始化视图*/
    abstract fun initView(view: View)

    /**数据与视图绑定*/
    abstract fun bindView(view: View)

    /**获取数据*/
    abstract fun getData()

    override fun getNowTime(): Long {
        return System.currentTimeMillis()
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        initData(bundle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?): View? {
        LogUtil.i("创建:${javaClass.name}")

        mContext = context

        mainView = if (layoutId == null) {
            inflater.inflate(defLayoutId, container, false)
        } else {
            inflater.inflate(layoutId!!, container, false)
        }

        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // kotlin 务必在此进行 initView 操作，否则将出现空指针异常
        initView(view)

        ll_base_not_net?.setOnClickListener(this)
        ll_base_net_error?.setOnClickListener(this)

        bindView(view)
    }

    override fun onResume() {
        super.onResume()
        // 页面重现，重新加载数据
        if (reload) {
            getData()
        }
    }

    override fun finishThis() {
        (mContext as Activity).finish()
    }

    override fun showToast(msg: String?) {
        Toast.makeText(mContext, if (TextUtils.isEmpty(msg)) "没有信息" else msg, Toast.LENGTH_SHORT).show()
    }

    override fun showDialog(msg: String?) {
        Toast.makeText(mContext, "弹窗:$msg", Toast.LENGTH_SHORT).show()
    }

    fun setFirst() {
        isFirst = true
    }

    /**隐藏输入键盘*/
    fun hideKeyboard() {
        val imm: InputMethodManager = mContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            if (activity?.currentFocus?.windowToken != null) {
                imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
    }

    /**切换通用界面*/
    fun changeView(state: BaseView.STATE) {
        ll_base_not_net?.visibility = View.GONE
        ll_base_404?.visibility = View.GONE
        ll_base_net_error?.visibility = View.GONE
        ll_base_loading?.visibility = View.GONE
        ll_base_null?.visibility = View.GONE
        when (state) {
            BaseView.STATE.NOT_NET -> {
                ll_base_not_net?.visibility = View.VISIBLE
            }
            BaseView.STATE.LOADING -> {
                ll_base_loading?.visibility = View.VISIBLE
            }
            BaseView.STATE.NET_ERROR -> {
                ll_base_net_error?.visibility = View.VISIBLE
            }
            BaseView.STATE.NOT_FOUND -> {
                ll_base_404?.visibility = View.VISIBLE
            }
            BaseView.STATE.RESULT_NULL -> {
                ll_base_null?.visibility = View.VISIBLE
            }
            BaseView.STATE.OK -> {
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_base_not_net, R.id.ll_base_net_error -> {
                changeView(BaseView.STATE.LOADING)
                getData()
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified E : ViewModel> getViewModel(c: E): E {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return c as T
            }
        })[E::class.java]
    }

}
