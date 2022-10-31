package com.bukin.androidprofessionallevel.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
// enum-классы неявно реализуют Serializable
enum class Level : Parcelable {
    TEST, EASY, NORMAL, HARD
}