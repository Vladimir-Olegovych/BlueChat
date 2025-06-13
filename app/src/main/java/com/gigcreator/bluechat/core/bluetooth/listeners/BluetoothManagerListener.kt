package com.gigcreator.bluechat.core.bluetooth.listeners

abstract class BluetoothManagerListener: BluetoothReceiverListener() {
    abstract fun onStartScanDevices()
    abstract fun onStopScanDevices()

}
