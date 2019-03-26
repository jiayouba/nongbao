package com.android.lixiang.nongbao.ui.fragment.area1

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.android.lixiang.nongbao.R
import kotlinx.android.synthetic.main.fragment_location_info.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import com.smarttop.library.bean.County
import com.smarttop.library.bean.Street
import com.smarttop.library.widget.AddressSelector
import com.smarttop.library.widget.OnAddressSelectedListener
import com.blankj.utilcode.util.SnackbarUtils.getView
import com.orhanobut.logger.Logger
import com.smarttop.library.bean.Cun
import kotlinx.android.synthetic.main.fragment_set_password.*
import me.yokeyword.fragmentation.ISupportFragment


class LocationInfoFragment : SupportFragment(), View.OnClickListener, OnAddressSelectedListener, AddressSelector.OnDialogCloseListener, AddressSelector.onSelectorAreaPositionListener {

    private var mLocationInfo: String? = null
    private var flag1: Boolean? = false
    private var flag2: Boolean? = false

    override fun onAddressSelected(province: com.smarttop.library.bean.Province?, city: com.smarttop.library.bean.City?, county: County?, street: Street?, cun: Cun?) {
    }

    override fun dialogclose() {
    }

    override fun selectorAreaPosition(provincePosition: Int, cityPosition: Int, countyPosition: Int, streetPosition: Int, cunPosition: Int) {
    }

    override fun onClick(v: View?) {
        when (v) {
            mLocationConfirmRL -> {
                if(mLocationInfo!= null && mPlantLocationET.text.toString().isNotEmpty()){
                    val bundle = Bundle()
                    bundle.putString("INSURER_ADDRESS", mLocationInfo)
                    bundle.putString("PLANTING_PLACE", mPlantLocationET.text.toString())
                    setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                    pop()
                }
            }
        }
    }

    override fun onBackPressedSupport(): Boolean {
        val bundle = Bundle()
        bundle.putString("INSURER_ADDRESS", "")
        bundle.putString("PLANTING_PLACE", "")
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        return super.onBackPressedSupport()

    }

    private fun initViews() {
        mLocationInfoToolbar.title = "位置信息"
        (activity as AppCompatActivity).setSupportActionBar(mLocationInfoToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mPlantLocationET.addTextChangedListener(mTextWatcher)

        mLocationInfoToolbar.setNavigationOnClickListener {
            pop()
        }
    }

    private var mTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            if (s.toString().isNotEmpty()){
                flag2 = true
                editDone(flag1!!, flag2!!)
            }else{
                flag2 = false
                editDone(flag1!!, flag2!!)
            }
        }
    }

    fun newInstance(): LocationInfoFragment {
        val args = Bundle()
        val fragment = LocationInfoFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
//        mInfoCollectRL.setOnClickListener(this)
        initLocationPicker()
        mLocationConfirmRL.setOnClickListener(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initLocationPicker() {
        val selector = AddressSelector(context)
        selector.onAddressSelectedListener = OnAddressSelectedListener { province, city, county, street, cun ->
            mLocationInfo = province!!.name + city!!.name + county!!.name + street!!.name + cun!!.name
            if (mLocationInfo != null)
                flag1 = true
            var mSharedPreferences: SharedPreferences? = null
            mSharedPreferences = activity!!.getSharedPreferences("ADDRE", Context.MODE_PRIVATE)
            val editor = mSharedPreferences!!.edit()
            editor.putString("shi", city!!.name)
            editor.putString("xian", county!!.name)
            editor.putString("xiang", street!!.name)
            editor.putString("cun", cun!!.name)
            editor.commit()
            editDone(flag1!!, flag2!!)
        }
        val view = selector.view
        selector.listView.isVerticalScrollBarEnabled = false
        selector.setIndicatorBackgroundColor("#D0021B")
        selector.listView.setOnTouchListener { v, event ->
            selector.listView.parent.requestDisallowInterceptTouchEvent(true)
            false
        }
        mLocationPickerRL.addView(view)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun editDone(f1: Boolean, f2: Boolean) {
        if (f1 && f2) {
            mLocationConfirmRL.setBackgroundColor(Color.parseColor("#6299FF"))
        } else {
            mLocationConfirmRL.setBackgroundColor(Color.parseColor("#C5C5C5"))
        }
    }
}
