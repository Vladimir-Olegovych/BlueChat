package com.gigcreator.bluechat.feature.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gigcreator.bluechat.core.bluetooth.BluetoothDeviceManager
import com.gigcreator.bluechat.core.fragment.FeatureFragment
import com.gigcreator.bluechat.databinding.FragmentMenuBinding
import com.gigcreator.bluechat.feature.menu.adapter.MenuAdapter
import com.gigcreator.bluechat.feature.menu.screens.MenuDestinations
import com.gigcreator.bluechat.feature.scan.screens.ScanDestinations
import org.koin.android.ext.android.inject

class MenuFragment() : FeatureFragment(MenuDestinations.Menu) {

    private lateinit var binding: FragmentMenuBinding
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

        })
        binding.rcView.layoutManager = LinearLayoutManager(requireContext())
        binding.rcView.adapter = menuAdapter
        menuAdapter.set(bluetoothDeviceManager.getBondedDevices())

        binding.scan.setOnClickListener {
            getNavController().navigate(ScanDestinations.Scan)
        }
    }
}