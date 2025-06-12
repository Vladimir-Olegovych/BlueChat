package com.gigcreator.bluechat.core.feature

import androidx.fragment.app.Fragment
import com.gigcreator.bluechat.core.navigation.NavController

interface Feature {

    fun buildNavigation(
        navController: NavController<Destination>,
        curDestination: Destination
    ): Fragment?

}