package com.gigcreator.bluechat.core

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.gigcreator.bluechat.core.feature.Destination
import com.gigcreator.bluechat.core.fragment.FeatureFragment

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}


fun <T: Destination>Fragment.asFeatureFragment(): FeatureFragment<T> {
    return this as FeatureFragment<T>
}

fun textToPixels(vararg text: String): Int {
    var size = 0
    text.forEach {
        size += it.length
    }

    return size * 24
}