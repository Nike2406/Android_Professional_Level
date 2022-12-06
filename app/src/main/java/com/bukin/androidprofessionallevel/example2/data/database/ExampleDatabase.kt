package com.bukin.androidprofessionallevel.example2.data.database

import android.content.Context
import android.util.Log
import com.bukin.androidprofessionallevel.R
import javax.inject.Inject
import javax.inject.Singleton

/**
* Т.к. бд является синглтоном, то в даггере можно ее так и пометить
 *
 * Если объект из графа зависимостей, использует определенный scope
 * или время жизни(в данном случае @Singleton), то и компонент,
 * который создает граф зависимостей, должен использовать ту же
 * самую аннотацию.
 *
 * Как не странно, при пересоздании activity, все равно будут
 * создаваться новые объекты. Для того, чтобы добиться того, чтобы
 * создавался один объект, нужно создавать его не в activity, а в
 * классе Application.
 *
 * @Singleton говорит даггеру, что пока живет компонент, помеченный
 * такой же аннотицией, экземпляр этого объекта всегда будет только один.
* */
@Singleton
class ExampleDatabase @Inject constructor(
    private val context: Context,
    private val timeInMillis: Long
) {

    fun method() {
        Log.d("EXAMPLE", "${context.getString(R.string.app_name) }, $timeInMillis, $this")
    }
}
