package com.l.wikipediasearch.viewModel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm

abstract class BaseViewModel: ViewModel() {

    val realm: Realm = Realm.getDefaultInstance()
    val subscriptions = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        realm.close()
        subscriptions.dispose()
    }
}