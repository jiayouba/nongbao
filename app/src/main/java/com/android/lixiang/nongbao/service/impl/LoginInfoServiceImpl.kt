package com.android.lixiang.nongbao.service.impl

import com.android.lixiang.base.net.RestClient
import com.android.lixiang.nongbao.presenter.data.bean.DetailBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectPictureBean
import com.android.lixiang.nongbao.presenter.data.bean.LoginInfoBean
import com.android.lixiang.nongbao.presenter.data.repository.InfoCollectEntryRepository
import com.android.lixiang.nongbao.presenter.data.repository.InfoCollectPictureRepository
import com.android.lixiang.nongbao.presenter.data.repository.InfoCollectRepository
import com.android.lixiang.nongbao.presenter.data.repository.LoginInfoRepository
import com.android.lixiang.nongbao.service.InfoCollectEntryService
import com.android.lixiang.nongbao.service.InfoCollectPictureService
import com.android.lixiang.nongbao.service.InfoCollectService
import com.android.lixiang.nongbao.service.LoginInfoService
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import java.util.*
import javax.inject.Inject

class LoginInfoServiceImpl @Inject constructor(): LoginInfoService {

    @Inject
    lateinit var loginInfoRepository : LoginInfoRepository


//    override fun getData2(area: String, productType: String): Observable<DetailBean> {
//        Logger.d(area)
//        Logger.d(productType)
//        return infoCollectRepository.getDataFromRepository(area, productType).flatMap(Function <DetailBean, ObservableSource<DetailBean>> { t ->
//            return@Function Observable.just(t)
//        })
//    }

    override fun getData(loginName: String, passWord: String): Observable<LoginInfoBean> {
//        Logger.d(loginNameId)
//        Logger.d(plotNumber)

        return loginInfoRepository.getDataFromRepository(loginName,passWord).flatMap(Function <LoginInfoBean,ObservableSource<LoginInfoBean>> { t ->
            return@Function Observable.just(t)
        })
    }

//    private var params: WeakHashMap<String, Any>? = WeakHashMap()
//    private var detailBean = DetailBean()

//    override fun getData(area: String, productType: String): DetailBean {
////        params!!["area"] = area
////        params!!["productType"] = productType
////
////        RestClient().builder()
////                .url("http://59.110.164.214:8024/global/programming/detail")
////                .params(params!!)
////                .success(object : ISuccess {
////                    override fun onSuccess(response: String) {
////                        detailBean = Gson().fromJson(response, DetailBean::class.java)
////                        Logger.d(response)
////
////                        if (detailBean.message == "success") {
////                        } else {
////                        }
////                    }
////                })
////                .error(object : IError {
////                    override fun onError(code: Int, msg: String) {}
////                })
////                .failure(object : IFailure {
////                    override fun onFailure() {}
////                })
////                .build()
////                .post()
////
//        return detailBean
//
//    }

    override fun test(): String {
        return "FFF"
    }
}