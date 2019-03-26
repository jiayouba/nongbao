package com.android.lixiang.nongbao.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.nongbao.presenter.data.api.ApiService
import com.android.lixiang.nongbao.presenter.data.bean.DetailBean
import io.reactivex.Observable
import javax.inject.Inject

class InfoCollectEntryRepository @Inject constructor() {
    fun getDataFromRepository(s1: String, s2: String): Observable<DetailBean> {
        return RetrofitFactory("http://59.110.164.214:8024/").create(ApiService::class.java).detail(s1, s2)
    }
}