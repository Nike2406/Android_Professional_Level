package com.bukin.androidprofessionallevel.example1

/**
* Component - отвечает за создание объектов зависимостей.
* */
class Component {

    fun getComputer(): Computer {
        val monitor = Monitor()
        val keyboard = Keyboard()
        val mouse = Mouse()
        val computerTower = ComputerTower(
            Storage(),
            Memory(),
            Processor()
        )
        return Computer(monitor, computerTower, keyboard, mouse)
    }

    // в качестве параметра метода нужно указать класс,
    // в который мы хотим вставить значение
    fun inject(activity: Activity) {
        activity.computer = getComputer()
        activity.keyboard = Keyboard()
    }
}