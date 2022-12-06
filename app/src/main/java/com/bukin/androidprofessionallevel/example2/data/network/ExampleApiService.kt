package com.bukin.androidprofessionallevel.example2.data.network

import android.content.Context
import android.util.Log
import com.bukin.androidprofessionallevel.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExampleApiService @Inject constructor(
    private val context: Context,
    private val timeInMillis: Long
) {

    fun method() {
        Log.d("EXAMPLE", "ApiService, ${context.getString(R.string.app_name)}" +
                "$timeInMillis")
    }
}
