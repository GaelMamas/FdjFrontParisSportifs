package com.villejuif.fdjfrontparissportifs.details

interface DetailsContract {
    interface View{
        fun onTeam()
    }

    interface Presenter{
        fun searchTeam(teamName:String, idTeam: String?)
        fun cleanUpJob()
    }
}