package com.villejuif.fdjfrontparissportifs.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.villejuif.fdjfrontparissportifs.data.model.LeagueModel
import com.villejuif.fdjfrontparissportifs.data.model.Team
import com.villejuif.fdjfrontparissportifs.network.DataRepository
import com.villejuif.fdjfrontparissportifs.network.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

enum class TeamsStatus {LOADING, ERROR, EMPTY, DONE}

class MainPresenter constructor(val mView: MainContract.View) : CoroutineScope,
    MainContract.Presenter {

    private val TAG = MainPresenter::class.java.simpleName

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    private val _leagues = MutableLiveData<List<LeagueModel?>>()

    private val _leaguesNames: LiveData<List<String>> =
        Transformations.map(_leagues) { leagues ->

            if(leagues.isNullOrEmpty())  listOf()
            else{
                leagues.asSequence().filter {
                    SOCCER.equals(it?.strSport, ignoreCase = true)
                }.mapNotNull { it?.strLeague }.toList()
            }
        }


    val leaguesNames: LiveData<List<String>> = _leaguesNames

    private val _teams = MutableLiveData<List<Team?>>()
    val teams: LiveData<List<Team?>> = _teams

    private val _clickedTeam = MutableLiveData<Team>()
    val clickedTeam: LiveData<Team> = _clickedTeam

    private val _status = MutableLiveData<TeamsStatus>()
    val status: LiveData<TeamsStatus> = _status

    init {
        _status.value = TeamsStatus.DONE
        getAllTheLeagueForAutoComplete()
    }

    override fun searchAllTeams(filter: String) {
        _status.value = TeamsStatus.LOADING
        launch {
            withContext(Dispatchers.Main) {

                try {
                    val result = DataRepository.searchAllTeamsAsync(filter)

                    if(result is Result.Success){
                        _teams.value = result.data
                        Log.d(TAG, "$filter Teams: ${result.data?.size}")
                    }

                    _status.value = if(_teams.value?.size == 0) TeamsStatus.EMPTY
                    else TeamsStatus.DONE

                }catch (e: Exception){
                    Log.e(TAG, "searchAllTeams: ${e.message}")

                    _status.value = TeamsStatus.ERROR
                }

                mView.onTeams()
            }
        }
    }

    override fun cleanUpJob() {
        job.cancel()
    }

    fun onClickTeam(team: Team) {
        _clickedTeam.value = team
    }

    private fun getAllTheLeagueForAutoComplete() {
        launch {
            withContext(Dispatchers.IO){

                try {
                    val result = DataRepository.getAllLeaguesAsync()

                    if(result is Result.Success){

                        _leagues.postValue(result.data)
                        Log.d(TAG, "All the leagues: ${result.data?.size}")

                    }else return@withContext

                }catch (e: Exception){
                    _status.postValue(TeamsStatus.ERROR)
                    Log.e(TAG, "All the leagues: ${e.message}")
                }

            }
        }
    }

    companion object {
        const val SOCCER = "Soccer"
    }

}