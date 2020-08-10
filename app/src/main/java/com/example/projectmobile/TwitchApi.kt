package com.example.projectmobile

import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TwitchApi {

    @GET("games/top")
    @Headers("Client-ID: 3j1uw3eb9e1y9z6m38sl27dlgy93hi",
        "Authorization: Bearer ijbcun0fwi6zz3hsx8xkf3wzo8ax10")
    fun getGameResult(
    ) : Call<GetDataResponse>

    @GET("streams?")
    @Headers("Client-ID: 3j1uw3eb9e1y9z6m38sl27dlgy93hi",
        "Authorization: Bearer ijbcun0fwi6zz3hsx8xkf3wzo8ax10")
    fun getStreamResult(
        @Query("game_id") game_id: String
    ) : Call<GetStreamResponse>
}