package com.l.wikipediasearch.viewModel


import com.l.wikipediasearch.database.SearchPageEntity
import com.l.wikipediasearch.responseModel.MoneyTapRepo
import com.l.wikipediasearch.responseModel.QueryAlreadyInProgress
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.RealmResults
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

class MoneyTapViewModel : BaseViewModel() {
    private val repo = MoneyTapRepo()
    private var queryInProgress = false

    fun getWikiResultBaseOnSearchTerm(searchTerm: String): Single<List<SearchPageEntity>?>? {
        return when {
            queryInProgress -> Single.error(QueryAlreadyInProgress())
            else -> {
                queryInProgress = true
                val queryMap: MutableMap<String?, Any?> = HashMap()
                queryMap["action"] = "query"
                queryMap["formatversion"] = 2
                queryMap["prop"] = "pageimages|pageterms"
                queryMap["pilimit"] = 10
                queryMap["piprop"] = "thumbnail"
                queryMap["wbptterms"] = "description"
                queryMap["redirects"] = 1
                queryMap["format"] = "json"
                queryMap["generator"] = "prefixsearch"
                queryMap["gpssearch"] = searchTerm
                queryMap["gpslimit"] = 10

                repo.getWikipediaResult(queryMap)
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.map {
                        handleResponse(it)
                    }
                    ?.doFinally {
                        queryInProgress = false
                    }
            }
        }
    }

    private fun handleResponse(body: String): List<SearchPageEntity>? {
        val searchPageEntities: MutableList<SearchPageEntity> = mutableListOf()
        val queryObject = JSONObject(body).optJSONObject("query") ?: JSONObject()
        val pages = queryObject.optJSONArray("pages") ?: JSONArray()
        realm.executeTransaction {
            for (i in 0 until pages.length()) {
                val searchPageEntity: SearchPageEntity =
                    it.createOrUpdateObjectFromJson(
                        SearchPageEntity::class.java,
                        pages.optJSONObject(i)
                    )
                searchPageEntities.add(searchPageEntity)
            }
        }

        return searchPageEntities
    }

    fun getAllSearchResult(): RealmResults<SearchPageEntity>? {
        return realm.where(SearchPageEntity::class.java).findAll()
    }

    fun deleteResult() {
        realm.executeTransaction {
            it.deleteAll()
        }
    }

    override fun onCleared() {
        super.onCleared()
        repo.clear()
    }
}