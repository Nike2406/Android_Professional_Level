package com.bukin.androidprofessionallevel.example2.data.repository

import com.bukin.androidprofessionallevel.example2.data.datasource.ExampleLocalDataSource
import com.bukin.androidprofessionallevel.example2.data.datasource.ExampleRemoteDataSource
import com.bukin.androidprofessionallevel.example2.domain.ExampleRepository
import javax.inject.Inject

class ExampleRepositoryImpl @Inject constructor(
    private val localDataSource: ExampleLocalDataSource,
    private val remoteDataSource: ExampleRemoteDataSource
) : ExampleRepository {

    override fun method() {

    }
}
