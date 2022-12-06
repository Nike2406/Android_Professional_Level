package com.bukin.androidprofessionallevel.example2.di

import android.content.Context
import com.bukin.androidprofessionallevel.example2.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, DomainModule::class])
interface ApplicationComponent  {

    fun inject(activity: MainActivity)

//   /**
//   Говорим даггеру, что будем использовать свой builder()
//
//    Этот способ предпочтительней, если нам нужно передать в граф
//    свое значение, а после его использоваь, например context
//
//   @BindsInstance - помечает метод на component builder или параметр
//    на component factory как привязку инстанса к какому-нибудь ключу
//    внутри компонента.
//   */
//    @Component.Builder
//    interface ApplicationComponentBuilder {
//
//        @BindsInstance // Вставляем в граф зависимостей
//        fun context(context: Context): ApplicationComponentBuilder
//
//        @BindsInstance
//        fun timeInMillis(timeInMillis: Long): ApplicationComponentBuilder
//
//        fun build(): ApplicationComponent
//    }

    /**
    * В новой версии dagger появилась возможность создавать зависимости
     * через фабрику.
     * Если через @Component.Builder нам нужно было создавать метод,
     * то через фабрику можно передать все параметры через create(components),
     * их также нужно пометить @BindsInstance
    * */
    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance context: Context,
            @BindsInstance timeInMillis: Long
        ): ApplicationComponent
    }
}