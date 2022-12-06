package com.bukin.androidprofessionallevel.example2.di

import com.bukin.androidprofessionallevel.example2.data.datasource.ExampleLocalDataSource
import com.bukin.androidprofessionallevel.example2.data.datasource.ExampleLocalDataSourceImpl
import com.bukin.androidprofessionallevel.example2.data.datasource.ExampleRemoteDataSource
import com.bukin.androidprofessionallevel.example2.data.datasource.ExampleRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    // Если абстрактный класс использует методы без реализации,
    // то лучше его сделаь интерфейсом
    /**
    * Если, при использовании dagger, нужно взять у интерфейса определенную
     * реализцацию, то лучше использоваь @Binds. Для этого также нужно
     * сделать класс абстрактным
    * */
    @ApplicationScope
    @Binds
    fun bindRemoteDataSource(impl: ExampleRemoteDataSourceImpl): ExampleRemoteDataSource

    @ApplicationScope
    @Binds
    fun bindLocalDataSource(impl: ExampleLocalDataSourceImpl): ExampleLocalDataSource
}