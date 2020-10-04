package com.villejuif.fdjfrontparissportifs.network

import com.villejuif.fdjfrontparissportifs.data.model.LeagueModel
import com.villejuif.fdjfrontparissportifs.data.model.Team

object DataRepository {

    suspend fun searchAllTeamsAsync(league:String):Result<List<Team?>?>{
        val result = RemoteDataProvider.searchAllTeamsAsync(league)

         if(result is Result.Error)
            throw result.exception

        return result
    }

    suspend fun searchTeamsAsync(team:String):Result<List<Team?>?>{
        val result = RemoteDataProvider.searchTeamsAsync(team)

        if(result is Result.Error)
            throw result.exception

        return result
    }

    suspend fun getAllLeaguesAsync():Result<List<LeagueModel?>?>{
        val result = RemoteDataProvider.getAllLeaguesAsync()

        if(result is Result.Error)
            throw result.exception

        return result
    }


}