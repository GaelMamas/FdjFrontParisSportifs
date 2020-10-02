package com.villejuif.fdjfrontparissportifs.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.villejuif.fdjfrontparissportifs.R
import com.villejuif.fdjfrontparissportifs.network.TheSportsDBApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFragment:Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        GlobalScope.launch {
            val teams = TheSportsDBApi.retrofitService.searchAllTeams("French Ligue 1").await().teams

            Log.d("MainFragment", "Ligue 1 Teams: ${teams?.size}")
        }

        GlobalScope.launch {
            val team = TheSportsDBApi.retrofitService.searchTeams("Paris SG").await()

            Log.d("MainFragment", "PSG Stadium: ${team.strStadiumDescription}")
        }
    }
}