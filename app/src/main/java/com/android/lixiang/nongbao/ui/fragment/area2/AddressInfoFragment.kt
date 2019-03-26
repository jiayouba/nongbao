package com.android.lixiang.nongbao.ui.fragment.area2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.nongbao.R
import com.smarttop.library.widget.AddressSelector
import com.smarttop.library.widget.OnAddressSelectedListener
import kotlinx.android.synthetic.main.fragment_address_info.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class AddressInfoFragment : SupportFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            mAddressConfirmRL -> {
                pop()
            }
        }
    }

    private fun initViews() {
        mAddressInfoToolbar.title = "地址信息"
        (activity as AppCompatActivity).setSupportActionBar(mAddressInfoToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mAddressInfoToolbar.setNavigationOnClickListener {
            pop()
        }
    }

    fun newInstance(): AddressInfoFragment {
        val args = Bundle()
        val fragment = AddressInfoFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        mAddressConfirmRL.setOnClickListener(this)
        val selector = AddressSelector(context)
        selector.onAddressSelectedListener = OnAddressSelectedListener { province, city, county, street, cun-> }
        val view = selector.view
        mAddressRL.addView(view)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }
}
