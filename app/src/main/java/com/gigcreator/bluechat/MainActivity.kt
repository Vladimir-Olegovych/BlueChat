package com.gigcreator.bluechat

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gigcreator.bluechat.core.feature.Destination
import com.gigcreator.bluechat.core.feature.Feature
import com.gigcreator.bluechat.core.navigation.NavController
import com.gigcreator.bluechat.core.navigation.NavigationListener
import com.gigcreator.bluechat.core.activity.ActivityResultUtils
import com.gigcreator.bluechat.feature.menu.MenuFragment
import com.gigcreator.bluechat.feature.scan.ScanFragment
import com.gigcreator.bluechat.feature.splash.SplashFragment
import com.gigcreator.bluechat.feature.splash.screens.SplashDestinations
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ActivityResultUtils.initialize(this)

        val features = arrayOf<Feature>(
            SplashFragment(),
            MenuFragment(),
            ScanFragment()
        )

        val navController = NavController<Destination>()

        navController.setOnNavigationListener(object : NavigationListener<Destination> {
            override suspend fun onStartNavigation(
                currentDestination: Destination?,
                nextDestination: Destination
            ): Boolean {
                while (supportFragmentManager.isStateSaved) delay(200)
                return true
            }
            override fun onNavigate(destination: Destination) {
                registerNavigationForFeatures(navController, destination, features)
            }
        })

        navController.navigate(SplashDestinations.Splash)
    }


    private fun registerNavigationForFeatures(
        navController: NavController<Destination>,
        currentDestination: Destination,
        features: Array<Feature>,
    ) {
        for (feature in features) {
            val fragment = feature.buildNavigation(navController, currentDestination)
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container_view, fragment?: continue)
                .commit()
            return
        }
    }
}