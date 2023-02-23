package id.my.mufidz.graphcountry.data

import com.apollographql.apollo3.ApolloClient
import id.my.mufidz.CountriesQuery
import id.my.mufidz.CountryQuery
import id.my.mufidz.graphcountry.utils.DataResult
import id.my.mufidz.graphcountry.utils.awaitResult
import javax.inject.Inject

interface CountryClient {
    suspend fun getCountries(): DataResult<CountriesQuery.Data>
    suspend fun getDetailCountry(countryCode: String): DataResult<CountryQuery.Data>
}

class CountryClientImpl @Inject constructor(private val apolloClient: ApolloClient) :
    CountryClient {

    override suspend fun getCountries(): DataResult<CountriesQuery.Data> =
        apolloClient.query(CountriesQuery()).execute().awaitResult()

    override suspend fun getDetailCountry(countryCode: String): DataResult<CountryQuery.Data> =
        apolloClient.query(CountryQuery(countryCode)).execute().awaitResult()
}