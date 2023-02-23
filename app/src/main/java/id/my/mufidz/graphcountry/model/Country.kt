package id.my.mufidz.graphcountry.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val code: String = "",
    val name: String = "",
    val emoji: String = "",
    val capital: String = "",
    val currency: String = "",
    val languages: List<Language> = listOf(),
    val continent: String = "",
    val state: List<State> = listOf(),
    val phoneCode: String = "",
    val native: String = ""
) : Parcelable {

    fun toListCountryInfo(): List<CountryInfo> = listOf(
        CountryInfo("Code", code),
        CountryInfo("Name", name),
        CountryInfo("Capital", capital),
        CountryInfo("Currency", currency),
        CountryInfo("Language", languages.joinToString { it.name }.ifEmpty { "-" }),
        CountryInfo("Continent", continent),
        CountryInfo("State", state.joinToString { it.name }.ifEmpty { "-" }),
        CountryInfo("Phone Code", phoneCode),
        CountryInfo("Native", native),
    )

}

data class CountryInfo(
    val title: String,
    val content: String,
)

@Parcelize
data class State(
    val code: String = "", val name: String = ""
) : Parcelable

@Parcelize
data class Language(
    val code: String = "", val name: String = ""
) : Parcelable
