package edu.washington.wynhsu.quizdroid

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Topic(val title: String,
                 val desc: String,
                 val questions: Array<Questions>): Parcelable