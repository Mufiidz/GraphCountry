package id.my.mufidz.graphcountry.usecase

import id.my.mufidz.CountriesQuery
import id.my.mufidz.graphcountry.base.ActionResult
import id.my.mufidz.graphcountry.base.BaseUseCase
import id.my.mufidz.graphcountry.data.CountryClient
import id.my.mufidz.graphcountry.data.toCountries
import id.my.mufidz.graphcountry.model.Countries
import id.my.mufidz.graphcountry.utils.DataResult
import javax.inject.Inject

class CountriesUseCase @Inject constructor(private val countryClient: CountryClient) :
    BaseUseCase<Nothing, CountriesQuery.Data, CountriesUseCase.UseCaseResult>() {

    override suspend fun execute(param: Nothing?): DataResult<CountriesQuery.Data> =
        countryClient.getCountries()

    override suspend fun DataResult<CountriesQuery.Data>.transformToResult(): UseCaseResult =
        when (this) {
            is DataResult.Failure -> UseCaseResult.Failed(exception.localizedMessage.orEmpty())
            is DataResult.Success -> UseCaseResult.Success(value.countries.map { it.toCountries() })
        }

    sealed class UseCaseResult : ActionResult {
        data class Success(val countries: List<Countries>) : UseCaseResult()
        data class Failed(val message: String) : UseCaseResult()
    }
}