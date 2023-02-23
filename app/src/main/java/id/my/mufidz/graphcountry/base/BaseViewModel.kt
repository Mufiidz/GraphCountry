package id.my.mufidz.graphcountry.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import timber.log.Timber

abstract class BaseViewModel<State : ViewState, Action : ViewAction, Result : ActionResult>(
    initialState: State, savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow =
        SaveableMutableSaveStateFlow(savedStateHandle, initialState::javaClass.name, initialState)

    val viewState: StateFlow<State> = _stateFlow.asStateFlow()

    protected fun currentViewState(): State = viewState.value

    protected abstract fun Result.updateViewState(): State
    protected abstract fun Action.handleAction(): Flow<Result>

    protected fun updateState(updatedState: (State) -> State) : State =
        _stateFlow.asMutableStateFlow().updateAndGet(updatedState)

    fun execute(action: Action) {
        action.handleAction().map { it.updateViewState() }.onEach { _stateFlow.value = it }
            .catch { Timber.e(it) }.launchIn(viewModelScope)
    }
}

private class SaveableMutableSaveStateFlow<T>(
    private val savedStateHandle: SavedStateHandle, private val key: String, defaultValue: T
) {
    private val _state: MutableStateFlow<T> =
        MutableStateFlow(savedStateHandle[key] ?: defaultValue)

    var value: T
        get() = _state.value
        set(value) {
            _state.value = value
            savedStateHandle[key] = value
        }

    fun asStateFlow(): StateFlow<T> = _state

    fun asMutableStateFlow(): MutableStateFlow<T> = _state
}