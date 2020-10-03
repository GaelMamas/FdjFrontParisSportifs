package com.villejuif.fdjfrontparissportifs.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.villejuif.fdjfrontparissportifs.data.model.Team
import com.villejuif.fdjfrontparissportifs.data.model.LeagueModel
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory.*
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface TheSportsDBApiService {
@GET("search_all_teams.php")
fun searchAllTeams(@Query("l") league:String):Deferred<LeagueModel>

    @GET("searchteams.php")
    fun searchTeams(@Query("t") team:String):Deferred<LeagueModel>
}

object TheSportsDBApi{
    val retrofitService : TheSportsDBApiService by lazy { retrofit.create(TheSportsDBApiService::class.java) }
}