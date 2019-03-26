package com.android.lixiang.nongbao.ui.fragment.area1

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.nongbao.R
import com.android.lixiang.nongbao.ui.fragment.MapInfoFragment
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_success_commit.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class SuccessCommitFragment : SupportFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            mCheckInfoRL -> {
//                start(MapInfoFragment().newInstance())
                val snackBar = Snackbar.make(view!!, "此版本为开发版本，暂未开放该功能", Snackbar.LENGTH_SHORT)
                snackBar.show()
            }

            mCompleteRL ->{
                popTo(InfoCollectEntryFragment::class.java, false)
            }

            mCheckInfoBtn -> {
//                start(MapInfoFragment().newInstance())
                val snackBar = Snackbar.make(view!!, "此版本为开发版本，暂未开放该功能", Snackbar.LENGTH_SHORT)
                snackBar.show()
            }
        }
    }

    private fun initViews() {
        mCheckInfoRL.setOnClickListener(this)
        mCompleteRL.setOnClickListener(this)
        mCheckInfoBtn.setOnClickListener(this)
    }

    fun newInstance(): SuccessCommitFragment {
        val args = Bundle()
        val fragment = SuccessCommitFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_success_commit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()

    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}
