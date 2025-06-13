package com.gigcreator.bluechat.feature.scan

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gigcreator.bluechat.core.activity.ActivityResultUtils
import com.gigcreator.bluechat.core.bluetooth.BluetoothDeviceManager
import com.gigcreator.bluechat.core.bluetooth.listeners.BluetoothManagerListener
import com.gigcreator.bluechat.core.fragment.FeatureFragment
import com.gigcreator.bluechat.databinding.FragmentScanBinding
import com.gigcreator.bluechat.feature.menu.screens.MenuDestinations
import com.gigcreator.bluechat.feature.scan.adapter.ScanAdapter
import com.gigcreator.bluechat.feature.scan.contract.ScanContract
import com.gigcreator.bluechat.feature.scan.contract.ScanViewModel
import com.gigcreator.bluechat.feature.scan.screens.ScanDestinations
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class ScanFragment : FeatureFragment(ScanDestinations.Scan) {

    private lateinit var binding: FragmentScanBinding
    private val viewModel by viewModel<ScanViewModel>()
    private val bluetoothDeviceManager by inject<BluetoothDeviceManager>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!bluetoothDeviceManager.isBluetoothAvailable()) {
            getNavController().navigate(MenuDestinations.Menu)
            return@onViewCreated
        }

        val scanAdapter = ScanAdapter(onClickListener = { device ->
            bluetoothDeviceManager.connectDevice(device)
        })
        binding.rcView.layoutManager = LinearLayoutManager(requireContext())
        binding.rcView.adapter = scanAdapter

        bluetoothDeviceManager.setListener(object : BluetoothManagerListener() {
            override fun onFoundDevice(device: BluetoothDevice) {
                scanAdapter.add(device)
            }

            override fun onStartScanDevices() {
                scanAdapter.clear()
                binding.progressBar.visibility = View.VISIBLE
                binding.load.visibility = View.GONE
            }

            override fun onStopScanDevices() {
                binding.progressBar.visibility = View.GONE
                binding.load.visibility = View.VISIBLE
            }

            override fun onBoundDevice(device: BluetoothDevice) {
                scanAdapter.add(device)
            }
        })

        viewModel.effect.observe(viewLifecycleOwner) {
            when(it) {
                is ScanContract.Effect.StartScan -> {
                    bluetoothDeviceManager.startScanDevices()
                }
                is ScanContract.Effect.OpenBluetooth -> {
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    ActivityResultUtils.requestResult(enableBtIntent, onSuccess = {
                        bluetoothDeviceManager.startScanDevices()
                    }, onFailure = {
                        getNavController().navigate(MenuDestinations.Menu)
                    })
                }
            }
        }
        binding.load.setOnClickListener {
            bluetoothDeviceManager.startScanDevices()
        }

        binding.back.setOnClickListener {
            getNavController().navigate(MenuDestinations.Menu)
        }

        viewModel.handleEvent(ScanContract.Event.DetermineRoute(bluetoothDeviceManager.isBluetoothEnabled()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bluetoothDeviceManager.stopScanDevices()
    }
}