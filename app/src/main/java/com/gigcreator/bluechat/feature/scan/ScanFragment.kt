package com.gigcreator.bluechat.feature.scan

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gigcreator.bluechat.core.activity.ActivityResultUtils
import com.gigcreator.bluechat.core.fragment.FeatureFragment
import com.gigcreator.bluechat.databinding.FragmentScanBinding
import com.gigcreator.bluechat.feature.menu.screens.MenuDestinations
import com.gigcreator.bluechat.feature.scan.screens.ScanDestinations

class ScanFragment : FeatureFragment(ScanDestinations.Scan) {

    private lateinit var binding: FragmentScanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.back.setOnClickListener {
            getNavController().navigate(MenuDestinations.Menu)
        }
        val bluetoothManager = requireContext().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter

        if (bluetoothAdapter == null) {
            Toast.makeText(requireContext(), "Bluetooth не поддерживается", Toast.LENGTH_SHORT).show()
        } else if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            ActivityResultUtils.requestResult(enableBtIntent, onSuccess = {
                println("scseess")
            }, onFailure = {
                println("faliesr")
            })
        }

    }

}