package com.villejuif.fdjfrontparissportifs.main

import com.villejuif.fdjfrontparissportifs.data.Team

interface MainContract {
    interface View{
        fun onTeams()
    }

    interface Presenter{
        fun searchAllTeams(filter:String)
        fun cleanUpJob()
    }
}