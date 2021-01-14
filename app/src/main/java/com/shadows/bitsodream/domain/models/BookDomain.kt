package com.shadows.bitsodream.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookDomain(val major: String, val minor:String): Parcelable
