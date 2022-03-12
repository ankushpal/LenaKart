package com.example.lenskart.network

import com.example.lenskart.model.RepoModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("/search/repositories")
    fun getRepos(@QueryMap map: Map<String, String>): Call<RepoModel>

//    @GET("users?page=1")
//    fun getDetails(): Call<DetailsDataResponse>
}