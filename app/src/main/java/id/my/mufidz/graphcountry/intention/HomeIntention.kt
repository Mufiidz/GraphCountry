package id.my.mufidz.graphcountry.intention

import id.my.mufidz.graphcountry.base.ActionResult
import id.my.mufidz.graphcountry.base.ViewAction
import id.my.mufidz.graphcountry.base.ViewState
import id.my.mufidz.graphcountry.model.Countries
import id.my.mufidz.graphcountry.usecase.CountriesUseCase
import kotlinx.parcelize.Parcelize

sealed class HomeAction : ViewAction {
    object LoadCountries : HomeAction()
}

sealed class HomeResult : ActionResult {
    data class CountriesResult(val useCaseResult: CountriesUseCase.UseCaseResult) : HomeResult()
}

@Parcelize
data class HomeViewState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val groupingCountries : Map<Char, List<Countries>> = emptyMap()
) : ViewState