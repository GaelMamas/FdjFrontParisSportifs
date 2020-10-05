package com.villejuif.fdjfrontparissportifs.depinjection

import com.villejuif.fdjfrontparissportifs.network.TheSportsDBApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideTheSportsDBApi() = TheSportsDBApi()
}