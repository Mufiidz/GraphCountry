package id.my.mufidz.graphcountry.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Countries(
    val code: String = "CODE",
    val name: String = "NAME",
    val emoji: String = "FLAG",
    val capital: String = "CAPITAL"
) : Parcelable
