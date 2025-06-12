package com.gigcreator.data.feature.permission.datasource.impl

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.gigcreator.data.feature.permission.datasource.PermissionDataSource

class PermissionDataSourceImpl(private val context: Context) : PermissionDataSource {

    private val permissions = when(Build.VERSION.SDK_INT) {
        Build.VERSION_CODES.S -> arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.BLUETOOTH_CONNECT
        )

        else -> {
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,

            )
        }
    }

    override fun arePermissionsGranted(): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun getPermissions(): Array<String> {
        return permissions
    }
}