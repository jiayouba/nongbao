package com.android.lixiang.nongbao.presenter.injection.module

import com.android.lixiang.nongbao.service.InfoCollectEntryService
import com.android.lixiang.nongbao.service.InfoCollectService
import com.android.lixiang.nongbao.service.LoginInfoService
import com.android.lixiang.nongbao.service.ResetPasswordService
import com.android.lixiang.nongbao.service.impl.InfoCollectEntryServiceImpl
import com.android.lixiang.nongbao.service.impl.InfoCollectServiceImpl
import com.android.lixiang.nongbao.service.impl.LoginInfoServiceImpl
import com.android.lixiang.nongbao.service.impl.ResetPasswordServiceImpl
import dagger.Module
import dagger.Provides
@Module
class ResetPasswordModule {

    @Provides
    fun provideInfoCollectService(service: ResetPasswordServiceImpl): ResetPasswordService {
        return service
    }
}