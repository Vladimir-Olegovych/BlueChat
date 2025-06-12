package com.gigcreator.domain.feature.permission.repository

interface PermissionRepository {
    fun arePermissionsGranted(): Boolean
    fun getPermissions(): Array<String>
}