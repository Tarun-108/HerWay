package com.taruns.herway.data

import com.taruns.herway.models.RequestBody
import com.taruns.herway.models.ResponseJson
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface APIInterface {

    @POST("/routes")
    fun getResponse(@Body body: RequestBody): Call<ResponseJson?>?

}