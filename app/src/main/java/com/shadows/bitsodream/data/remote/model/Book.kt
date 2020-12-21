package com.shadows.bitsodream.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(
	var volume: String? = null,
	var high: String? = null,
	var last: String? = null,
	var low: String? = null,
	var book: String? = null,
	var vwap: String? = null,
	var ask: String? = null,
	@SerializedName("created_at")
	var createdAt: String? = null,
	var bid: String? = null,
	var change24: String? = null
) : Parcelable
