package com.android.lixiang.nongbao.presenter.injection.module

import com.android.lixiang.nongbao.service.InfoCollectEntryService
import com.android.lixiang.nongbao.service.impl.InfoCollectEntryServiceImpl

import dagger.Module
import dagger.Provides

@Module
class InfoCollectEntryModule {

    @Provides
    fun provideInfoCollectEntryService(service: InfoCollectEntryServiceImpl): InfoCollectEntryService{
        return service
    }
}
