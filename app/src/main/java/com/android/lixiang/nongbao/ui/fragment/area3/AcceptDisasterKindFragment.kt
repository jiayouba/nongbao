package com.android.lixiang.nongbao.ui.fragment.area2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import com.android.lixiang.nongbao.R
import com.android.lixiang.nongbao.ui.adapter.BankAdapter
import com.android.lixiang.nongbao.ui.adapter.DisasterKindAdapter
import com.android.lixiang.nongbao.ui.fragment.area3.DisasterKind
import kotlinx.android.synthetic.main.fragment_accept_disasterkind.*
import kotlinx.android.synthetic.main.fragment_accept_disasterspecies.*
import kotlinx.android.synthetic.main.fragment_choose_bank.*
import kotlinx.android.synthetic.main.fragment_fill_survey.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.ArrayList


class AcceptDisasterKindFragment : SupportFragment() {
    //    override fun onClick(v: View?) {
//        when (v) {
//            mBankNextStepRL -> {
//                start(ChooseAreaFragment().newInstance())
//            }
//        }
//    }
    var index = -1
    private var disasterkindList: MutableList<DisasterKind> = ArrayList()
    private fun init() {
        for (i in 0..0) {
            val ziduan1 = DisasterKind("暴雨")
            disasterkindList.add(ziduan1)
            val ziduan2 = DisasterKind("洪水")
            disasterkindList.add(ziduan2)
            val zhonghang = DisasterKind("内涝")
            disasterkindList.add(zhonghang)
            val jianhang = DisasterKind("风灾")
            disasterkindList.add(jianhang)
            val zhongxin = DisasterKind("雹灾")
            disasterkindList.add(zhongxin)
            val xingye = DisasterKind("旱灾")
            disasterkindList.add(xingye)
            val ziduan5 = DisasterKind("冻灾")
            disasterkindList.add(ziduan5)
            val ziduan6 = DisasterKind("泥石流")
            disasterkindList.add(ziduan6)
            val ziduan7 = DisasterKind("地震山体滑坡")
            disasterkindList.add(ziduan7)
            val ziduan8 = DisasterKind("病虫草鼠害")
            disasterkindList.add(ziduan8)
            val ziduan9 = DisasterKind("其他灾害")
            disasterkindList.add(ziduan9)

        }
    }

    private fun initViews() {
        mChooseDisasterKindToolbar.title = "受灾类型"
        (activity as AppCompatActivity).setSupportActionBar(mChooseDisasterKindToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun newInstance(): AcceptDisasterKindFragment {
        val args = Bundle()
        val fragment = AcceptDisasterKindFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accept_disasterkind, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        init()
//        mBankNextStepRL.setOnClickListener(this)
        val adapter = DisasterKindAdapter(context, R.layout.disasterkind_item, disasterkindList)
        disasterkinditems.adapter = adapter
        disasterkinditems.choiceMode = AbsListView.CHOICE_MODE_SINGLE
        disasterkinditems.setOnItemClickListener { adapterView, view, i, l ->
            index = i
            val bundle = Bundle()
            bundle.putString("index", index.toString())
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
            pop()

        }

//        bankitems.setOnItemClickListener() {
//                    定义子项点击事件
//        }

//        val selector = AddressSelector(context)
//        selector.onAddressSelectedListener = OnAddressSelectedListener { province, city, county, street -> }
//        val view = selector.view
//        mAddressRL.addView(view)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()

        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }

    override fun onBackPressedSupport(): Boolean {
        val bundle = Bundle()
        bundle.putString("index", "-1")
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        return super.onBackPressedSupport()
    }
}
