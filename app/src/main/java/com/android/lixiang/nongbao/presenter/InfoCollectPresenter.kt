package com.android.lixiang.nongbao.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.nongbao.presenter.data.bean.DetailBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectBean
import com.android.lixiang.nongbao.presenter.data.bean.InsertCollectingPictureBean
import com.android.lixiang.nongbao.presenter.view.InfoCollectEntryView
import com.android.lixiang.nongbao.presenter.view.InfoCollectView
import com.android.lixiang.nongbao.service.InfoCollectEntryService
import com.android.lixiang.nongbao.service.InfoCollectService
import com.android.lixiang.nongbao.service.impl.InfoCollectServiceImpl
import com.orhanobut.logger.Logger
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class InfoCollectPresenter @Inject constructor() : BasePresenter<InfoCollectView>() {
    @Inject
    lateinit var mInfoCollectService: InfoCollectService

    fun test() {
        mView.test(mInfoCollectService.test())
    }

    fun getData() {
        mView.returnData(mInfoCollectService.getData("45.91", "1"))

    }

    fun getData2() {
        mInfoCollectService.getData2("45.91", "1").execute(object : BaseObserver<DetailBean>() {
            override fun onNext(t: DetailBean) {
                super.onNext(t)
                mView.returnData2(t)
            }
        }, lifecycleProvider)
    }

    fun insertCollectingInformation(loginNameId: String, plotNumber: String, insurer: String, identityType: String, idNumber: String, insurerAddress: String, phoneNumber: String, plantingPlace: String, insuranceAmount: String, shape: String, plantingPlace1: String, insuranceAmount1: String) {
        Logger.d("INSUREADDRESS" + insurerAddress)
        mInfoCollectService.getData3(loginNameId,
                plotNumber,
                insurer,
                identityType,
                idNumber,
                insurerAddress,
                phoneNumber,
                plantingPlace,
                insuranceAmount,
                shape,
                plantingPlace1,
                insuranceAmount1).execute(object : BaseObserver<InfoCollectBean>() {
            override fun onNext(t: InfoCollectBean) {
                super.onNext(t)
                println(t)
                mView.returnData3(t)
            }
        }, lifecycleProvider)
    }

    fun insertCollectingPicture(infomationId: String, collectingPictureNames: Array<File>) {
        mInfoCollectService.insertCollectingPicture(infomationId, collectingPictureNames).execute(object : BaseObserver<InsertCollectingPictureBean>() {
            override fun onNext(t: InsertCollectingPictureBean) {
                super.onNext(t)
                Logger.d(t.message)
//                mView.returnData3(t)
            }
        }, lifecycleProvider)
    }

    fun insertCollectingPicture(infomationId: String, collectingPictureNames: File) {
        mInfoCollectService.testInsertCollectingPicture(infomationId, collectingPictureNames).execute(object : BaseObserver<InsertCollectingPictureBean>() {
            override fun onNext(t: InsertCollectingPictureBean) {
                super.onNext(t)
                Logger.d(t.message)
//                mView.returnData3(t)
            }
        }, lifecycleProvider)
    }

    fun insertCollectingPicture(infomationId: String, collectingPictureNames: MultipartBody.Part) {
        mInfoCollectService.testInsertCollectingPicture(infomationId, collectingPictureNames).execute(object : BaseObserver<InsertCollectingPictureBean>() {
            override fun onNext(t: InsertCollectingPictureBean) {
                super.onNext(t)
                Logger.d(t.message)
//                mView.returnData3(t)
            }
        }, lifecycleProvider)
    }

    fun insertCollectingPicture(infomationId: String, collectingPictureNames: List<MultipartBody.Part>) {
        mInfoCollectService.InsertCollectingPictureOffical(infomationId, collectingPictureNames).execute(object : BaseObserver<InsertCollectingPictureBean>() {
            override fun onNext(t: InsertCollectingPictureBean) {
                super.onNext(t)
                Logger.d(t.message)
//                mView.returnData3(t)
            }
        }, lifecycleProvider)
    }
    ////////test
}