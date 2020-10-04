package com.villejuif.fdjfrontparissportifs.main

interface MainContract {
    interface View{
        fun onTeams()
    }

    interface Presenter{
        fun searchAllTeams(filter:String)
        fun cleanUpJob()
    }
}