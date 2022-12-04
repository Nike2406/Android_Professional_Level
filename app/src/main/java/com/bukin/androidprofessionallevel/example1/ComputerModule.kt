package com.bukin.androidprofessionallevel.example1

import dagger.Module
import dagger.Provides

/**
* @Module - предоставляет реализацию интерфейсов,
 * или каких-то классов, к которым нельзя прменить
 * аннотацию @Inject
* */
@Module
class ComputerModule {

    /**
    * Если dagger не находит реализацию в компонентах,
     * он ищет ее в модулях.
     * Здесь через @Provides(обеспечить) нужно
     * реализовать необходимый метод, указать его тип
     * для dagger и указать его в модулях компонента
    * */
    @Provides
    fun provideMonitor(): Monitor {
        return Monitor()
    }

    @Provides
    fun provideStorage(): Storage {
        return Storage()
    }

    @Provides
    fun provideMemory(): Memory {
        return Memory()
    }

    @Provides
    fun provideProcessor(): Processor {
        return Processor()
    }

    @Provides
    fun provideComputerTower(
        storage: Storage,
        memory: Memory,
        processor: Processor
    ): ComputerTower {
        return ComputerTower(storage, memory, processor)
    }

    @Provides
    fun provideKeyboard(): Keyboard {
        return Keyboard()
    }

    @Provides
    fun provideMouse(): Mouse {
        return Mouse()
    }

    @Provides
    fun provideComputer(
        monitor: Monitor,
        computerTower: ComputerTower,
        keyboard: Keyboard,
        mouse: Mouse,
    ): Computer {
        return Computer(monitor, computerTower, keyboard, mouse)
    }
}