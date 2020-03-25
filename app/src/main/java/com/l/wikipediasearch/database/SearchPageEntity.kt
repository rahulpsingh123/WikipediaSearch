package com.l.wikipediasearch.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SearchPageEntity : RealmObject() {

    var thumbUrl: String? = null
        get() = thumbnail?.source

    var description: String? = null
        get() {
            if (terms == null) {
                return null
            }
            val desc = terms?.description
            return if (desc?.isEmpty() == true) {
                null
            } else desc?.first()
        }

    @PrimaryKey
    var pageid = 0
    var ns = 0
    var title: String? = null
    var index = 0
    var thumbnail: ThumbnailEntity? = null
    var terms: TermsEntity? = null
}