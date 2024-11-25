package com.beletskiy.bullscows.compose.di

import com.beletskiy.bullscows.game.IGameController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class GameModule {
    @Provides
    @ViewModelScoped
    fun provideGameController(): IGameController = IGameController.getInstance()
}
