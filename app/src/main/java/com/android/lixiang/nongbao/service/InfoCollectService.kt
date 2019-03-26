package com.android.lixiang.nongbao.service

import com.android.lixiang.nongbao.presenter.data.bean.DetailBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectBean
import com.android.lixiang.nongbao.presenter.data.bean.InsertCollectingPictureBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface InfoCollectService {
    fun test(): String
    fun getData(area: String, productType: String): DetailBean
    fun getData2(area: String, productType: String): Observable<DetailBean>
    fun getData3(loginNameId: String,
                 plotNumber: String,
                 insurer: String,
                 identityType: String,
                 idNumber: String,
                 phoneNumber: String,
                 plantingPlace: String,
                 insuranceAmount: String,
                 shape: String,
                 shi: String,
                 xian: String,
                 xiang: String): Observable<InfoCollectBean>
    fun insertCollectingPicture(informationId: String, collectingPictureNames: Array<File>): Observable<InsertCollectingPictureBean>
    fun testInsertCollectingPicture(informationId: String, collectingPictureNames: File): Observable<InsertCollectingPictureBean>
    fun testInsertCollectingPicture(informationId: String, collectingPictureNames: MultipartBody.Part): Observable<InsertCollectingPictureBean>
    fun InsertCollectingPictureOffical(informationId: String, collectingPictureNames: List<MultipartBody.Part>): Observable<InsertCollectingPictureBean>

}