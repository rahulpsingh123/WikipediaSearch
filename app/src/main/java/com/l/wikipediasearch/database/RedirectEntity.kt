package com.l.wikipediasearch.database

import io.realm.RealmObject

open class RedirectEntity : RealmObject() {
    var index = 0
    var from: String? = null
    var to: String? = null

}