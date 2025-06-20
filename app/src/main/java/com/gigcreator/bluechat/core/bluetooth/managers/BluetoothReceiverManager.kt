package com.gigcreator.bluechat.core.bluetooth.managers

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.gigcreator.bluechat.core.bluetooth.managers.listeners.BluetoothReceiverListener
import com.gigcreator.bluechat.core.parcelable

class BluetoothReceiverManager {

    private var listener: BluetoothReceiverListener? = null
    private val receiver = BluetoothReceiver()

    fun setListener(listener: BluetoothReceiverListener?) {
        this.listener = listener
    }
    
    fun registerBluetoothReceiver(activity: Activity){
        if (receiver.registered) return
        val filter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_FOUND)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)

            addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        }
        activity.registerReceiver(receiver, filter)
        receiver.registered = true
    }

    fun unregisterBluetoothReceiver(activity: Activity) {
        if (!receiver.registered) return
        activity.unregisterReceiver(receiver)
        receiver.registered = false
    }

    private inner class BluetoothReceiver(): BroadcastReceiver() {
        var registered = false

        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action?: return
            val device: BluetoothDevice = intent.parcelable(BluetoothDevice.EXTRA_DEVICE)?: return

            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    listener?.onFoundDevice(device)
                }
                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                    val state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1)
                    if (state == BluetoothDevice.BOND_BONDED) listener?.onBoundDevice(device)
                }
            }
        }
    }
}