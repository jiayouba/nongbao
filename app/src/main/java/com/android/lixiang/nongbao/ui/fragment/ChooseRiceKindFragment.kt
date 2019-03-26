package com.android.lixiang.nongbao.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
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
import kotlinx.android.synthetic.main.fragment_choose_rice_kind.*
import kotlinx.android.synthetic.main.fragment_choose_speciesname.*
import kotlinx.android.synthetic.main.fragment_fill_survey.*
import kotlinx.android.synthetic.main.fragment_id_info.*
import kotlinx.android.synthetic.main.fragment_location_info.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.ArrayList


class ChooseRiceKindFragment : SupportFragment(), View.OnClickListener {
    var index = -1
    override fun onClick(v: View?) {
        when (v) {
            first_rice -> {
                first_rice_choose.visibility = View.VISIBLE
                second_rice_choose.visibility = View.INVISIBLE
                third_rice_choose.visibility = View.INVISIBLE
                index = 0
                val bundle = Bundle()
                bundle.putString("index", index.toString())
                setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                pop()
            }
            second_rice -> {
                first_rice_choose.visibility = View.INVISIBLE
                second_rice_choose.visibility = View.VISIBLE
                third_rice_choose.visibility = View.INVISIBLE
                index = 1
                val bundle = Bundle()
                bundle.putString("index", index.toString())
                setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                pop()
            }
            third_rice -> {
                first_rice_choose.visibility = View.INVISIBLE
                second_rice_choose.visibility = View.INVISIBLE
                third_rice_choose.visibility = View.VISIBLE

                index = 2
                val bundle = Bundle()
                bundle.putString("index", index.toString())
                setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                pop()
            }
        }
    }


    private fun initViews() {
        mChooseRiceKindRLToolbar.title = "品种选择"
        (activity as AppCompatActivity).setSupportActionBar(mChooseRiceKindRLToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun newInstance(): ChooseRiceKindFragment {
        val args = Bundle()
        val fragment = ChooseRiceKindFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_rice_kind, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()

        first_rice.setOnClickListener(this)
        second_rice.setOnClickListener(this)
        third_rice.setOnClickListener(this)
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
