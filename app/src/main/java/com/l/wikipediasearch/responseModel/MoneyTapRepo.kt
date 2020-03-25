package com.l.wikipediasearch.responseModel

import com.l.wikipediasearch.network.APIManager
import com.l.wikipediasearch.network.NetworkClient
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MoneyTapRepo : BaseRepo() {

    fun getWikipediaResult(searchTerm: MutableMap<String?, Any?>): Single<String>? {
        return APIManager.instance?.searchApi(searchTerm)?.let {
            NetworkClient.getResult(it).subscribeOn(Schedulers.io())
        }
    }
}