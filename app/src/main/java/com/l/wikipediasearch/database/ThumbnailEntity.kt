package com.l.wikipediasearch.database

import io.realm.RealmObject

open class ThumbnailEntity : RealmObject() {
    var source: String? = null
    var width = 0
    var height = 0

}