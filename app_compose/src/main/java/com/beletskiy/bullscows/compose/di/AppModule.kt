package com.beletskiy.bullscows.compose.di

import com.beletskiy.bullscows.game.IGameController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideGameController(): IGameController = IGameController.getInstance()
}
