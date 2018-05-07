package edu.washington.wynhsu.quizdroid

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Topic(val name: String,
                 val sub: String,
                 val descr: String,
                 val icon: String,
                 val questions: Array<Questions>): Parcelable {
    override fun toString(): String {
        return name
    }
}