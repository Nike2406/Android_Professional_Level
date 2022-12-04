package com.bukin.androidprofessionallevel.example1

import javax.inject.Inject

/**
* Если для работы класса А нужен класс В, то
 * класс А зависит от класса В
* */

class Activity {

    /**
    * Инъекция в поле через создание элемента
    * */
//    val keyboard: Keyboard = DaggerNewComponent.create().getKeyboard()

    // Если мы делаем зависимость в поле,
    // ее также следует пометить аннотацией @Inject
    // * переменная, в которую вставляется зависимость,
    // должна быть public, иначе dagger не справится
    //
    // Передаем dagger, что нам нужен объект Keyboard
    @Inject
    lateinit var computer: Computer


    init {
        DaggerNewComponent.create().inject(this)
    }
}