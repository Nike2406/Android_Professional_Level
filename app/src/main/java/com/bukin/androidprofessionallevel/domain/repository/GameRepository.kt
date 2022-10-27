package com.bukin.androidprofessionallevel.domain.repository

import com.bukin.androidprofessionallevel.domain.entity.GameSettings
import com.bukin.androidprofessionallevel.domain.entity.Level
import com.bukin.androidprofessionallevel.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int,
    ): Question
    fun getGameSettings(level: Level): GameSettings
}