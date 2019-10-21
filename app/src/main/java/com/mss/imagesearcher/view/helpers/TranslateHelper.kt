package com.mss.imagesearcher.view.helpers

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*
import javax.inject.Inject

class TranslateHelper @Inject constructor(val context: Context) {

    private var enResources = getEngResources()

    private fun getEngResources(): Resources {
        var conf = context.resources.configuration
        conf = Configuration(conf)
        conf.setLocale(Locale("en"))
        val localizedContext = context.createConfigurationContext(conf)
        return localizedContext.resources
    }

    fun getEngValueFromArray(idArray: Int, position: Int): String {
        return enResources.getStringArray(idArray)[position]
    }

    fun getTranslatedValueFromArray(idArray: Int, position: Int): String {
        return context.resources.getStringArray(idArray)[position]
    }

    fun getTranslatedValueFromArray(idArray: Int, engValue: String): String {
        return getTranslatedValueFromArray(idArray, getPositionEngValueFromArray(idArray, engValue))
    }

    fun getPositionEngValueFromArray(idRes: Int, engValue: String): Int {
        return enResources.getStringArray(idRes).indexOf(engValue)
    }

    fun getTranslatedStringByRes(idRes: Int): String {
        return context.resources.getString(idRes)
    }
}