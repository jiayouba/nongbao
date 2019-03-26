package com.android.lixiang.nongbao.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.nongbao.presenter.data.api.ApiService
import com.android.lixiang.nongbao.presenter.data.bean.DetailBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectBean
import com.android.lixiang.nongbao.presenter.data.bean.LoginInfoBean
import io.reactivex.Observable
import java.util.logging.Logger
import javax.inject.Inject

class LoginInfoRepository @Inject constructor() {
    fun getDataFromRepository(s1: String, s2: String): Observable<LoginInfoBean> {
        return RetrofitFactory("http://202.111.178.10:34567/").create(ApiService::class.java).login(s1, s2)
    }

//    fun getDataFromRepository3(s1: String, s2: String, s3: String, s4: String, s5: String, s6: String, s7: String, s8: String, s9: String, s10: String): Observable<InfoCollectBean> {
//        return RetrofitFactory("http://202.111.178.10:34567/nb_back/").create(ApiService::class.java).infoCollect(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10)
//    }
}