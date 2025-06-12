package com.gigcreator.bluechat.core.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.gigcreator.bluechat.core.parcelable
import com.gigcreator.domain.feature.permission.repository.PermissionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BluetoothDeviceManager(
    private val permissionRepository: PermissionRepository,
    private val scope: CoroutineScope,
    applicationContext: Context
) {

    private val bluetoothManager = applicationContext.applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter = bluetoothManager.adapter
    private val receiver = BluetoothReceiver()

    fun isBluetoothAvailable(): Boolean {
        return bluetoothAdapter != null
    }

    fun isBluetoothEnabled(): Boolean{
        return bluetoothAdapter?.isEnabled == true
    }

    fun startScanDevices(activity: Activity): Boolean {
        if (receiver.registered) return false
        if (bluetoothAdapter.isDiscovering) bluetoothAdapter.cancelDiscovery()

        val filter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_FOUND)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        }
        activity.registerReceiver(receiver, filter)
        receiver.registered = true

        val hasPermissions = permissionRepository.arePermissionsGranted()
        //if (!hasPermissions) return false
        println("scan ${bluetoothAdapter.startDiscovery()}")


        scope.launch {
            delay(12000)
            activity.unregisterReceiver(receiver)
            receiver.registered = false
        }

        return true
    }


    private inner class BluetoothReceiver(): BroadcastReceiver() {

        var registered = false

        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if (BluetoothDevice.ACTION_FOUND != action) return

            val device: BluetoothDevice = intent.parcelable(BluetoothDevice.EXTRA_DEVICE)?: return
            println("device ${device.name}")

        }
    }

}