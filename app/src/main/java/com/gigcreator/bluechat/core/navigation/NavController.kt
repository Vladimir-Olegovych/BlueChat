package com.gigcreator.bluechat.core.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface NavigationListener<D: Any> {
    suspend fun onStartNavigation(currentDestination: D?, nextDestination: D): Boolean = true
    fun onNavigate(destination: D)
}

class NavController<D: Any>() {

    private val scope = CoroutineScope(Dispatchers.IO)
    @Volatile private var inNavigation = false
    @Volatile private var currentDestination: D? = null
    @Volatile private var navigationListener: NavigationListener<D>? = null

    fun setOnNavigationListener(navigationListener: NavigationListener<D>){
        this.navigationListener = navigationListener
    }

    fun navigate(destination: D) {
        if (currentDestination == destination || inNavigation) return
        inNavigation = true

        scope.launch {
            val onNavigationStart = navigationListener?.onStartNavigation(currentDestination, destination)

            if (onNavigationStart == false) {
                inNavigation = false
                return@launch
            }

            currentDestination = destination
            inNavigation = false

            scope.launch(Dispatchers.Main) {
                navigationListener?.onNavigate(destination)
            }
        }
    }
}
