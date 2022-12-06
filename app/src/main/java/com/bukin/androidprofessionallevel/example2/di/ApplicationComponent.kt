package com.bukin.androidprofessionallevel.example2.di

import com.bukin.androidprofessionallevel.example2.presentation.MainActivity
import dagger.Component

@Component(modules = [DataModule::class, DomainModule::class, ContextModule::class])
interface ApplicationComponent  {

    fun inject(activity: MainActivity)
}