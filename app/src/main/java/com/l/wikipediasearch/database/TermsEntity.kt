package com.l.wikipediasearch.database

import io.realm.RealmList
import io.realm.RealmObject

open class TermsEntity : RealmObject() {
    var description: RealmList<String>? = null
}