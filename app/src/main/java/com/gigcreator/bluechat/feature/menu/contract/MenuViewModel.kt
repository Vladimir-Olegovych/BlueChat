package com.gigcreator.bluechat.feature.menu.contract

import com.gigcreator.bluechat.core.vm.MasterViewModel

class MenuViewModel: MasterViewModel<MenuContract.Event, MenuContract.State, MenuContract.Effect>() {

    override fun setInitialState(): MenuContract.State = MenuContract.State.Idle

    override fun handleEvent(event: MenuContract.Event) {
        when(event) {
            is MenuContract.Event.DetermineRoute -> determineNavRoute(event.isBluetoothEnabled)
        }
    }

    private fun determineNavRoute(isBluetoothEnabled: Boolean) {
        if (isBluetoothEnabled) setEffect(MenuContract.Effect.StartScan)
        else setEffect(MenuContract.Effect.OpenBluetooth)
    }



}