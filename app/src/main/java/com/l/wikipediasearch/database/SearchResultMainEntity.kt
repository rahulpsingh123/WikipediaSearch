package com.l.wikipediasearch.database

import io.realm.RealmObject

open class SearchResultMainEntity : RealmObject() {
    var isBatchcomplete = false
    var query: QueryEntity? = null

}