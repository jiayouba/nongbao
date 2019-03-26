package com.android.lixiang.nongbao.ui.fragment.area1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.utils.view.DimenUtil
import com.android.lixiang.base.utils.view.GridSpacingItemDecoration
import com.android.lixiang.nongbao.R
import com.android.lixiang.nongbao.ui.adapter.AllInfoAdapter
import com.android.lixiang.nongbao.ui.fragment.MapInfoFragment
import com.android.lixiang.nongbao.ui.fragment.area2.AcceptInsuranceFragment
import kotlinx.android.synthetic.main.fragment_all_info.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class AllInfoFragment : SupportFragment(), View.OnClickListener {

    private var mAdapter: AllInfoAdapter? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private var mData: MutableList<String>? = mutableListOf()

    override fun onClick(v: View?) {
        when (v) {
//            mIdConfirmRL -> {
//                start(LocationInfoFragment().newInstance())
//            }
        }
    }

    private fun initViews() {
        mAllInfoToolbar.title = "全部信息"
        (activity as AppCompatActivity).setSupportActionBar(mAllInfoToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        for (i in 0 until 10)
            mData!!.add(i.toString())
    }

    fun newInstance(): AllInfoFragment {
        val args = Bundle()
        val fragment = AllInfoFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
//        mIdConfirmRL.setOnClickListener(this)
        mLayoutManager = LinearLayoutManager(activity)
        mAllInfoRV.layoutManager = mLayoutManager
        mAllInfoRV.addItemDecoration(GridSpacingItemDecoration(1, DimenUtil().dip2px(activity!!, 14F), true))
        mAdapter = AllInfoAdapter(R.layout.item_all_info_list, mData!!)
        mAdapter!!.setOnItemClickListener { adapter, view, position ->
            start(MapInfoFragment().newInstance())
        }
        mAllInfoRV.adapter = mAdapter
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
