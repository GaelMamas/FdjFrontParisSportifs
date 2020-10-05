package com.villejuif.fdjfrontparissportifs

import android.app.Application
import com.villejuif.fdjfrontparissportifs.network.DataRepository
import com.villejuif.fdjfrontparissportifs.network.RemoteDataProvider
import com.villejuif.fdjfrontparissportifs.network.TheSportsDBApi

class FdjApplication:Application() {

    lateinit var mDataRepository: DataRepository

    override fun onCreate() {
        super.onCreate()

        mDataRepository = DataRepository(RemoteDataProvider(TheSportsDBApi()))
    }
}