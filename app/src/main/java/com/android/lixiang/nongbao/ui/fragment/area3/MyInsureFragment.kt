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
import com.android.lixiang.nongbao.ui.fragment.AllAppsFragment
import com.android.lixiang.nongbao.ui.fragment.MapInfoFragment
import com.android.lixiang.nongbao.ui.fragment.area1.AllInfoFragment
import com.android.lixiang.nongbao.ui.fragment.area1.LocationInfoFragment
import com.android.lixiang.nongbao.ui.fragment.area1.SuccessCommitFragment
import com.android.lixiang.nongbao.ui.fragment.area2.AcceptInsuranceFragment
import com.blankj.utilcode.util.KeyboardUtils
import com.smarttop.library.utils.LogUtil
import kotlinx.android.synthetic.main.fragment_my_insure.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class MyInsureFragment : SupportFragment(), View.OnClickListener {

    var listFragment: MutableList<Fragment>? = mutableListOf()
    var listTitle: MutableList<String>? = mutableListOf()
    private var fAdapter: FragmentPagerAdapter? = null



    private var toast: Toast? = null
    override fun onClick(v: View?) {
        when (v) {
//            mIdConfirmRL -> {
//                start(LocationInfoFragment().newInstance())
//            }
        }
    }

    private fun initViews() {
        mMyInsureToolbar.title = "我的保单"
        (activity as AppCompatActivity).setSupportActionBar(mMyInsureToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        var allInsure = AllInsureFragment()
        var checkedInsure = AllInsureFragment()
        var uncheckedInsure = AllInsureFragment()

        listFragment!!.add(allInsure)
        listFragment!!.add(checkedInsure)
        listFragment!!.add(uncheckedInsure)

        listTitle!!.add("全部保单")
        listTitle!!.add("已查勘")
        listTitle!!.add("未查勘")

        mMyInsureTL.tabMode = TabLayout.MODE_FIXED
        mMyInsureTL.addTab(mMyInsureTL.newTab().setText(listTitle!![0]))
        mMyInsureTL.addTab(mMyInsureTL.newTab().setText(listTitle!![1]))
        mMyInsureTL.addTab(mMyInsureTL.newTab().setText(listTitle!![2]))

        fAdapter=Find_tab_Adapter(childFragmentManager,listFragment,listTitle)

        mMyInsureVP.adapter=fAdapter
        mMyInsureTL.setupWithViewPager(mMyInsureVP)

        mMyInsureVP.offscreenPageLimit=5
        mMyInsureVP.currentItem = 0
//        mMyInsureTL.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                mMyInsureVP.currentItem=tab!!.position
////               if(mMyInsureTL.getTabAt(0)!!.isSelected){
////                   mMyInsureTL.getTabAt(0)!!.customView=getTabView
////               }
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                mMyInsureVP.currentItem=tab!!.position
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                mMyInsureVP.currentItem=tab!!.position
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        })


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
        return inflater.inflate(R.layout.fragment_my_insure, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
//        mIdConfirmRL.setOnClickListener(this)





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
