package com.bukin.androidprofessionallevel.example2.di

import com.bukin.androidprofessionallevel.example2.data.repository.ExampleRepositoryImpl
import com.bukin.androidprofessionallevel.example2.domain.ExampleRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: ExampleRepositoryImpl): ExampleRepository
}