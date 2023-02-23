package id.my.mufidz.graphcountry.usecase

import id.my.mufidz.CountryQuery
import id.my.mufidz.graphcountry.base.ActionResult
import id.my.mufidz.graphcountry.base.BaseUseCase
import id.my.mufidz.graphcountry.data.CountryClient
import id.my.mufidz.graphcountry.data.toCountry
import id.my.mufidz.graphcountry.model.Country
import id.my.mufidz.graphcountry.utils.DataResult
import javax.inject.Inject

class CountryUseCase @Inject constructor(private val countryClient: CountryClient) :
    BaseUseCase<String, CountryQuery.Data, CountryUseCase.UseCaseResult>() {

    override suspend fun execute(param: String?): DataResult<CountryQuery.Data> =
        if (param.isNullOrEmpty()) DataResult.Failure(Exception("Country code is empty"))
        else countryClient.getDetailCountry(param)

    override suspend fun DataResult<CountryQuery.Data>.transformToResult(): UseCaseResult =
        when (this) {
            is DataResult.Failure -> UseCaseResult.Failed(exception.localizedMessage.orEmpty())
            is DataResult.Success -> {
                if (value.country != null) {
                    UseCaseResult.Success(value.country.toCountry())
                } else {
                    UseCaseResult.Failed("Country not found")
                }
            }
        }

    sealed class UseCaseResult : ActionResult {
        data class Success(val country: Country) : UseCaseResult()
        data class Failed(val message: String) : UseCaseResult()
    }
}