package id.my.mufidz.graphcountry.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.mufidz.graphcountry.base.BaseViewModel
import id.my.mufidz.graphcountry.intention.DetailCountryAction
import id.my.mufidz.graphcountry.intention.DetailCountryResult
import id.my.mufidz.graphcountry.intention.DetailCountryViewState
import id.my.mufidz.graphcountry.usecase.CountryUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCountryViewModel @Inject constructor(
    private val countryUseCase: CountryUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailCountryViewState, DetailCountryAction, DetailCountryResult>(
    DetailCountryViewState(), savedStateHandle
) {
    override fun DetailCountryResult.updateViewState(): DetailCountryViewState = when (this) {
        is DetailCountryResult.CountryResult -> {
            when (useCaseResult) {
                is CountryUseCase.UseCaseResult.Failed -> updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = useCaseResult.message
                    )
                }
                is CountryUseCase.UseCaseResult.Success -> updateState {
                    it.copy(isLoading = false, country = useCaseResult.country)
                }
            }
        }
    }

    override fun DetailCountryAction.handleAction(): Flow<DetailCountryResult> = channelFlow {
        when (this@handleAction) {
            is DetailCountryAction.GetDetailCountry -> {
                delay(250)
                val countryCode = this@handleAction.countryCode
                launch {
                    countryUseCase.getResult(countryCode)
                        .also { send(DetailCountryResult.CountryResult(it)) }
                }
            }
        }
    }
}