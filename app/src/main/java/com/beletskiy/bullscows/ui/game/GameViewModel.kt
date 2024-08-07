package com.beletskiy.bullscows.ui.game

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.beletskiy.bullscows.game.Guess
import com.beletskiy.bullscows.game.IGameController
import com.beletskiy.bullscows.utils.DuplicateNumbersException

class GameViewModel(private val gameController: IGameController) : ViewModel() {

    val picker1 = MutableLiveData(1)
    val picker2 = MutableLiveData(2)
    val picker3 = MutableLiveData(3)
    val picker4 = MutableLiveData(4)

    val guessList: LiveData<List<Guess>> = gameController.guesses.asLiveData()

    // to trigger code in case of "DuplicateNumbers" event
    private val _eventDuplicateNumbers = MutableLiveData<Boolean>()
    val eventDuplicateNumbers: LiveData<Boolean>
        get() = _eventDuplicateNumbers

    // shows/hides pickers' container in GameFragment
    private val _pickersContainerVisible = MutableLiveData<Int>()
    val pickersContainerVisible: LiveData<Int>
        get() = _pickersContainerVisible

    // controls GameFragment caption
    private val _gameIsOver = MutableLiveData<Boolean>()
    val gameIsOver: LiveData<Boolean>
        get() = _gameIsOver

    init {
        _eventDuplicateNumbers.value = false
        _pickersContainerVisible.value = View.VISIBLE
        _gameIsOver.value = false
    }

    // called when TRY button tapped. Takes values of four NumberPickers
    fun onTryTapped() {
        val pickers = listOf(
            picker1.value!!,
            picker2.value!!,
            picker3.value!!,
            picker4.value!!,
        )
        // validate user input
        val userInput: List<Int> = try {
            validateUserInput(pickers)
        } catch (_: DuplicateNumbersException) {
            _eventDuplicateNumbers.value = true
            return
        } catch (e: IllegalArgumentException) {
            return
        }

        if (gameController.evaluateUserInput(userInput)) {
            // blocking user input - hides pickers' container
            _pickersContainerVisible.value = View.INVISIBLE
            // to change GameFragment caption
            _gameIsOver.value = true
        }
    }

    private fun validateUserInput(userInput: List<Int>): List<Int> {
        require(4 == userInput.size) { "There must be four Int." }
        if (userInput.toSet().size != userInput.size) {
            throw DuplicateNumbersException("There are repeating numbers.")
        }
        return userInput
    }

    // called when RESET button tapped
    fun onResetTapped() {
        picker1.value = 1
        picker2.value = 2
        picker3.value = 3
        picker4.value = 4
        _pickersContainerVisible.value = View.VISIBLE
        _gameIsOver.value = false
        gameController.restart()
    }

    // when event "DuplicateNumbers" complete resets variable value to default
    fun onDuplicateNumbersComplete() {
        _eventDuplicateNumbers.value = false
    }
}

@Suppress("UNCHECKED_CAST")
class GameViewModelFactory(private val gameController: IGameController) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(gameController) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
