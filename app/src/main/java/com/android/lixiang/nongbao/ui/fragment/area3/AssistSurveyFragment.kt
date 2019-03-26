package com.android.lixiang.nongbao.ui.fragment.area3

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.nongbao.R
import kotlinx.android.synthetic.main.fragment_assist_survey.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class AssistSurveyFragment : SupportFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            mMyInsuranceRL -> {
                start(MyInsureFragment().newInstance())

            }
//            mAllInfoRL -> {
//                start(AllInfoFragment().newInstance())
//            }
        }
    }

    private fun initViews() {
        mSurveyToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mSurveyToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mSurveyToolbar.setNavigationOnClickListener {
            pop()
        }
    }

    fun newInstance(): AssistSurveyFragment {
        val args = Bundle()
        val fragment = AssistSurveyFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assist_survey, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0, null)
        initViews()
//        mInfoEntryRelativeLayout.setOnClickListener(this)
//        mAllInfoRL.setOnClickListener(this)
        mMyInsuranceRL.setOnClickListener(this)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
    }
}
