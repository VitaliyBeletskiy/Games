package com.beletskiy.bullscows.ui.game

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.beletskiy.bullscows.game.Guess
import com.beletskiy.bullscows.game.IGameController
import com.beletskiy.bullscows.game.ifFailure
import com.beletskiy.bullscows.utils.DuplicateNumbersException
import kotlinx.coroutines.flow.map

class GameViewModel(private val gameController: IGameController) : ViewModel() {

    val picker1 = MutableLiveData(1)
    val picker2 = MutableLiveData(2)
    val picker3 = MutableLiveData(3)
    val picker4 = MutableLiveData(4)

    val guessList: LiveData<List<Guess>> = gameController.guesses.asLiveData()

    // to trigger code in case of "DuplicateNumbers" event
    private val _eventDuplicateNumbers = MutableLiveData(false)
    val eventDuplicateNumbers: LiveData<Boolean>
        get() = _eventDuplicateNumbers

    // shows/hides pickers' container in GameFragment
    val pickersContainerVisible = gameController.isGameOver.map {
        if (it) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }.asLiveData(timeoutInMs = 5_000)

    // controls GameFragment caption
    val gameIsOver: LiveData<Boolean> = gameController.isGameOver.asLiveData(timeoutInMs = 5_000)

    // called when TRY button tapped. Takes values of four NumberPickers
    fun onTryTapped() {
        val pickers = listOf(
            picker1.value!!,
            picker2.value!!,
            picker3.value!!,
            picker4.value!!,
        )

        gameController.evaluateUserInput(pickers).ifFailure {
            if (it is DuplicateNumbersException) {
                _eventDuplicateNumbers.value = true
            }
        }
    }

    // called when RESET button tapped
    fun onResetTapped() {
        picker1.value = 1
        picker2.value = 2
        picker3.value = 3
        picker4.value = 4
        gameController.restart()
    }

    // when event "DuplicateNumbers" complete resets variable value to default
    fun onDuplicateNumbersComplete() {
        _eventDuplicateNumbers.value = false
    }
}

@Suppress("UNCHECKED_CAST")
class GameViewModelFactory(private val gameController: IGameController) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(gameController) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
