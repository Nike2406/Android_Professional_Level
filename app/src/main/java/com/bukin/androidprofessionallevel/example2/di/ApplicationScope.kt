package com.bukin.androidprofessionallevel.example2.di

import javax.inject.Scope

/**
* Т.к. по @Singleton нельзя сразу сказать жиненный
 * цикл компонента, то принято создавать дополнительный
 * класс, который непосредственно за него отвечает
* */
@Scope
@Retention(AnnotationRetention.RUNTIME) // Рефлексия (?)
annotation class ApplicationScope