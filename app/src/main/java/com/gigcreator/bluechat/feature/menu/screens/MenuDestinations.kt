package com.gigcreator.bluechat.feature.menu.screens

import com.gigcreator.bluechat.core.feature.Destination

sealed interface MenuDestinations : Destination {
    data object Menu : MenuDestinations
}