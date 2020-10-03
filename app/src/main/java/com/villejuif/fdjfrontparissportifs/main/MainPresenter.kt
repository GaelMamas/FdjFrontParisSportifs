package com.villejuif.fdjfrontparissportifs.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.villejuif.fdjfrontparissportifs.data.model.Team
import com.villejuif.fdjfrontparissportifs.network.TheSportsDBApi
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainPresenter constructor(val mView: MainContract.View) : CoroutineScope, MainContract.Presenter {

    private val TAG = MainPresenter::class.java.simpleName

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    private val _teams = MutableLiveData<List<Team?>?>()
    val teams:LiveData<List<Team?>?> = _teams

    private val _clickedTeam = MutableLiveData<Team>()
    val clickedTeam: LiveData<Team> = _clickedTeam

    override fun searchAllTeams(filter: String) {
        launch {
            withContext(Dispatchers.Main) {
                _teams.value = TheSportsDBApi.retrofitService.searchAllTeams(filter).await().teams
                Log.d(TAG, "$filter Teams: ${_teams.value?.size}")
                mView.onTeams()
            }
        }
    }

    override fun cleanUpJob(){
        job.cancel()
    }

    fun onClickEvent(team: Team){
        _clickedTeam.value = team
    }
}