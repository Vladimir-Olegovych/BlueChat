package com.gigcreator.bluechat.core.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import com.gigcreator.bluechat.core.bluetooth.listeners.BluetoothManagerListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BluetoothDeviceManager(
    private val bluetoothReceiverManager: BluetoothReceiverManager,
    private val applicationContext: Context,
    private val scope: CoroutineScope
) {

    private val bluetoothManager = applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter = bluetoothManager.adapter
    private var scanDelay: Job? = null

    private var listener: BluetoothManagerListener? = null

    fun getBondedDevices(): Set<BluetoothDevice> {
        return bluetoothAdapter?.bondedDevices ?: emptySet()
    }

    fun setListener(listener: BluetoothManagerListener) {
        bluetoothReceiverManager.setListener(listener)
        this.listener = listener
    }

    fun isBluetoothAvailable(): Boolean {
        return bluetoothAdapter != null
    }

    fun isBluetoothEnabled(): Boolean{
        return bluetoothAdapter?.isEnabled == true
    }

    fun connectDevice(device: BluetoothDevice) {
        device.createBond()

    }

    fun stopScanDevices(){
        listener?.onStopScanDevices()
        scanDelay?.cancel()
        scanDelay = null
        bluetoothAdapter.cancelDiscovery()
    }

    fun startScanDevices() {
        stopScanDevices()
        bluetoothAdapter.startDiscovery()
        scanDelay = scope.launch {
            delay(30000)
            stopScanDevices()
        }
        listener?.onStartScanDevices()
    }


    companion object {
        const val APP_KEY_UUID = "3c9dcf0a-7728-430d-88ea-480723dc4fdc"
    }

}