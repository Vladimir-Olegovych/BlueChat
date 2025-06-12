package com.gigcreator.bluechat.feature.scan.screens

import com.gigcreator.bluechat.core.feature.Destination

sealed interface ScanDestinations : Destination {
    data object Scan : ScanDestinations
}