package com.villejuif.fdjfrontparissportifs.depinjection

import com.villejuif.fdjfrontparissportifs.details.DetailsActivity
import dagger.Subcomponent

@Subcomponent
interface DetailsComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():DetailsComponent
    }

    fun inject(detailsActivity: DetailsActivity)

}