package com.l.wikipediasearch.responseModel

import io.reactivex.disposables.CompositeDisposable

open class BaseRepo {
    private val subscriptions = CompositeDisposable()
    open fun clear(){
        subscriptions.dispose()
    }
}