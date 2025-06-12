package com.gigcreator.bluechat.feature.splash.screens

import com.gigcreator.bluechat.core.feature.Destination

sealed interface SplashDestinations : Destination {
    data object Splash : SplashDestinations
}