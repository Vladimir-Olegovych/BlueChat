package com.gigcreator.bluechat.core.fragment

import androidx.fragment.app.Fragment
import com.gigcreator.bluechat.core.feature.Destination
import com.gigcreator.bluechat.core.navigation.NavController

abstract class FeatureFragment<T: Destination>(): Fragment() {


    abstract val destinationClass: Class<T>

    private var navController: NavController<Destination>? = null
    private var curDestination: T? = null

    protected fun getNavController(): NavController<Destination>? = navController
    protected fun getCurrentDestination(): T? = curDestination

    fun buildNavigation(
        navController: NavController<Destination>,
        curDestination: Destination
    ): Fragment? {
        if (!destinationClass.isInstance(curDestination)) return null
        this.navController = navController
        this.curDestination = curDestination as T
        return this
    }

}