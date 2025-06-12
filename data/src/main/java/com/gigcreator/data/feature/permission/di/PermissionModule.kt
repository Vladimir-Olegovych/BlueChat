package com.gigcreator.data.feature.permission.di

import com.gigcreator.data.feature.permission.PermissionRepositoryImpl
import com.gigcreator.data.feature.permission.datasource.PermissionDataSource
import com.gigcreator.data.feature.permission.datasource.impl.PermissionDataSourceImpl
import com.gigcreator.domain.feature.permission.repository.PermissionRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val permissionModule = module {
    single<PermissionDataSource> { PermissionDataSourceImpl(androidContext()) }
    single<PermissionRepository> { PermissionRepositoryImpl(get()) }
}