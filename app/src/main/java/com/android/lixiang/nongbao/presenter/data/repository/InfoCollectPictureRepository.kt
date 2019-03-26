package com.android.lixiang.nongbao.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.nongbao.presenter.data.api.ApiService
import com.android.lixiang.nongbao.presenter.data.bean.DetailBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectPictureBean
import com.android.lixiang.nongbao.presenter.data.bean.InsertCollectingPictureBean
import io.reactivex.Observable
import java.io.File
import java.util.logging.Logger
import javax.inject.Inject

class InfoCollectPictureRepository @Inject constructor() {

    fun getDataFromRepository(s1: String, s2: Array<File>): Observable<InsertCollectingPictureBean> {
        return RetrofitFactory("http://202.111.178.10:34567/nb_back/").create(ApiService::class.java).insertCollectingPicture(s1, s2)
    }
}