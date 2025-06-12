package com.gigcreator.bluechat.feature.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gigcreator.bluechat.core.fragment.FeatureFragment
import com.gigcreator.bluechat.databinding.FragmentMenuBinding
import com.gigcreator.bluechat.feature.menu.screens.MenuDestinations
import com.gigcreator.bluechat.feature.scan.screens.ScanDestinations

class MenuFragment() : FeatureFragment(MenuDestinations.Menu) {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.scan.setOnClickListener {
            getNavController().navigate(ScanDestinations.Scan)
        }
    }
}