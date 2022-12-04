package com.bukin.androidprofessionallevel.example1

/**
 * Чтобы создеть инъкцию в конструктор, нужно
 * чтобы dagger знал, как создать все поля в
 * этом конструкторе
* */
class Computer(
    val monitor: Monitor,
    val computerTower: ComputerTower,
    val keyboard: Keyboard,
    val mouse: Mouse
)
