package com.l.wikipediasearch

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.preference.PreferenceManager
import androidx.core.content.res.ResourcesCompat
import com.google.gson.Gson
import io.realm.Realm
import io.realm.RealmConfiguration

val gson = Gson()

fun string(id: Int): String {
    return WikipediaApplication.instance.resources.getString(id)
}

fun color(id: Int): Int {
    return ResourcesCompat.getColor(WikipediaApplication.instance.resources, id, null)
}

fun colorList(id: Int): ColorStateList? {
    return ResourcesCompat.getColorStateList(WikipediaApplication.instance.resources, id, null)
}


fun font(id: Int): Typeface? {
    return ResourcesCompat.getFont(WikipediaApplication.instance, id)
}

fun dimen(id: Int): Float {
    return WikipediaApplication.instance.resources.getDimension(id)
}

fun integer(id: Int): Int {
    return WikipediaApplication.instance.resources.getInteger(id)
}

fun drawable(id: Int): Drawable? {
    return ResourcesCompat.getDrawable(WikipediaApplication.instance.resources, id, null)
}

class WikipediaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        pref = PreferenceManager.getDefaultSharedPreferences(this)

        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @get:Synchronized
        lateinit var instance: Application
            private set
        lateinit var defaultSharedPreferences: SharedPreferences
        lateinit var pref: SharedPreferences


    }
}