package com.android.lixiang.nongbao.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.nongbao.R
import com.smarttop.library.widget.AddressSelector
import com.smarttop.library.widget.OnAddressSelectedListener
import kotlinx.android.synthetic.main.fragment_address_info.*
import kotlinx.android.synthetic.main.fragment_all_apps.*
import kotlinx.android.synthetic.main.fragment_choose_area.*
import kotlinx.android.synthetic.main.fragment_id_info.*
import kotlinx.android.synthetic.main.fragment_location_info.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class ChooseAreaFragment : SupportFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            mAreaNextStepRL -> {
                start(ChooseBankOfDepositFragment().newInstance())
            }
        }
    }

    private fun initViews() {
        mAreaToolbar.title = "选择区域"
        (activity as AppCompatActivity).setSupportActionBar(mAreaToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun newInstance(): ChooseAreaFragment {
        val args = Bundle()
        val fragment = ChooseAreaFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_area, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        mAreaNextStepRL.setOnClickListener(this)
        val selector = AddressSelector(context)
        selector.onAddressSelectedListener = OnAddressSelectedListener { province, city, county, street, cun -> }
        val view = selector.view
        mAreaRL.addView(view)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()

        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }
}
