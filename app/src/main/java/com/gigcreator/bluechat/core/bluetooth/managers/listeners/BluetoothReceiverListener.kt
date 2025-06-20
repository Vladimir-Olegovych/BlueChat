package com.gigcreator.bluechat.core.bluetooth.managers.listeners

import android.bluetooth.BluetoothDevice

abstract class BluetoothReceiverListener {
    open fun onFoundDevice(device: BluetoothDevice) {}
    open fun onBoundDevice(device: BluetoothDevice) {}
}