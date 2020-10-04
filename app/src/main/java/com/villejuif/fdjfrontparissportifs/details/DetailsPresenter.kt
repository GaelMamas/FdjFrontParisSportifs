package com.villejuif.fdjfrontparissportifs.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.villejuif.fdjfrontparissportifs.data.model.Team
import com.villejuif.fdjfrontparissportifs.network.TheSportsDBApi
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailsPresenter(val mView: DetailsContract.View): DetailsContract.Presenter, CoroutineScope{

    private val TAG = DetailsPresenter::class.java.simpleName

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    private val _team = MutableLiveData<Team?>()
    val team: LiveData<Team?> = _team

    override fun searchTeam(teamName: String, idTeam: String?) {
        launch {
            withContext(Dispatchers.Main) {
                val teams = TheSportsDBApi.retrofitService.searchTeamsAsync(teamName).await().teams

                val searchedTeam = teams?.filter {
                    return@filter idTeam?.equals(it?.idTeam, ignoreCase = true) ?: false
                }?.first()

                searchedTeam?.let {
                    Log.d(TAG, "$teamName: ${it.strDescriptionFR}")
                    _team.value = it
                    mView.onTeam()
                }

            }
        }
    }

    override fun cleanUpJob() {
        job.cancel()
    }
}