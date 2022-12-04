package com.bukin.androidprofessionallevel.example1

/**
* Если для работы класса А нужен класс В, то
 * класс А зависит от класса В
* */

class Activity {

    // Вариант так себе, т.к. активити теперь зависит от компонента
//    val computer: Computer = Component().getComputer()
    // в таких случаях лучше использовать инъекции,
    lateinit var computer: Computer
    lateinit var keyboard: Keyboard

    init {
        // При инициализации запрашиваются все зависимости
        // в данные класс
        Component().inject(this)
    }
}