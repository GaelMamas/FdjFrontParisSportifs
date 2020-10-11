package com.villejuif.fdjfrontparissportifs.network

import com.villejuif.fdjfrontparissportifs.data.model.LeagueModel
import com.villejuif.fdjfrontparissportifs.data.model.Team

abstract class BaseDataProvider {
    abstract suspend fun searchAllTeamsAsync(league: String): Result<List<Team?>?>

    abstract suspend fun searchTeamsAsync(team: String): Result<List<Team?>?>

    abstract suspend fun getAllLeaguesAsync(): Result<List<LeagueModel?>?>
}