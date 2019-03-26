package com.android.lixiang.nongbao.presenter.injection.module

import com.android.lixiang.nongbao.service.InfoCollectEntryService
import com.android.lixiang.nongbao.service.InfoCollectService
import com.android.lixiang.nongbao.service.LoginInfoService
import com.android.lixiang.nongbao.service.impl.InfoCollectEntryServiceImpl
import com.android.lixiang.nongbao.service.impl.InfoCollectServiceImpl
import com.android.lixiang.nongbao.service.impl.LoginInfoServiceImpl
import dagger.Module
import dagger.Provides
@Module
class LoginInfoModule {

    @Provides
    fun provideInfoCollectService(service: LoginInfoServiceImpl): LoginInfoService {
        return service
    }
}