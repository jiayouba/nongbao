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
import kotlinx.android.synthetic.main.fragment_accept_disasterdegree.*
import kotlinx.android.synthetic.main.fragment_accept_disasterkind.*
import kotlinx.android.synthetic.main.fragment_accept_disasterspecies.*
import kotlinx.android.synthetic.main.fragment_choose_bank.*
import kotlinx.android.synthetic.main.fragment_fill_survey.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.ArrayList


class AcceptDisasterDegreeFragment : SupportFragment() {
    //    override fun onClick(v: View?) {
//        when (v) {
//            mBankNextStepRL -> {
//                start(ChooseAreaFragment().newInstance())
//            }
//        }
//    }
    var index = -1
    private var disasterdegreeList: MutableList<DisasterKind> = ArrayList()
    private fun init() {
        for (i in 0..0) {
            val ziduan1 = DisasterKind("轻度")
            disasterdegreeList.add(ziduan1)
            val ziduan2 = DisasterKind("中度")
            disasterdegreeList.add(ziduan2)
            val ziduan3 = DisasterKind("重度")
            disasterdegreeList.add(ziduan3)
            val ziduan4 = DisasterKind("接近绝收")
            disasterdegreeList.add(ziduan4)


        }
    }

    private fun initViews() {
        mChooseDisasterDegreeToolbar.title = "受灾程度"
        (activity as AppCompatActivity).setSupportActionBar(mChooseDisasterDegreeToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun newInstance(): AcceptDisasterDegreeFragment {
        val args = Bundle()
        val fragment = AcceptDisasterDegreeFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accept_disasterdegree, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        init()
//        mBankNextStepRL.setOnClickListener(this)
        val adapter = DisasterKindAdapter(context, R.layout.disasterkind_item, disasterdegreeList)
        disasterdegreeitems.adapter = adapter
        disasterdegreeitems.choiceMode = AbsListView.CHOICE_MODE_SINGLE
        disasterdegreeitems.setOnItemClickListener { adapterView, view, i, l ->
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
