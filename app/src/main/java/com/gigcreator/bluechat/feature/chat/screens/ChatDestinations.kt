package com.gigcreator.bluechat.feature.chat.screens

import android.bluetooth.BluetoothDevice
import com.gigcreator.bluechat.core.feature.Destination

sealed interface ChatDestinations : Destination {
    data class Chat(val device: BluetoothDevice) : ChatDestinations
}