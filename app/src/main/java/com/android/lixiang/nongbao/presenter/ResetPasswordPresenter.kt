package com.android.lixiang.nongbao.presenter

import com.android.lixiang.base.ext.execute
import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.base.rx.BaseObserver
import com.android.lixiang.nongbao.presenter.data.bean.*
import com.android.lixiang.nongbao.presenter.view.*
import com.android.lixiang.nongbao.service.*
import com.android.lixiang.nongbao.service.impl.InfoCollectServiceImpl
import java.util.logging.Logger
import javax.inject.Inject

class ResetPasswordPresenter @Inject constructor(): BasePresenter<ResetPasswordView>(){
    @Inject
    lateinit var mResetPasswordService : ResetPasswordService

    fun test(){
        mView.testfromresetpassword(mResetPasswordService.test())
    }




    fun updatePassword(oldPassword:String,newPassword:String){
        mResetPasswordService.getData(oldPassword,newPassword).execute(object :BaseObserver<ResetPasswordBean>() {

            override fun onNext(t: ResetPasswordBean) {
                super.onNext(t)

                mView.returnDatafromresetpassword(t)
            }
        },lifecycleProvider)
    }
}