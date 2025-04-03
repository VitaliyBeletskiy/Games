package com.beletskiy.ttt.ui.di

import com.beletskiy.ttt.data.ITicTacToeGame
import com.beletskiy.ttt.data.TicTacToeGameImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GameModule {
    @Provides
    fun provideTicTacToeGame(): ITicTacToeGame = ITicTacToeGame.getInstance()
}
