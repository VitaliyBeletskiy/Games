package com.beletskiy.fifteen.ui.di

import com.beletskiy.fifteen.data.IFifteenGame
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GameModule {
    @Provides
    fun provideFifteenGame(): IFifteenGame = IFifteenGame.getInstance()
}
