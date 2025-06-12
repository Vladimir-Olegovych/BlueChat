package com.gigcreator.bluechat.feature.splash.contract

import com.gigcreator.bluechat.core.vm.ViewEffect
import com.gigcreator.bluechat.core.vm.ViewEvent
import com.gigcreator.bluechat.core.vm.ViewState

object SplashContract {
    sealed interface Event : ViewEvent {
        data object DetermineRoute : Event
    }

    sealed interface State : ViewState {
        data object Idle : State
    }

    sealed interface Effect : ViewEffect {
        data object OpenMenu : Effect
    }

}