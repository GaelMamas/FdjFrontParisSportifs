package com.villejuif.fdjfrontparissportifs.depinjection

import com.villejuif.fdjfrontparissportifs.network.BaseDataProvider
import com.villejuif.fdjfrontparissportifs.network.RemoteDataProvider
import com.villejuif.fdjfrontparissportifs.network.TheSportsDBApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideTheSportsDBApi() = TheSportsDBApi()

    @Singleton
    @Provides
    fun provideRemoteDataProvider(theSportsDBApi: TheSportsDBApi)
            = RemoteDataProvider(theSportsDBApi) as BaseDataProvider
}