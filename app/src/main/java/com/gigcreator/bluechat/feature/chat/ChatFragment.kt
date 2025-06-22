package com.gigcreator.bluechat.feature.chat

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.gigcreator.bluechat.core.bluetooth.client.BluetoothClient
import com.gigcreator.bluechat.core.bluetooth.server.BluetoothServer
import com.gigcreator.bluechat.core.bluetooth.server.BluetoothServerListener
import com.gigcreator.bluechat.core.fragment.FeatureFragment
import com.gigcreator.bluechat.databinding.FragmentChatBinding
import com.gigcreator.bluechat.feature.chat.adapter.ChatAdapter
import com.gigcreator.bluechat.feature.chat.adapter.ChatMessage
import com.gigcreator.bluechat.feature.chat.screens.ChatDestinations
import com.gigcreator.bluechat.feature.menu.screens.MenuDestinations
import org.koin.android.ext.android.inject

class ChatFragment : FeatureFragment<ChatDestinations.Chat>() {

    override val destinationClass: Class<ChatDestinations.Chat> = ChatDestinations.Chat::class.java
    private lateinit var binding: FragmentChatBinding

    private val bluetoothClient by inject<BluetoothClient>()
    private val bluetoothServer by inject<BluetoothServer>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val device: BluetoothDevice = getCurrentDestination()?.device?: return
        val adapter = ChatAdapter(
            onAdd = { message, adapter ->
                val lastIndex = adapter.itemCount - 1
                binding.rcView.scrollToPosition(lastIndex)
                binding.rcView.smoothScrollToPosition(lastIndex)
            }
        )

        bluetoothServer.setListener(object : BluetoothServerListener() {
            override fun onReceiveMessage(message: String) {
                adapter.add(ChatMessage(message = message, isCurrentDevice = false))
            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(binding.constraintLayout) { view, insets ->
            val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(0, 0, 0, systemBarInsets.bottom)
            insets
        }

        binding.rcView.layoutManager = LinearLayoutManager(requireContext())
        binding.rcView.adapter = adapter

        binding.back.setOnClickListener {
            getNavController()?.navigate(MenuDestinations.Menu)
        }

        binding.sendButton.setOnClickListener {
            val message = binding.editText.text.toString().trim()
            if (message.isEmpty()) return@setOnClickListener
            binding.editText.text.clear()
            bluetoothClient.send(device, message)
            adapter.add(ChatMessage(message = message, isCurrentDevice = true))
        }

        bluetoothServer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bluetoothServer.stop()
    }
}