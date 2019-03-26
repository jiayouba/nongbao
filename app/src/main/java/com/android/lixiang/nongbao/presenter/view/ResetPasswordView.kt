package com.android.lixiang.nongbao.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.nongbao.presenter.data.bean.*

interface ResetPasswordView : BaseView {
    fun testfromresetpassword(string:String)
    fun returnDatafromresetpassword(s : ResetPasswordBean)

}