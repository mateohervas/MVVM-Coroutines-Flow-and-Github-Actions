package com.shadows.bitsodream.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookStatistic(
	val date: String? = null,
	val volume: String? = null,
	val high: String? = null,
	val low: String? = null,
	val vwap: String? = null,
	val dated: String? = null,
	val value: String? = null,
	val close: String? = null,
	val open: String? = null
) : Parcelable
