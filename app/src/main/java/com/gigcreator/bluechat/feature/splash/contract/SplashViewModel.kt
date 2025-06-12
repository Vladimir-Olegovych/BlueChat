package com.gigcreator.bluechat.feature.splash.contract

import com.gigcreator.bluechat.core.vm.MasterViewModel
import com.gigcreator.domain.feature.permission.usecase.PermissionUseCase

class SplashViewModel(
    private val permissionUseCase: PermissionUseCase
): MasterViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    override fun setInitialState(): SplashContract.State = SplashContract.State.Idle

    override fun handleEvent(event: SplashContract.Event) = when(event) {
        is SplashContract.Event.DetermineRoute -> determineNavRoute()
        is SplashContract.Event.PermissionSuccess -> setEffect(SplashContract.Effect.OpenMenu)
    }

    private fun determineNavRoute() {
        if (permissionUseCase.arePermissionsGranted()) setEffect(SplashContract.Effect.OpenMenu)
        else setEffect(SplashContract.Effect.RequestPermissions(permissionUseCase.getPermissions()))
    }

}