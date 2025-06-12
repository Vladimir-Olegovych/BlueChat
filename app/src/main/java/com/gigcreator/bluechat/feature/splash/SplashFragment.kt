package com.gigcreator.bluechat.feature.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gigcreator.bluechat.core.activity.ActivityResultUtils
import com.gigcreator.bluechat.core.fragment.FeatureFragment
import com.gigcreator.bluechat.databinding.FragmentSplashBinding
import com.gigcreator.bluechat.feature.menu.screens.MenuDestinations
import com.gigcreator.bluechat.feature.splash.contract.SplashContract
import com.gigcreator.bluechat.feature.splash.contract.SplashViewModel
import com.gigcreator.bluechat.feature.splash.screens.SplashDestinations
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : FeatureFragment(SplashDestinations.Splash) {

    private lateinit var binding: FragmentSplashBinding
    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.effect.observe(viewLifecycleOwner) {
            when(it) {
                is SplashContract.Effect.OpenMenu -> getNavController().navigate(MenuDestinations.Menu)
                is SplashContract.Effect.RequestPermissions -> ActivityResultUtils.requestResult(
                    array = it.permissions,
                    onSuccess = {
                        viewModel.handleEvent(SplashContract.Event.PermissionSuccess)
                    },
                    onFailure = {}
                )
            }
        }
        viewModel.handleEvent(SplashContract.Event.DetermineRoute)

    }
}
