package com.android.lixiang.nongbao.service

import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectPictureBean
import com.android.lixiang.nongbao.presenter.data.bean.LoginInfoBean
import io.reactivex.Observable
import java.util.*

interface LoginInfoService {
    fun test(): String
    fun getData(loginName:String,passWord:String): Observable<LoginInfoBean>
}