package com.gigcreator.bluechat.core.navigation

import androidx.appcompat.app.AppCompatActivity
import com.gigcreator.bluechat.R
import com.gigcreator.bluechat.core.asFeatureFragment
import com.gigcreator.bluechat.core.feature.Destination
import com.gigcreator.bluechat.core.fragment.FeatureFragment
import kotlinx.coroutines.delay

class NavFeatureController {
    private val features = ArrayList<FeatureFragment<Destination>>()
    private val navController = NavController<Destination>()

    fun initialize(activity: AppCompatActivity) {
        navController.setOnNavigationListener(object : NavigationListener<Destination> {
            override suspend fun onStartNavigation(
                currentDestination: Destination?,
                nextDestination: Destination
            ): Boolean {
                while (activity.supportFragmentManager.isStateSaved) delay(200)
                return true
            }
            override fun onNavigate(destination: Destination) {
                registerNavigationForFeatures(navController, destination, activity)
            }
        })
    }

    fun addFeature(featureFragment: FeatureFragment<Destination>): Boolean {
        return features.add(featureFragment)
    }

    fun removeFeature(featureFragment: FeatureFragment<Destination>): Boolean{
        return features.remove(featureFragment)
    }

    fun navigate(destination: Destination){
        navController.navigate(destination)
    }

    private fun registerNavigationForFeatures(
        navController: NavController<Destination>,
        currentDestination: Destination,
        activity: AppCompatActivity
    ) {
        for (feature in features) {
            val fragment = feature.asFeatureFragment<Destination>()
                .buildNavigation(navController, currentDestination)
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container_view, fragment?: continue)
                .commit()
            return
        }
    }
}