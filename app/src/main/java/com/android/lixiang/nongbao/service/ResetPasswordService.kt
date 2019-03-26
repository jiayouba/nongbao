package com.android.lixiang.nongbao.service

import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectPictureBean
import com.android.lixiang.nongbao.presenter.data.bean.LoginInfoBean
import com.android.lixiang.nongbao.presenter.data.bean.ResetPasswordBean
import io.reactivex.Observable
import java.util.*

interface ResetPasswordService {
    fun test(): String
    fun getData(oldPassword:String,newPassWord:String): Observable<ResetPasswordBean>
}