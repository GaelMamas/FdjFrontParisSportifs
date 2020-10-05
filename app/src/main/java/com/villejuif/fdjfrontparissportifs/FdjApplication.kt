package com.villejuif.fdjfrontparissportifs

import android.app.Application
import com.villejuif.fdjfrontparissportifs.depinjection.DaggerFdjAppComponent
import com.villejuif.fdjfrontparissportifs.depinjection.FdjAppComponent

class FdjApplication : Application() {

    val appComponent:FdjAppComponent by lazy {
        DaggerFdjAppComponent.factory().create(applicationContext)
    }

}