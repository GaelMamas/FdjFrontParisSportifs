package com.villejuif.fdjfrontparissportifs.depinjection

import com.villejuif.fdjfrontparissportifs.main.MainActivity
import dagger.Subcomponent

@AppScope
@Subcomponent
interface MainComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create():MainComponent
    }

    fun inject(mainActivity: MainActivity)
}