package com.beletskiy.bullscows.ui.game

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beletskiy.bullscows.game.GameController
import com.beletskiy.bullscows.game.Guess
import com.beletskiy.bullscows.utils.DuplicateNumbersException

class GameViewModel : ViewModel() {

    private val gameController = GameController()

    val picker1 = MutableLiveData(1)
    val picker2 = MutableLiveData(2)
    val picker3 = MutableLiveData(3)
    val picker4 = MutableLiveData(4)

    private val _guessList = MutableLiveData<List<Guess>>(emptyList())
    val guessList: LiveData<List<Guess>>
        get() = _guessList

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
        _guessList.value = ArrayList()
    }

    // called when TRY button tapped. Takes values of four NumberPickers
    fun onTryTapped() {
        // validate user input
        val userInput: List<Int> = try {
            gameController.isUserInputValid(
                picker1.value!!,
                picker2.value!!,
                picker3.value!!,
                picker4.value!!,
            )
        } catch (_: DuplicateNumbersException) {
            _eventDuplicateNumbers.value = true
            return
        } catch (e: IllegalArgumentException) {
            return
        }

        val newGuessList = _guessList.value?.toMutableList() ?: mutableListOf()
        // evaluate user input, create a new Guess and add it to the list
        val newGuess = gameController.evaluateUserInput(userInput)
        newGuessList.add(newGuess)
        _guessList.value = newGuessList

        // check if game is over
        if (gameController.isGameOver(newGuess)) {
            // blocking user input - hides pickers' container
            _pickersContainerVisible.value = View.INVISIBLE
            // to change GameFragment caption
            _gameIsOver.value = true
        }
    }

    // called when RESET button tapped
    fun onResetTapped() {
        picker1.value = 1
        picker2.value = 2
        picker3.value = 3
        picker4.value = 4
        _guessList.value = ArrayList()
        _pickersContainerVisible.value = View.VISIBLE
        _gameIsOver.value = false
        gameController.reset()
    }

    // when event "DuplicateNumbers" complete resets variable value to default
    fun onDuplicateNumbersComplete() {
        _eventDuplicateNumbers.value = false
    }
}
