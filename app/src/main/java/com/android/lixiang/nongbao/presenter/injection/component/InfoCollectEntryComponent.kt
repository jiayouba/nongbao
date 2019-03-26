package com.android.lixiang.nongbao.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.nongbao.presenter.injection.module.InfoCollectEntryModule
import com.android.lixiang.nongbao.ui.fragment.area1.InfoCollectEntryFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(InfoCollectEntryModule::class))
interface InfoCollectEntryFragmentComponent {
    fun inject(fragment: InfoCollectEntryFragment)
}