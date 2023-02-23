package id.my.mufidz.graphcountry.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.mufidz.graphcountry.base.BaseViewModel
import id.my.mufidz.graphcountry.intention.HomeAction
import id.my.mufidz.graphcountry.intention.HomeResult
import id.my.mufidz.graphcountry.intention.HomeViewState
import id.my.mufidz.graphcountry.usecase.CountriesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val countriesUseCase: CountriesUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<HomeViewState, HomeAction, HomeResult>(HomeViewState(), savedStateHandle) {

    override fun HomeResult.updateViewState(): HomeViewState = when (this) {
        is HomeResult.CountriesResult -> {
            when (useCaseResult) {
                is CountriesUseCase.UseCaseResult.Failed -> updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = useCaseResult.message
                    )
                }
                is CountriesUseCase.UseCaseResult.Success -> updateState {
                    it.copy(
                        isLoading = false,
                        groupingCountries = useCaseResult.countries.groupBy { key -> key.name[0] }
                            .toSortedMap()
                    )
                }
            }
        }
    }

    override fun HomeAction.handleAction(): Flow<HomeResult> = channelFlow {
        when (this@handleAction) {
            HomeAction.LoadCountries -> {
                delay(250)
                launch {
                    countriesUseCase.getResult().also { send(HomeResult.CountriesResult(it)) }
                }
            }
        }
    }
}