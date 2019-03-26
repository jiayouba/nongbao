package com.android.lixiang.nongbao.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.nongbao.presenter.data.bean.DetailBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectPictureBean
import com.android.lixiang.nongbao.presenter.data.bean.LoginInfoBean
import com.android.lixiang.nongbao.presenter.view.InfoCollectEntryView
import com.android.lixiang.nongbao.presenter.view.InfoCollectPictureView
import com.android.lixiang.nongbao.presenter.view.InfoCollectView
import com.android.lixiang.nongbao.presenter.view.LoginInfoView
import com.android.lixiang.nongbao.service.InfoCollectEntryService
import com.android.lixiang.nongbao.service.InfoCollectPictureService
import com.android.lixiang.nongbao.service.InfoCollectService
import com.android.lixiang.nongbao.service.LoginInfoService
import com.android.lixiang.nongbao.service.impl.InfoCollectServiceImpl
import java.util.logging.Logger
import javax.inject.Inject

class LoginInfoPresenter @Inject constructor(): BasePresenter<LoginInfoView>(){
    @Inject
    lateinit var mLoginInfoService : LoginInfoService

    fun test(){
        mView.testfromlogin(mLoginInfoService.test())
    }




    fun login(loginName:String,passWord:String){
        mLoginInfoService.getData(loginName,passWord).execute(object :BaseObserver<LoginInfoBean>() {

            override fun onNext(t: LoginInfoBean) {
                super.onNext(t)

                mView.returnDatafromLogin(t)
            }
        },lifecycleProvider)
    }
}