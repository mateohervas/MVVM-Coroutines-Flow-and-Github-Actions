package com.shadows.bitsodream.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ticker(var volume: String,
                  var high: String,
                  var last: String,
                  var low: String,
                  var book: BookDomain,
                  var vwap: String,
                  var ask: String,
                  var spread: String
): Parcelable
