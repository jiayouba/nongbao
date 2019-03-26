package com.android.lixiang.nongbao.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.nongbao.presenter.data.bean.DetailBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectPictureBean
import com.android.lixiang.nongbao.presenter.view.InfoCollectEntryView
import com.android.lixiang.nongbao.presenter.view.InfoCollectPictureView
import com.android.lixiang.nongbao.presenter.view.InfoCollectView
import com.android.lixiang.nongbao.service.InfoCollectEntryService
import com.android.lixiang.nongbao.service.InfoCollectPictureService
import com.android.lixiang.nongbao.service.InfoCollectService
import com.android.lixiang.nongbao.service.impl.InfoCollectServiceImpl
import java.util.logging.Logger
import javax.inject.Inject

class InfoCollectPicturePresenter @Inject constructor(): BasePresenter<InfoCollectPictureView>(){
    @Inject
    lateinit var mInfoCollectPictureService : InfoCollectPictureService

    fun test(){
        mView.testfromPic(mInfoCollectPictureService.test())
    }




//    fun insertCollectingPicture(informationId:String,collectPictureNames:String){
//        mInfoCollectPictureService.getData(informationId,collectPictureNames).execute(object :BaseObserver<InfoCollectPictureBean>() {
//
//            override fun onNext(t: InfoCollectPictureBean) {
//                super.onNext(t)
//
//                mView.returnDatafromPic(t)
//            }
//        },lifecycleProvider)
//    }
}