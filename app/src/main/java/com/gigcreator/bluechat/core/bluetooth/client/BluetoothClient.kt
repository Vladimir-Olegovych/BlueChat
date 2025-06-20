package com.gigcreator.bluechat.core.bluetooth.client

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import com.gigcreator.bluechat.core.bluetooth.server.BluetoothServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

class BluetoothClient(
    private val scope: CoroutineScope
) {

    private var connectJob: Job? = null

    fun send(device: BluetoothDevice, message: String) {
        connectJob?.cancel()
        connectJob = null

        val uuid = UUID.fromString(BluetoothServer.APP_SERVER_UUID)
        connectJob = scope.launch(Dispatchers.IO) {
            while (true) {
                var socket: BluetoothSocket? = null
                try {
                    socket = device.createRfcommSocketToServiceRecord(uuid)
                    socket.connect()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    delay(1000)
                    continue
                }

                try {
                    socket.outputStream.write((message + "\n").toByteArray(Charsets.UTF_8))
                    socket.outputStream.flush()
                    delay(200)
                } catch (e: Throwable) {
                    e.printStackTrace()
                } finally {
                    runCatching { socket.close() }
                }

                break
            }
        }
    }


    fun cancelSend() {
        connectJob?.cancel()
        connectJob = null
    }

}