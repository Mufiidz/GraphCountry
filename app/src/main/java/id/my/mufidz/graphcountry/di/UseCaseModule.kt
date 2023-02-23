package id.my.mufidz.graphcountry.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.mufidz.graphcountry.data.CountryClient
import id.my.mufidz.graphcountry.usecase.CountriesUseCase
import id.my.mufidz.graphcountry.usecase.CountryUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideCountriesUseCase(countryClient: CountryClient) : CountriesUseCase =
        CountriesUseCase(countryClient)

    @Singleton
    @Provides
    fun provideCountryUseCase(countryClient: CountryClient) : CountryUseCase =
        CountryUseCase(countryClient)

}