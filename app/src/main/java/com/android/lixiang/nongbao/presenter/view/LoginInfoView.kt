package com.android.lixiang.nongbao.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.nongbao.presenter.data.bean.DetailBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectPictureBean
import com.android.lixiang.nongbao.presenter.data.bean.LoginInfoBean

interface LoginInfoView : BaseView {
    fun testfromlogin(string:String)
    fun returnDatafromLogin(s :LoginInfoBean)

}