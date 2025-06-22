package com.gigcreator.bluechat.core.bluetooth.client

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import androidx.annotation.RequiresPermission
import com.gigcreator.bluechat.core.bluetooth.server.BluetoothServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

class BluetoothClient(
    private val scope: CoroutineScope
) {
    fun send(device: BluetoothDevice, message: String) {
        scope.launch(Dispatchers.IO) {
            val socket = connectToDevice(device)?: return@launch
            try {
                socket.outputStream.write((message + "\n").toByteArray(Charsets.UTF_8))
                socket.outputStream.flush()
            } catch (e: Throwable) {
                e.printStackTrace()
            } finally {
                delay(200)
                runCatching { socket.close() }
            }
        }
    }


    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun connectToDevice(device: BluetoothDevice): BluetoothSocket? {
        val uuid = UUID.fromString(BluetoothServer.APP_SERVER_UUID)

        try {
            val socket = device.createRfcommSocketToServiceRecord(uuid)
            socket.connect()
            return socket
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return null
    }
}