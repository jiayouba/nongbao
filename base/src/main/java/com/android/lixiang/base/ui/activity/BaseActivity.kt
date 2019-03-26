package com.android.lixiang.base.ui.activity

import android.support.v7.app.AppCompatActivity
import com.trello.rxlifecycle2.components.RxActivity
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportActivity
import me.yokeyword.fragmentation.SupportActivity
import me.yokeyword.fragmentation.SupportActivityDelegate
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

open class BaseActivity: RxActivity(), ISupportActivity{
    val mDelegate = SupportActivityDelegate(this)

    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator?) {
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    override fun getFragmentAnimator(): FragmentAnimator {
        return mDelegate.getFragmentAnimator();
    }

    override fun onBackPressedSupport() {
        mDelegate.onBackPressedSupport();
    }

    override fun extraTransaction(): ExtraTransaction {
        return mDelegate.extraTransaction();
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return mDelegate.onCreateFragmentAnimator()
    }

    override fun getSupportDelegate(): SupportActivityDelegate {
        return mDelegate;
    }

    override fun post(runnable: Runnable?) {
        mDelegate.post(runnable);
    }
}