package com.villejuif.fdjfrontparissportifs.depinjection

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, SubComponentsModule::class])
interface FdjAppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): FdjAppComponent
    }

    fun mainComponent(): MainComponent.Factory

    fun detailsComponent(): DetailsComponent.Factory
}