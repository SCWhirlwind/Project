package com.example.projectmobile

import android.util.Log
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object DataRepository {
    private val twitchApi: TwitchApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.twitch.tv/helix/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        twitchApi = retrofit.create(TwitchApi::class.java)
    }

    fun getGameResults(
        onSuccess: (gameResults: List<Data>) -> Unit,
        onError: () -> Unit
    ) {
        twitchApi.getGameResult()
            .enqueue(object: Callback<GetDataResponse> {
                override fun onFailure(call: Call<GetDataResponse>, t: Throwable) {
                    Log.d("GameRepository", t.message.toString())
                    onError.invoke()
                }

                override fun onResponse(
                    call: Call<GetDataResponse>,
                    response: Response<GetDataResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        val responseBody = response.body()

                        if (responseBody != null)
                        {
                            onSuccess.invoke(responseBody.data)
                        }
                        else
                        {
                            Log.d("GameRepository", "Response was successful, but no body")
                            onError.invoke()
                        }
                    }
                }
            })
    }

    fun getStreamResults(
        game_id: String,
        onSuccess: (streamResults: List<Stream>) -> Unit,
        onError: () -> Unit
    ) {
        twitchApi.getStreamResult(game_id = game_id)
            .enqueue(object: Callback<GetStreamResponse> {
                override fun onFailure(call: Call<GetStreamResponse>, t: Throwable) {
                    Log.d("DataRepository", t.message.toString())
                    onError.invoke()
                }

                override fun onResponse(
                    call: Call<GetStreamResponse>,
                    response: Response<GetStreamResponse>
                ) {
                    if(response.isSuccessful)
                    {
                        val responseBody = response.body()

                        if (responseBody != null)
                        {
                            onSuccess.invoke(responseBody.stream)
                        }
                        else
                        {
                            Log.d("DataRepository", "Response was successful, but no body")
                            onError.invoke()
                        }
                    }
                }
            })
    }
}