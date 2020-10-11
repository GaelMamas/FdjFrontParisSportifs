package com.villejuif.fdjfrontparissportifs.data

import com.villejuif.fdjfrontparissportifs.data.model.LeagueModel
import com.villejuif.fdjfrontparissportifs.data.model.Team
import com.villejuif.fdjfrontparissportifs.network.DataRepository
import com.villejuif.fdjfrontparissportifs.network.Result

class FakeDataRepository(private val fakeData: FakeTheDBSportsApo):DataRepository(fakeData) {

    override suspend fun searchAllTeamsAsync(league:String): Result<List<Team?>?> {
        val result = fakeData.searchAllTeamsAsync(league)

        if(result is Result.Error)
            throw result.exception

        return result
    }

    override suspend fun searchTeamsAsync(team:String): Result<List<Team?>?> {
        val result = fakeData.searchTeamsAsync(team)

        if(result is Result.Error)
            throw result.exception

        return result
    }

    override suspend fun getAllLeaguesAsync(): Result<List<LeagueModel?>?> {
        val result = fakeData.getAllLeaguesAsync()

        if(result is Result.Error)
            throw result.exception

        return result
    }

}