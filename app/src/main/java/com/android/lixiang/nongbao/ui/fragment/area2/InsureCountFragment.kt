package com.android.lixiang.nongbao.ui.fragment.area2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.nongbao.R
import kotlinx.android.synthetic.main.fragment_insure_count.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class InsureCountFragment : SupportFragment(), View.OnClickListener {

    private var mKind: MutableList<String> ?= mutableListOf()
    override fun onClick(v: View?) {
        when (v) {
            mInsureCountConfirmRL -> {
                pop()
            }
        }
    }

    private fun initViews() {
        mInsureCountToolbar.title = "保险数量"
        (activity as AppCompatActivity).setSupportActionBar(mInsureCountToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun newInstance(): InsureCountFragment {
        val args = Bundle()
        val fragment = InsureCountFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insure_count, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        mKind!!.add("株")
        mKind!!.add("亩")
        mKind!!.add("公顷")

//        mIdConfirmRL.setOnClickListener(this)
        loopView.setItems(mKind)
        loopView.setNotLoop()
        loopView.setInitPosition(1)
        loopView.setTextSize(14F)

        mInsureCountConfirmRL.setOnClickListener(this)

    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}
