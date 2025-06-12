package com.gigcreator.bluechat.core.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface ViewEffect

interface ViewEvent

interface ViewState

abstract class MasterViewModel<Event : ViewEvent, UiState : ViewState, Effect : ViewEffect>(
    protected val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {
    private val initialState: UiState by lazy { setInitialState() }
    protected abstract fun setInitialState(): UiState

    private val _effect = MutableLiveData<Effect>()
    val effect: LiveData<Effect> = _effect

    private val _state = MutableLiveData<UiState>(initialState)
    val state: LiveData<UiState> = _state

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    protected fun setEffect(effect: Effect) {
        viewModelScope.launch(dispatcher) { _effect.postValue(effect) }
    }

    protected fun setState(newState: UiState) {
        _state.value = newState
    }

    abstract fun handleEvent(event: Event)

}