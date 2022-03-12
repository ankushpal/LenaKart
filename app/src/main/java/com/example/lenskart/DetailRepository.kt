package com.example.lenskart

import androidx.lifecycle.MutableLiveData
import com.example.lenskart.model.RepoModel
import com.example.lenskart.network.ApiInterface
import com.example.lenskart.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRepository {
    companion object {
        private var instance: DetailRepository? = null

        fun getinstance(): DetailRepository {
            if (instance == null) {
                instance = DetailRepository()
            }

            return instance as DetailRepository
        }
    }
    fun getDetails(map:MutableMap<String, String>): MutableLiveData<RepoModel> {
        val detailsdata = MutableLiveData<RepoModel>()

        val detailsDataActivity: ApiInterface = RetrofitClient.apiInterface
        detailsDataActivity.getRepos(map)
            .enqueue(object : Callback<RepoModel> {
                override fun onResponse(call: Call<RepoModel>, response: Response<RepoModel>?) {
                    val itemItem = response?.body()
                    if(response!=null && response.code()==200)
                    {
                        detailsdata.value = itemItem
                        detailsdata
                    }
                    else
                    {
                        detailsdata.value = null
                    }
                }

                override fun onFailure(call: Call<RepoModel>?, t: Throwable) {
                    detailsdata.value = null
                }

            })
        return detailsdata
    }
}