package com.example.lenskart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lenskart.model.RepoModel

class DetailViewModel : ViewModel {

    private var mutableLiveData: MutableLiveData<RepoModel>? = null

    private var itemRepository: DetailRepository? = null

    constructor(map:MutableMap<String, String>) {
        if (mutableLiveData != null) {
            return
        }
        itemRepository = DetailRepository.getinstance()
        mutableLiveData = itemRepository?.getDetails(map)
    }


    fun getDetails(): MutableLiveData<RepoModel>? {

        if (mutableLiveData != null) {
            mutableLiveData
        }
        return mutableLiveData
    }

    class ViewModelFactory(val map:MutableMap<String, String>) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(map) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }

    }
}