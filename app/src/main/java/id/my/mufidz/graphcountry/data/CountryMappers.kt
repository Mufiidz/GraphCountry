package id.my.mufidz.graphcountry.data

import id.my.mufidz.CountriesQuery
import id.my.mufidz.CountryQuery
import id.my.mufidz.graphcountry.model.Countries
import id.my.mufidz.graphcountry.model.Country
import id.my.mufidz.graphcountry.model.Language
import id.my.mufidz.graphcountry.model.State

fun CountriesQuery.Country.toCountries(): Countries = Countries(
    code = code, name = name, emoji = emoji, capital = capital ?: "[NO CAPITAL]"
)

fun CountryQuery.Country.toCountry(): Country = Country(
    code,
    name,
    emoji,
    capital ?: "[NO CAPITAL]",
    currency ?: "[NO CURRENCY]",
    languages.map { it.toLanguage() },
    continent.name,
    states.map { it.toState() },
    "+$phone",
    native
)

fun CountryQuery.Language.toLanguage(): Language = Language(code, name ?: "[NO LANGUAGE]")

fun CountryQuery.State.toState(): State = State(code ?: "[NO STATE CODE]", name)
