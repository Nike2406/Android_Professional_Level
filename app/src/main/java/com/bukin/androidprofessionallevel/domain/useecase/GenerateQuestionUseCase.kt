package com.bukin.androidprofessionallevel.domain.useecase

import com.bukin.androidprofessionallevel.domain.entity.Question
import com.bukin.androidprofessionallevel.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val repository: GameRepository
) {

    /*
    * Т.к. usecase, уже содержит имя действия, то в котлине
    * дали возможность переопределить  метод invoke()
    * */
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    companion object {
        // количество вараинтов - часть бизнес логики, поэтому хранится
        // в самом usecase
        private const val COUNT_OF_OPTIONS = 6
    }
}