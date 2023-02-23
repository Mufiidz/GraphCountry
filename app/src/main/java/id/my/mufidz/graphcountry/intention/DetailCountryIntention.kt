package id.my.mufidz.graphcountry.intention

import id.my.mufidz.graphcountry.base.ActionResult
import id.my.mufidz.graphcountry.base.ViewAction
import id.my.mufidz.graphcountry.base.ViewState
import id.my.mufidz.graphcountry.model.Country
import id.my.mufidz.graphcountry.usecase.CountryUseCase
import kotlinx.parcelize.Parcelize

sealed class DetailCountryAction : ViewAction {
    data class GetDetailCountry(val countryCode: String) : DetailCountryAction()
}

sealed class DetailCountryResult : ActionResult {
    data class CountryResult(val useCaseResult: CountryUseCase.UseCaseResult) : DetailCountryResult()
}

@Parcelize
data class DetailCountryViewState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val country: Country = Country()
) : ViewState