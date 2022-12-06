package com.bukin.androidprofessionallevel.example2.di

import android.content.Context
import com.bukin.androidprofessionallevel.example2.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, DomainModule::class])
interface ApplicationComponent  {

    fun inject(activity: MainActivity)

   /**
   Говорим даггеру, что будем использовать свой builder()

    Этот способ предпочтительней, если нам нужно передать в граф
    свое значение, а после его использоваь, например context
   */
    @Component.Builder
    interface ApplicationComponentBuilder {

        @BindsInstance // Вставляем в граф зависимостей
        fun context(context: Context): ApplicationComponentBuilder

        @BindsInstance
        fun timeInMillis(timeInMillis: Long): ApplicationComponentBuilder

        fun build(): ApplicationComponent
    }
}