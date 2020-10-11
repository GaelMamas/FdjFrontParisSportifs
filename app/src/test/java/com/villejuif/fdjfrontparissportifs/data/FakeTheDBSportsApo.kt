package com.villejuif.fdjfrontparissportifs.data

import com.villejuif.fdjfrontparissportifs.data.model.LeagueModel
import com.villejuif.fdjfrontparissportifs.data.model.Team
import com.villejuif.fdjfrontparissportifs.network.BaseDataProvider
import com.villejuif.fdjfrontparissportifs.network.Result

class FakeTheDBSportsApo(val errorTrigger:Boolean):BaseDataProvider() {

    private fun searchAllTeamsAsync():List<Team?>{
        return emptyList()
    }

    private fun searchTeamsAsync():List<Team?>{
        return listOf(Team("ideateam",
            null,null, null,
        "team" ),
            Team("ideateamBis",
                null,null, null,
                "team" ))
    }

    private fun getAllLeaguesAsync_():List<LeagueModel?>{
        return emptyList()
    }

    override suspend fun searchAllTeamsAsync(league: String): Result<List<Team?>?> {
        return if(errorTrigger){
            Result.Error(Exception("Fake Exception"))
        }else{
            Result.Success(searchAllTeamsAsync())
        }
    }

    override suspend fun searchTeamsAsync(team: String): Result<List<Team?>?> {
        return if(errorTrigger){
            Result.Error(Exception("Fake Exception"))
        }else{
            Result.Success(searchTeamsAsync())
        }
    }

    override suspend fun getAllLeaguesAsync():Result<List<LeagueModel?>>{
        return if(errorTrigger){
            Result.Error(Exception("Fake Exception"))
        }else{
            Result.Success(getAllLeaguesAsync_())
        }
    }

}