package com.villejuif.fdjfrontparissportifs.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.villejuif.fdjfrontparissportifs.data.Team
import com.villejuif.fdjfrontparissportifs.network.TheSportsDBApi
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainPresenter constructor(val mView: MainContract.View) : CoroutineScope, MainContract.Presenter {

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    private val _teams = MutableLiveData<List<Team?>?>()
    val teams:LiveData<List<Team?>?> = _teams

    override fun searchAllTeams(filter: String) {
        launch {
            withContext(Dispatchers.Main) {
                _teams.value = TheSportsDBApi.retrofitService.searchAllTeams(filter).await().teams
                Log.d("MainFragment", "Ligue 1 Teams: ${_teams.value?.size}")
                mView.onTeams()
            }
        }
    }

    override fun cleanUpJob(){
        job.cancel()
    }
}