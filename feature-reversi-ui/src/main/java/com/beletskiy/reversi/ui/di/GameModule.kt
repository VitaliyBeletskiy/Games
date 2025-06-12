package com.beletskiy.reversi.ui.di

import com.beletskiy.reversi.data.IReversiGame
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GameModule {
    @Provides
    fun provideReversiGame(): IReversiGame = IReversiGame.getInstance()
}
