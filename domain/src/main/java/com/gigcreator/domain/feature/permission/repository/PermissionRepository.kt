package com.gigcreator.domain.feature.permission.repository

interface PermissionRepository {
    fun checkPermissions(): Boolean
    fun getPermissions(): Array<String>
}