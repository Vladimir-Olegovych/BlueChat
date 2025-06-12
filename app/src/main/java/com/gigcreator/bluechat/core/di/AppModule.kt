package com.gigcreator.bluechat.core.di

import com.gigcreator.bluechat.feature.splash.contract.SplashViewModel
import com.gigcreator.domain.feature.permission.usecase.PermissionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { CoroutineScope(Dispatchers.Main) }
    single { PermissionUseCase(get()) }
    viewModel { SplashViewModel(get()) }
}