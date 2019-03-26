package com.android.lixiang.nongbao.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.nongbao.presenter.data.bean.DetailBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectBean
import com.android.lixiang.nongbao.presenter.data.bean.InfoCollectPictureBean

interface InfoCollectPictureView : BaseView {
    fun testfromPic(string:String)
    fun returnDatafromPic(s :InfoCollectPictureBean)

}