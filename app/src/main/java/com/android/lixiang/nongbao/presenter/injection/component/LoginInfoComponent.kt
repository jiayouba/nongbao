package com.android.lixiang.nongbao.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.nongbao.presenter.injection.module.InfoCollectEntryModule
import com.android.lixiang.nongbao.presenter.injection.module.InfoCollectModule
import com.android.lixiang.nongbao.presenter.injection.module.LoginInfoModule
import com.android.lixiang.nongbao.ui.fragment.area1.InfoCollectEntryFragment
import com.android.lixiang.nongbao.ui.fragment.area1.InfoCollectFragment
import com.android.lixiang.nongbao.ui.fragment.user.LoginFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(LoginInfoModule::class))
interface LoginInfoFragmentComponent {
    fun inject(fragment: LoginFragment)
}