package edu.washington.wynhsu.quizdroid

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Questions(val text: String,
                     val answer: Int,
                     val answers: Array<String>
                     ) : Parcelable