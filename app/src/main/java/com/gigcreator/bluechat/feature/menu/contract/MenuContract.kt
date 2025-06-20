package com.gigcreator.bluechat.feature.menu.contract

import com.gigcreator.bluechat.core.vm.ViewEffect
import com.gigcreator.bluechat.core.vm.ViewEvent
import com.gigcreator.bluechat.core.vm.ViewState

object MenuContract {
    sealed interface Event : ViewEvent {
        data class DetermineRoute(val isBluetoothEnabled: Boolean) : Event
    }

    sealed interface State : ViewState {
        data object Idle : State
    }

    sealed interface Effect : ViewEffect {
        data object StartScan : Effect
        data object OpenBluetooth : Effect
    }

}