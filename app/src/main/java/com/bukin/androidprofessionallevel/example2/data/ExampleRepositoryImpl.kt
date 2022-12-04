package com.bukin.androidprofessionallevel.example2.data

import com.bukin.androidprofessionallevel.example2.domain.ExampleRepository

class ExampleRepositoryImpl(
    private val localDataSource: ExampleLocalDataSource,
    private val remoteDataSource: ExampleRemoteDataSource
) : ExampleRepository {

    override fun method() {

    }
}
