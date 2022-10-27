package com.bukin.androidprofessionallevel.domain.useecase

import com.bukin.androidprofessionallevel.domain.entity.GameSettings
import com.bukin.androidprofessionallevel.domain.entity.Level
import com.bukin.androidprofessionallevel.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}