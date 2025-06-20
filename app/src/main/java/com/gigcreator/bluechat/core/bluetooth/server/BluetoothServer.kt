package com.gigcreator.bluechat.core.bluetooth.server

import android.Manifest
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.UUID


class BluetoothServer(
    private val applicationContext: Context,
    private val scope: CoroutineScope
) {
    private val bluetoothManager = applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter = bluetoothManager.adapter

    @Volatile private var serverSocket: BluetoothServerSocket? = null

    private var listener: BluetoothServerListener? = null

    fun setListener(listener: BluetoothServerListener?) {
        this.listener = listener
    }

    fun start(){
        if (serverSocket != null) return
        val serverSocket = initializeSocket()?: return
        this.serverSocket = serverSocket

        scope.launch(Dispatchers.IO) {
            var clientSocket: BluetoothSocket?
            while (this@BluetoothServer.serverSocket != null) {
                clientSocket = try {
                    serverSocket.accept()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    break
                }

                if (clientSocket != null)
                    startListenClient(clientSocket)
            }
        }
    }

    fun stop(){
        runCatching { serverSocket?.close() }
        serverSocket = null
    }


    private fun startListenClient(socket: BluetoothSocket) {
        val result = StringBuilder()
        try {
            val inputStream = socket.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))

            reader.use {
                var line = it.readLine()
                while (line != null) {
                    result.append(line)
                    line = it.readLine()
                }
            }

        } catch (e: Throwable) {
            e.printStackTrace()
        } finally {
            runCatching { socket.close() }
        }

        scope.launch { listener?.onReceiveMessage(result.toString()) }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    private fun initializeSocket(): BluetoothServerSocket? {
        return try {
            bluetoothAdapter.listenUsingRfcommWithServiceRecord(
                SERVER_NAME,
                UUID.fromString(APP_SERVER_UUID)
            )
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        const val SERVER_NAME = "APP_BLUETOOTH_SERVER_CHAT"
        const val APP_SERVER_UUID = "71f02a79-5921-4a8d-b9cb-938e1252f987"
    }
}