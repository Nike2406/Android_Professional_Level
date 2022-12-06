package com.bukin.androidprofessionallevel.example2.di

import com.bukin.androidprofessionallevel.example2.data.repository.ExampleRepositoryImpl
import com.bukin.androidprofessionallevel.example2.domain.ExampleRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    // Т.к. dagger уже знает как создавать ExampleRepositoryImpl,
    // мы можем передать реализацию, а даггер за нас подставит все
    // значения
    @Binds
    fun bindRepository(impl: ExampleRepositoryImpl): ExampleRepository
}