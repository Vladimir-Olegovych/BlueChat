package com.gigcreator.bluechat.core.di

import com.gigcreator.bluechat.core.bluetooth.client.BluetoothClient
import com.gigcreator.bluechat.core.bluetooth.managers.BluetoothDeviceManager
import com.gigcreator.bluechat.core.bluetooth.managers.BluetoothReceiverManager
import com.gigcreator.bluechat.core.bluetooth.server.BluetoothServer
import com.gigcreator.bluechat.feature.menu.contract.MenuViewModel
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
    single { BluetoothReceiverManager() }
    single { BluetoothServer(get(), get()) }
    single { BluetoothClient(get()) }
    single { BluetoothDeviceManager(get(), androidContext(), get()) }

    viewModel { MenuViewModel() }
    viewModel { SplashViewModel(get()) }
    viewModel { ScanViewModel(get()) }
}