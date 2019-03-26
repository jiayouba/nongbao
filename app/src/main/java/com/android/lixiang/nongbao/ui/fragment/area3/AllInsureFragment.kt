package com.android.lixiang.nongbao.ui.fragment.area3

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.lixiang.base.utils.view.DimenUtil
import com.android.lixiang.base.utils.view.GridSpacingItemDecoration
import com.android.lixiang.nongbao.R
import com.android.lixiang.nongbao.ui.adapter.AllInfoAdapter
import com.android.lixiang.nongbao.ui.adapter.MyInsureAdapter
import com.android.lixiang.nongbao.ui.fragment.MapInfoFragment
import com.android.lixiang.nongbao.ui.fragment.area1.AllInfoFragment
import com.android.lixiang.nongbao.ui.fragment.area1.LocationInfoFragment
import com.android.lixiang.nongbao.ui.fragment.area1.SuccessCommitFragment
import com.android.lixiang.nongbao.ui.fragment.area2.AcceptInsuranceFragment
import com.blankj.utilcode.util.KeyboardUtils
import com.smarttop.library.utils.LogUtil
import kotlinx.android.synthetic.main.fragment_all_insure.*
import kotlinx.android.synthetic.main.fragment_my_insure.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.logging.Logger


class AllInsureFragment : SupportFragment(), View.OnClickListener {




    private var mAdapter: MyInsureAdapter? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private var mData: MutableList<String>? = mutableListOf()
    private var toast: Toast? = null
    override fun onClick(v: View?) {
        when (v) {
//            mIdConfirmRL -> {
//                start(LocationInfoFragment().newInstance())
//            }
        }
    }

    private fun initViews() {
//        mMyInsureToolbar.title = "我的保单"
//        (activity as AppCompatActivity).setSupportActionBar(mMyInsureToolbar)
//        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        for (i in 0 until 10)
            mData!!.add(i.toString())




    }

    fun newInstance(): MyInsureFragment {
        val args = Bundle()
        val fragment = MyInsureFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_insure, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
//        mIdConfirmRL.setOnClickListener(this)
        mLayoutManager = LinearLayoutManager(activity)
        mAllInsureRV.layoutManager = mLayoutManager
        mAllInsureRV.addItemDecoration(GridSpacingItemDecoration(1, DimenUtil().dip2px(activity!!, 14F), true))
        mAdapter = MyInsureAdapter(R.layout.item_my_insure_list, mData!!)
        mAllInsureRV.adapter = mAdapter




        mAdapter!!.setOnItemClickListener { adapter, view, position ->
            val fragment: AcceptInsuranceFragment = AcceptInsuranceFragment().newInstance()
            val bundle=Bundle()
            bundle.putString("fromMyInsure","MyInsure")
            fragment.arguments=bundle
            //TODO:点任何子项都一样
            (parentFragment as MyInsureFragment).start(fragment)
            Log.e("item","点击了")

        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
//        StatusBarUtil.setColor(activity, Color.parseColor("#FFFFFF"), 0)
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }
}
