package com.gigcreator.bluechat

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.gigcreator.bluechat.core.activity.ActivityResultUtils
import com.gigcreator.bluechat.core.asFeatureFragment
import com.gigcreator.bluechat.core.bluetooth.client.BluetoothClient
import com.gigcreator.bluechat.core.bluetooth.managers.BluetoothReceiverManager
import com.gigcreator.bluechat.core.bluetooth.server.BluetoothServer
import com.gigcreator.bluechat.core.navigation.NavFeatureController
import com.gigcreator.bluechat.feature.chat.ChatFragment
import com.gigcreator.bluechat.feature.menu.MenuFragment
import com.gigcreator.bluechat.feature.scan.ScanFragment
import com.gigcreator.bluechat.feature.splash.SplashFragment
import com.gigcreator.bluechat.feature.splash.screens.SplashDestinations
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val bluetoothReceiverManager by inject<BluetoothReceiverManager>()
    private val bluetoothClient by inject<BluetoothClient>()
    private val bluetoothServer by inject<BluetoothServer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val features = arrayOf<Fragment>(
            SplashFragment(),
            MenuFragment(),
            ScanFragment(),
            ChatFragment()
        )

        val navFeatureController = NavFeatureController()
        features.forEach { navFeatureController.addFeature(it.asFeatureFragment()) }

        navFeatureController.initialize(this)
        bluetoothReceiverManager.registerBluetoothReceiver(this)
        ActivityResultUtils.initialize(this)


        navFeatureController.navigate(SplashDestinations.Splash)
    }



    override fun onDestroy() {
        super.onDestroy()
        bluetoothServer.stop()
        bluetoothReceiverManager.unregisterBluetoothReceiver(this)
    }
}