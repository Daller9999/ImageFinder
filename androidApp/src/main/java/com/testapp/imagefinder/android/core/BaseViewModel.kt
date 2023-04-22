package com.testapp.imagefinder.android.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Event>(
    initState: State
) : ViewModel() {

    private val _viewStates: MutableStateFlow<State> = MutableStateFlow(initState)

    protected val scopeIO = CoroutineScope(Dispatchers.IO)

    fun viewStates(): StateFlow<State> = _viewStates

    abstract fun obtainEvent(viewEvent: Event)

    protected fun update(update: (State) -> State) {
        _viewStates.update(update)
    }

    fun launchIO(call: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            call.invoke()
        }
    }

}