package edu.washington.wynhsu.quizdroid

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Questions(val question: String,
                     val options: Array<String>,
                     val answer: Int) : Parcelable