package com.android.lixiang.nongbao.presenter.injection.module

import com.android.lixiang.nongbao.service.InfoCollectEntryService
import com.android.lixiang.nongbao.service.InfoCollectService
import com.android.lixiang.nongbao.service.impl.InfoCollectEntryServiceImpl
import com.android.lixiang.nongbao.service.impl.InfoCollectServiceImpl
import dagger.Module
import dagger.Provides
@Module
class InfoCollectModule {

    @Provides
    fun provideInfoCollectService(service: InfoCollectServiceImpl): InfoCollectService {
        return service
    }
}