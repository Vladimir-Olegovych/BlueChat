package com.gigcreator.bluechat.core.fragment

import androidx.fragment.app.Fragment
import com.gigcreator.bluechat.core.feature.Destination
import com.gigcreator.bluechat.core.feature.Feature
import com.gigcreator.bluechat.core.navigation.NavController

open class FeatureFragment(
    protected val destination: Destination,
): Feature, Fragment() {

    private var navController: NavController<Destination>? = null
    protected fun getNavController(): NavController<Destination> = navController!!

    override fun buildNavigation(
        navController: NavController<Destination>,
        curDestination: Destination
    ): Fragment? {
        if (curDestination::class != destination::class) return null
        this.navController = navController
        return this
    }

}