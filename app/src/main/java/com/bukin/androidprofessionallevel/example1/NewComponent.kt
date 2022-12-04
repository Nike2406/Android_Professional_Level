package com.bukin.androidprofessionallevel.example1

import dagger.Component
/**
* @Component - класс, который предоставляет
 * реализацию зависимостей или инжектит их
 * в поля какого-нибудь класса
 * В модули записываем все доп модули в виде массива
* */
@Component(modules = [ComputerModule::class])
interface NewComponent {

    // Кто попросит, верни этот метод
    // с требуемой реализацией
    fun inject(activity: Activity)
}