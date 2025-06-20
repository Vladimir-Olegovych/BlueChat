package com.gigcreator.bluechat.core.bluetooth.managers.listeners

abstract class BluetoothManagerListener: BluetoothReceiverListener() {
    open fun onStartScanDevices() {}
    open fun onStopScanDevices() {}

}
