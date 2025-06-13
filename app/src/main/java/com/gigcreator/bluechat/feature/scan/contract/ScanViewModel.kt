package com.gigcreator.bluechat.feature.scan.contract

import com.gigcreator.bluechat.core.vm.MasterViewModel
import com.gigcreator.domain.feature.permission.usecase.PermissionUseCase

class ScanViewModel(
    private val permissionUseCase: PermissionUseCase
): MasterViewModel<ScanContract.Event, ScanContract.State, ScanContract.Effect>() {

    override fun setInitialState(): ScanContract.State = ScanContract.State.Idle

    override fun handleEvent(event: ScanContract.Event) {
        when(event) {
            is ScanContract.Event.DetermineRoute -> determineNavRoute(event.isBluetoothEnabled)
        }
    }
    private fun determineNavRoute(isBluetoothEnabled: Boolean) {
        if (!permissionUseCase.arePermissionsGranted()) return
        if (isBluetoothEnabled) setEffect(ScanContract.Effect.StartScan)
        else setEffect(ScanContract.Effect.OpenBluetooth)
    }



}