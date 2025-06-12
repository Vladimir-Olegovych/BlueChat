package com.gigcreator.bluechat.feature.scan

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gigcreator.bluechat.core.activity.ActivityResultUtils
import com.gigcreator.bluechat.core.fragment.FeatureFragment
import com.gigcreator.bluechat.databinding.FragmentScanBinding
import com.gigcreator.bluechat.feature.menu.screens.MenuDestinations
import com.gigcreator.bluechat.feature.scan.contract.ScanContract
import com.gigcreator.bluechat.feature.scan.contract.ScanViewModel
import com.gigcreator.bluechat.feature.scan.screens.ScanDestinations
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScanFragment : FeatureFragment(ScanDestinations.Scan) {

    private lateinit var binding: FragmentScanBinding
    private val viewModel by viewModel<ScanViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.effect.observe(viewLifecycleOwner) {
            when(it) {
                is ScanContract.Effect.StartScan -> {
                    startScan()
                }
                is ScanContract.Effect.OpenBluetooth -> {
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    ActivityResultUtils.requestResult(enableBtIntent, onSuccess = {
                        startScan()
                    }, onFailure = {
                        getNavController().navigate(MenuDestinations.Menu)
                    })
                }
            }
        }

        binding.back.setOnClickListener {
            getNavController().navigate(MenuDestinations.Menu)
        }

        viewModel.handleEvent(ScanContract.Event.DetermineRoute(false))
    }
    private fun startScan(){

    }
}