package com.bukin.androidprofessionallevel.example2.data.database

import android.content.Context
import android.util.Log
import com.bukin.androidprofessionallevel.R
import javax.inject.Inject

class ExampleDatabase @Inject constructor(
    private val context: Context
) {

    fun method() {
        Log.d("EXAMPLE", "${context.getString(R.string.app_name)}")
    }
}
