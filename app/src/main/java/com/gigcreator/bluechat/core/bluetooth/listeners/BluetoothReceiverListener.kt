package com.gigcreator.bluechat.core.bluetooth.listeners

import android.bluetooth.BluetoothDevice

abstract class BluetoothReceiverListener {
    abstract fun onFoundDevice(device: BluetoothDevice)
    abstract fun onBoundDevice(device: BluetoothDevice)
}