//package com.android.lixiang.nongbao.presenter.injection.component
//
//import com.android.lixiang.base.injection.ComponentScope
//import com.android.lixiang.base.injection.component.FragmentComponent
//import com.android.lixiang.nongbao.presenter.injection.module.InfoCollectEntryModule
//import com.android.lixiang.nongbao.presenter.injection.module.InfoCollectModule
//import com.android.lixiang.nongbao.presenter.injection.module.InfoCollectPictureModule
//import com.android.lixiang.nongbao.ui.fragment.ChooseBankFragment
//import com.android.lixiang.nongbao.ui.fragment.area1.InfoCollectEntryFragment
//import com.android.lixiang.nongbao.ui.fragment.area1.InfoCollectFragment
//import dagger.Component
//
//@ComponentScope
//@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(InfoCollectPictureModule::class))
//interface InfoCollectPictureFragmentComponent {
//    //TODO:图片采集和信息采集都绑定在InfoCollectFragment上,会出现编译错误
//    fun inject(fragment: InfoCollectFragment)
//}