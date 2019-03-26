package com.android.lixiang.nongbao.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.amap.api.maps.model.LatLng
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.nongbao.R
import com.android.lixiang.nongbao.ui.Bank
import com.android.lixiang.nongbao.ui.adapter.BankAdapter
import com.smarttop.library.widget.AddressSelector
import com.smarttop.library.widget.OnAddressSelectedListener
import kotlinx.android.synthetic.main.fragment_address_info.*
import kotlinx.android.synthetic.main.fragment_all_apps.*
import kotlinx.android.synthetic.main.fragment_choose_bank.*
import kotlinx.android.synthetic.main.fragment_fill_survey.*
import kotlinx.android.synthetic.main.fragment_id_info.*
import kotlinx.android.synthetic.main.fragment_location_info.*
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.ArrayList


class ChooseBankFragment : SupportFragment() {
//    override fun onClick(v: View?) {
//        when (v) {
//            mBankNextStepRL -> {
//                start(ChooseAreaFragment().newInstance())
//            }
//        }
//    }

 private var bankList: MutableList<Bank> = ArrayList()
    private fun initBanks(){
        for (i in 0..1) {
            val ziduan1 = Bank("字段", R.drawable.img_graynike)
            bankList.add(ziduan1)
            val ziduan2 = Bank("字段", R.drawable.img_graynike)
            bankList.add(ziduan2)
            val zhonghang = Bank("中国银行", R.drawable.img_graynike)
            bankList.add(zhonghang)
            val jianhang = Bank("建设银行", R.drawable.img_graynike)
            bankList.add(jianhang)
            val zhongxin = Bank("中信银行", R.drawable.img_graynike)
            bankList.add(zhongxin)
            val xingye = Bank("兴业银行", R.drawable.img_graynike)
            bankList.add(xingye)
            val ziduan5 = Bank("字段", R.drawable.img_graynike)
            bankList.add(ziduan5)
            val ziduan6 = Bank("字段", R.drawable.img_graynike)
            bankList.add(ziduan6)
        }
    }
    private fun initViews() {
        mBankRLToolbar.title = "选择银行"
        (activity as AppCompatActivity).setSupportActionBar(mBankRLToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun newInstance(): ChooseBankFragment {
        val args = Bundle()
        val fragment = ChooseBankFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_bank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initBanks()
//        mBankNextStepRL.setOnClickListener(this)
        val adapter=BankAdapter(context,R.layout.bank_item,bankList)
        bankitems.adapter=adapter
        mBankNextStepRL.setOnClickListener {
            start(ChooseAreaFragment().newInstance())
        }
        bankitems.setOnItemClickListener { adapterView, view, i, l ->

          adapter.setSelectedItem(i)
            adapter.notifyDataSetInvalidated()

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
}
