package com.bukin.androidprofessionallevel.example1

/**
* Если для работы класса А нужен класс В, то
 * класс А зависит от класса В
* */

class Activity {

    val monitor = Monitor()
    val keyboard = Keyboard()
    val mouse = Mouse()
    val computerTower = ComputerTower(
        Storage(),
        Memory(),
        Processor()
    )
    val computer = Computer(monitor, computerTower, keyboard, mouse)
}