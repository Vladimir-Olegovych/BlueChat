package com.gigcreator.bluechat.feature.menu

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gigcreator.bluechat.core.activity.ActivityResultUtils
import com.gigcreator.bluechat.core.bluetooth.managers.BluetoothDeviceManager
import com.gigcreator.bluechat.core.bluetooth.managers.listeners.BluetoothManagerListener
import com.gigcreator.bluechat.core.fragment.FeatureFragment
import com.gigcreator.bluechat.databinding.FragmentMenuBinding
import com.gigcreator.bluechat.feature.chat.screens.ChatDestinations
import com.gigcreator.bluechat.feature.menu.adapter.MenuAdapter
import com.gigcreator.bluechat.feature.menu.contract.MenuContract
import com.gigcreator.bluechat.feature.menu.contract.MenuViewModel
import com.gigcreator.bluechat.feature.menu.screens.MenuDestinations
import com.gigcreator.bluechat.feature.scan.screens.ScanDestinations
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MenuFragment() : FeatureFragment<MenuDestinations.Menu>() {

    override val destinationClass: Class<MenuDestinations.Menu> = MenuDestinations.Menu::class.java
    private lateinit var binding: FragmentMenuBinding
    private val viewModel by activityViewModel<MenuViewModel>()
    private val bluetoothDeviceManager by inject<BluetoothDeviceManager>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuAdapter = MenuAdapter(onClickListener = { device ->
            getNavController()?.navigate(ChatDestinations.Chat(device))
        })
        binding.rcView.layoutManager = LinearLayoutManager(requireContext())
        binding.rcView.adapter = menuAdapter


        bluetoothDeviceManager.setListener(object : BluetoothManagerListener() {
            override fun onBoundDevice(device: BluetoothDevice) {
                menuAdapter.set(bluetoothDeviceManager.getBondedDevices())
            }
        })

        binding.scan.setOnClickListener {
            getNavController()?.navigate(ScanDestinations.Scan)
        }
        viewModel.effect.observe(viewLifecycleOwner) {
            when(it) {
                is MenuContract.Effect.StartScan -> {
                    menuAdapter.set(bluetoothDeviceManager.getBondedDevices())
                }
                is MenuContract.Effect.OpenBluetooth -> {
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    ActivityResultUtils.requestResult(enableBtIntent, onSuccess = {
                        menuAdapter.set(bluetoothDeviceManager.getBondedDevices())
                    }, onFailure = {
                        viewModel.handleEvent(MenuContract.Event.DetermineRoute(false))
                    })
                }
            }
        }

        viewModel.handleEvent(MenuContract.Event.DetermineRoute(bluetoothDeviceManager.isBluetoothEnabled()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bluetoothDeviceManager.setListener(null)
    }
}