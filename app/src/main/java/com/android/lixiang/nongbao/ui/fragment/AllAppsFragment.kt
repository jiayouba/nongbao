package com.android.lixiang.nongbao.ui.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.nongbao.R
import com.android.lixiang.nongbao.ui.fragment.area1.InfoCollectEntryFragment
import com.android.lixiang.nongbao.ui.fragment.area2.FastInsureFragment
import com.android.lixiang.nongbao.ui.fragment.area3.AssistSurveyFragment
import kotlinx.android.synthetic.main.fragment_all_apps.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class AllAppsFragment : SupportFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            mInfoCollectRL -> {
                start(InfoCollectEntryFragment().newInstance())
            }

            mAllAppsFastInsureRL -> {
                start(FastInsureFragment().newInstance())

            }

            mAllAppsAssistSurveyRL -> {
                start(AssistSurveyFragment().newInstance())
            }
        }
    }

    private fun initViews() {

        mAppAppsToolbar.title = "我的应用"
        (activity as AppCompatActivity).setSupportActionBar(mAppAppsToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun newInstance(): AllAppsFragment {
        val args = Bundle()
        val fragment = AllAppsFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_apps, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        mInfoCollectRL.setOnClickListener(this)
        mAllAppsFastInsureRL.setOnClickListener(this)
        mAllAppsAssistSurveyRL.setOnClickListener(this)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}
