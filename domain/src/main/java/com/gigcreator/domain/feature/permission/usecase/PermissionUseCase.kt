package com.gigcreator.domain.feature.permission.usecase

import com.gigcreator.domain.feature.permission.repository.PermissionRepository

class PermissionUseCase(private val permissionRepository: PermissionRepository) {

    fun checkPermissions(): Boolean = permissionRepository.checkPermissions()

    fun getPermissions(): Array<String> = permissionRepository.getPermissions()

}