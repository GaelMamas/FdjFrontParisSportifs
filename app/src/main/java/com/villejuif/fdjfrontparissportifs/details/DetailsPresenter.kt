package com.villejuif.fdjfrontparissportifs.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.villejuif.fdjfrontparissportifs.data.model.Team
import com.villejuif.fdjfrontparissportifs.network.DataRepository
import com.villejuif.fdjfrontparissportifs.network.Result
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DetailsPresenter @Inject constructor(private val mDataRepository: DataRepository
) : DetailsContract.Presenter, CoroutineScope {

    private val TAG = DetailsPresenter::class.java.simpleName

    private lateinit var mView:DetailsContract.View

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    private val _team = MutableLiveData<Team?>()
    val team: LiveData<Team?> = _team

    override fun searchTeam(teamName: String, idTeam: String?) {
        launch {
            withContext(Dispatchers.Main) {

                try {
                    val result = mDataRepository.searchTeamsAsync(teamName)

                    if (result is Result.Success) {
                        val teams = result.data

                        val searchedTeam = teams?.filter {
                            return@filter idTeam?.equals(it?.idTeam, ignoreCase = true) ?: false
                        }?.first()

                        searchedTeam?.let {
                            Log.d(TAG, "$teamName: ${it.strDescriptionFR}")
                            _team.value = it
                            mView.onTeam()
                        }

                    } else return@withContext

                } catch (e: Exception) {
                    Log.e(TAG, "search a Team: ${e.message}")
                }

            }
        }
    }

    fun initView(view:DetailsContract.View){
        mView = view
    }

    override fun cleanUpJob() {
        job.cancel()
    }
}