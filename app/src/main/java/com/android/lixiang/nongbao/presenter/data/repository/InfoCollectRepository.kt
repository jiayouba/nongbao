package com.android.lixiang.nongbao.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.nongbao.presenter.data.api.ApiService
import com.android.lixiang.nongbao.presenter.data.bean.DetailBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectBean
import com.android.lixiang.nongbao.presenter.data.bean.InsertCollectingPictureBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File
import java.util.logging.Logger
import javax.inject.Inject

class InfoCollectRepository @Inject constructor() {
    fun getDataFromRepository(s1: String, s2: String): Observable<DetailBean> {
        return RetrofitFactory("http://59.110.164.214:8024/").create(ApiService::class.java).detail(s1, s2)
    }

    fun getDataFromRepository3(s1: String, s2: String, s3: String, s4: String, s5: String, s6: String, s7: String, s8: String, s9: String, s10: String, s11: String, s12: String): Observable<InfoCollectBean> {
        return RetrofitFactory("http://202.111.178.10:34567/nb_back/").create(ApiService::class.java).infoCollect(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12)
    }

    fun insertCollectingPicture(s1: String, s2: Array<File>): Observable<InsertCollectingPictureBean> {
        return RetrofitFactory("http://202.111.178.10:34567/nb_back/").create(ApiService::class.java).insertCollectingPicture(s1, s2)
    }

    fun testInsertCollectingPicture(s1: String, s2: File): Observable<InsertCollectingPictureBean> {
        return RetrofitFactory("http://202.111.178.10:34567/nb_back/").create(ApiService::class.java).testInsertCollectingPicture(s1, s2)
    }

    fun testInsertCollectingPicture2(s1: String, s2: MultipartBody.Part): Observable<InsertCollectingPictureBean> {
        return RetrofitFactory("http://202.111.178.10:34567/nb_back/").create(ApiService::class.java).testInsertCollectingPicture2(s1, s2)
    }

    fun InsertCollectingPictureOffical(s1: String, s2: List<MultipartBody.Part>): Observable<InsertCollectingPictureBean> {
        return RetrofitFactory("http://202.111.178.10:34567/nb_back/").create(ApiService::class.java).InsertCollectingPictureOffical(s1, s2)
    }
}