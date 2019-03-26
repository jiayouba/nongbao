package com.android.lixiang.nongbao.ui.fragment.area1

import android.app.Activity
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.nongbao.R
import kotlinx.android.synthetic.main.fragment_info_collect_entry.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.nongbao.R.id.*
import com.android.lixiang.nongbao.presenter.InfoCollectEntryPresenter
import com.android.lixiang.nongbao.presenter.data.bean.DetailBean
import com.android.lixiang.nongbao.presenter.injection.component.DaggerInfoCollectEntryFragmentComponent
import com.android.lixiang.nongbao.presenter.injection.module.InfoCollectEntryModule
import com.android.lixiang.nongbao.presenter.view.InfoCollectEntryView
import com.blankj.utilcode.util.FragmentUtils.pop
import com.blankj.utilcode.util.KeyboardUtils
import com.orhanobut.logger.Logger
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragmentDelegate


class InfoCollectEntryFragment :
        BaseMvpFragment<InfoCollectEntryPresenter>(),
//        SupportFragment(),
        View.OnClickListener, InfoCollectEntryView
//        , ISupportFragment
{
    override fun returnData2(s: DetailBean) {
        Logger.d(s.data.originalPrice)
    }

    override fun returnData(s: DetailBean) {
        Logger.d(s.data.originalPrice)
    }

    override fun test(string: String) {
        Logger.d(string)
    }

    override fun injectComponent() {
        DaggerInfoCollectEntryFragmentComponent.builder().fragmentComponent(fragmentComponent).infoCollectEntryModule(InfoCollectEntryModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onClick(v: View?) {
        when (v) {
            mInfoEntryRelativeLayout -> {
                start(InfoCollectFragment().newInstance())
            }
            mAllInfoRL -> {
                val snackBar = Snackbar.make(view!!, "此版本为开发版本，暂未开放该功能", Snackbar.LENGTH_SHORT)
                snackBar.show()
//                start(AllInfoFragment().newInstance())
            }

            mMapRL -> {
                val snackBar = Snackbar.make(view!!, "此版本为开发版本，暂未开放该功能", Snackbar.LENGTH_SHORT)
                snackBar.show()
            }
        }
    }

    private fun initViews() {
        mInfoSearchToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mInfoSearchToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mInfoSearchToolbar.setNavigationOnClickListener {
            pop()
        }

        mInfoEntrySearchET.setOnEditorActionListener { v, actionId, event ->
            /**
             * @param v
             * @param actionId 针对软键盘,若是实体键盘actionId=0
             * @param event 针对实体键盘,若是软键盘event=null
             * @return 返回true表示自己处理Enter事件,当imeOptions="actionSearch"时返回false此方法会被调用两次
             */
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                KeyboardUtils.hideSoftInput(activity)
//                start(AllInfoFragment().newInstance())
                val snackBar = Snackbar.make(view!!, "此版本为开发版本，暂未开放该功能", Snackbar.LENGTH_SHORT)
                snackBar.show()
                true
            } else false
        }

        mPresenter.getData2()
    }

    fun newInstance(): InfoCollectEntryFragment {
        val args = Bundle()
        val fragment = InfoCollectEntryFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_collect_entry, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0, null)
        initViews()
        mInfoEntryRelativeLayout.setOnClickListener(this)
        mAllInfoRL.setOnClickListener(this)
        mMapRL.setOnClickListener(this)
    }

//    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        onCreate(savedInstanceState)
//    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

    }

//    override val mDelegate = SupportFragmentDelegate(this)
//    override var _mActivity: FragmentActivity? = null
}
