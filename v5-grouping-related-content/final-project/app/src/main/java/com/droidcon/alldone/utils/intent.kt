package com.droidcon.alldone.utils

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable

/**
 * Backwards compatible mechanism for [parcelable] extraction from a [Bundle]
 */
inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}