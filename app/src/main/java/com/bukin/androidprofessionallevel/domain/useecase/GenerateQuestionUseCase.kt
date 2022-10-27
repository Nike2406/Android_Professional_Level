package com.bukin.androidprofessionallevel.domain.useecase

import com.bukin.androidprofessionallevel.domain.entity.Question
import com.bukin.androidprofessionallevel.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}