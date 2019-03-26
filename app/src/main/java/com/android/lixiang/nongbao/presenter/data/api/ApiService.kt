package com.android.lixiang.nongbao.presenter.data.api

import com.android.lixiang.nongbao.presenter.data.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface ApiService {
    @POST("/global/programming/detail")
    @FormUrlEncoded
    fun detail(@Field("area") targetSentence: String,
               @Field("productType") targetSentence2: String): Observable<DetailBean>

    @POST("/nb_back/collectingInformation/insertCollectingInformation")
    @FormUrlEncoded
    fun infoCollect(@Field("loginNameId") targetSentence: String,
                    @Field("insurer") targetSentence3: String,
                    @Field("identityType") targetSentence4: String,
                    @Field("idNumber") targetSentence5: String,
                    @Field("phoneNumber") targetSentence7: String,
                    @Field("plantingPlace") targetSentence8: String,
                    @Field("insuranceAmount") targetSentence9: String,
                    @Field("shape") targetSentence10: String,
                    @Field("city_name") targetSentence11: String,
                    @Field("county_name") targetSentence12: String,
                    @Field("town_name") targetSentence13: String,
                    @Field("village_name") targetSentence14: String
    ): Observable<InfoCollectBean>

//    @POST("/collectingInformation/selectCollectingInformation")
//    @FormUrlEncoded
//    fun infoCollectPicture(@Field("informationId") targetSentence: String,
//                           @Field("collectingPictureNames") targetSentence2: String): Observable<InfoCollectPictureBean>


    @POST("/nb_back/collectingInformation/insertCollectingPicture")
    @Multipart
    fun insertCollectingPicture(@Part("informationId") targetSentence: String,
                                @Part("collectingPictureNames") targetSentence2: Array<File>): Observable<InsertCollectingPictureBean>

    @POST("/nb_back/collectingInformation/insertCollectingPicture")
    @Multipart
    fun testInsertCollectingPicture(@Part("informationId") targetSentence: String,
                                    @Part("collectingPictureNames") targetSentence2: File): Observable<InsertCollectingPictureBean>


    @POST("/nb_back/collectingInformation/insertCollectingPicture")
    @Multipart
    fun testInsertCollectingPicture2(@Part("informationId") targetSentence: String,
                                     @Part targetSentence2: MultipartBody.Part): Observable<InsertCollectingPictureBean>

    @POST("/nb_back/collectingInformation/insertCollectingPicture")
    @Multipart
    fun InsertCollectingPictureOffical(@Query("informationId") targetSentence: String,
                                       @Part targetSentence2: List<MultipartBody.Part>): Observable<InsertCollectingPictureBean>

    @POST("/nb_back/login")
    @FormUrlEncoded
    fun login(@Field("loginName") targetSentence: String,
              @Field("passWord") targetSentence2: String): Observable<LoginInfoBean>

    @POST("/nb_back/user/updatePassword")
    @FormUrlEncoded
    fun updatePassword(@Field("oldPassword") targetSentence: String,
                       @Field("newPassword") targetSentence2: String): Observable<ResetPasswordBean>

//    @POST("/nb_back/collectingInformation/selectCollectingInformation")
//    @FormUrlEncoded
//    fun selectCollectingPicture() : Observable<ReturnInfoBean>


}
