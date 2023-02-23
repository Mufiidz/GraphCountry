package id.my.mufidz.graphcountry.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.mufidz.graphcountry.data.CountryClient
import id.my.mufidz.graphcountry.data.CountryClientImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://countries.trevorblades.com/graphql"

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient =
        ApolloClient.Builder().serverUrl(BASE_URL).build()

    @Provides
    @Singleton
    fun provideCountryClient(countryClientImpl: CountryClientImpl) : CountryClient = countryClientImpl

}