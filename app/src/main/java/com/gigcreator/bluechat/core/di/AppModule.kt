package com.gigcreator.bluechat.core.di

import com.gigcreator.bluechat.core.bluetooth.BluetoothDeviceManager
import com.gigcreator.bluechat.feature.scan.contract.ScanViewModel
import com.gigcreator.bluechat.feature.splash.contract.SplashViewModel
import com.gigcreator.domain.feature.permission.usecase.PermissionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { CoroutineScope(Dispatchers.Main) }
    single { PermissionUseCase(get()) }
    single { BluetoothDeviceManager(get(), get(), androidContext()) }

    viewModel { SplashViewModel(get()) }
    viewModel { ScanViewModel(get()) }
}