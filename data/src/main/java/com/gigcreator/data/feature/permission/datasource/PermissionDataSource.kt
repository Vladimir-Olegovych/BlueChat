package com.gigcreator.data.feature.permission.datasource

interface PermissionDataSource {
    fun arePermissionsGranted(): Boolean
    fun getPermissions(): Array<String>
}