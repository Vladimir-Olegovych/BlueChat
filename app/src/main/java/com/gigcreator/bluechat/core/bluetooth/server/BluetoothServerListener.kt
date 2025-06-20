package com.gigcreator.bluechat.core.bluetooth.server

abstract class BluetoothServerListener {
    abstract fun onReceiveMessage(message: String)
}