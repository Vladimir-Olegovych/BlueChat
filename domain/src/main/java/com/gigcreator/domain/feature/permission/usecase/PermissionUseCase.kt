package com.gigcreator.domain.feature.permission.usecase

import com.gigcreator.domain.feature.permission.repository.PermissionRepository

class PermissionUseCase(private val permissionRepository: PermissionRepository) {

    fun arePermissionsGranted(): Boolean = permissionRepository.arePermissionsGranted()

    fun getPermissions(): Array<String> = permissionRepository.getPermissions()

}