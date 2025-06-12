package com.gigcreator.data.feature.permission

import com.gigcreator.data.feature.permission.datasource.PermissionDataSource
import com.gigcreator.domain.feature.permission.repository.PermissionRepository

class PermissionRepositoryImpl(
    private val permissionDataSource: PermissionDataSource,
): PermissionRepository {
    override fun checkPermissions(): Boolean {
        return permissionDataSource.arePermissionsGranted()
    }

    override fun getPermissions(): Array<String> {
        return permissionDataSource.getPermissions()
    }

}