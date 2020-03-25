package com.l.wikipediasearch.database

import io.realm.RealmList
import io.realm.RealmObject

open class QueryEntity : RealmObject() {
    var pages: RealmList<SearchPageEntity>? = null
    var redirects: RealmList<RedirectEntity>? = null

}