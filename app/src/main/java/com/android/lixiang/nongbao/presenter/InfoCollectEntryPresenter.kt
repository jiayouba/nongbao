package com.android.lixiang.nongbao.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.nongbao.presenter.data.bean.DetailBean
import com.android.lixiang.nongbao.presenter.view.InfoCollectEntryView
import com.android.lixiang.nongbao.service.InfoCollectEntryService
import com.orhanobut.logger.Logger
import javax.inject.Inject

class InfoCollectEntryPresenter @Inject constructor() : BasePresenter<InfoCollectEntryView>() {

    @Inject
    lateinit var mInfoCollectEntryService: InfoCollectEntryService

    fun test() {
        mView.test(mInfoCollectEntryService.test())
    }

    fun getData() {
        Logger.d(mInfoCollectEntryService.getData("45.91", "1").data.originalPrice)
        mView.returnData(mInfoCollectEntryService.getData("45.91", "1"))
    }

    fun getData2(){
        mInfoCollectEntryService.getData2("45.91", "1").execute(object : BaseObserver<DetailBean>() {
            override fun onNext(t: DetailBean) {
                super.onNext(t)
                mView.returnData2(t)
            }
        }, lifecycleProvider)
    }
}