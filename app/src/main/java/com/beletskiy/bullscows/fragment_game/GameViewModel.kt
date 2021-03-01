package com.beletskiy.bullscows.fragment_game

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beletskiy.bullscows.utils.TAG
import com.beletskiy.bullscows.game.Attempt
import com.beletskiy.bullscows.game.GameController
import com.beletskiy.bullscows.utils.RepeatingNumbersException
import java.lang.IllegalArgumentException

class GameViewModel : ViewModel() {

    private val gameController = GameController()

    //<editor-fold desc="Pickers">
    // FIXME: to try to change to collection of four
    val picker1 = MutableLiveData(1)
    val picker2 = MutableLiveData(2)
    val picker3 = MutableLiveData(3)
    val picker4 = MutableLiveData(4)
    //</editor-fold>

    /// contains users's attempts to guess the secret number
    private val _attemptList = MutableLiveData<ArrayList<Attempt>>()
    val attemptList: LiveData<ArrayList<Attempt>>
        get() = _attemptList

    // to trigger code in case of "RepeatingNumbers" event
    private val _eventRepeatingNumbers = MutableLiveData<Boolean>()
    val eventRepeatingNumbers: LiveData<Boolean>
        get() = _eventRepeatingNumbers

    // to trigger code in case of "AttemptAddedAtPosition" event
    private val _eventAttemptAddedAtPosition = MutableLiveData<Int>()
    val eventAttemptAddedAtPosition: LiveData<Int>
        get() = _eventAttemptAddedAtPosition

    // shows/hides pickers' container in GameFragment
    private val _pickersContainerVisible = MutableLiveData<Int>()
    val pickersContainerVisible: LiveData<Int>
        get() = _pickersContainerVisible

    // controls GameFragment caption
    private val _gameIsOver = MutableLiveData<Boolean>()
    val gameIsOver: LiveData<Boolean>
        get() = _gameIsOver

    init {
        _eventRepeatingNumbers.value = false
        _eventAttemptAddedAtPosition.value = 0
        _pickersContainerVisible.value = View.VISIBLE
        _gameIsOver.value = false
        _attemptList.value = ArrayList()
    }

    /// called when TRY button tapped. Takes values of four NumberPickers
    fun onTryTapped() {
        Log.i(
            TAG,
            "Pickers(1-4): ${picker1.value} ${picker2.value} ${picker3.value} ${picker4.value}"
        )

        // validate user input
        val attemptValues: List<Int> = try {
            gameController.isUserInputValid(
                picker1.value!!,
                picker2.value!!,
                picker3.value!!,
                picker4.value!!
            )
        } catch (_: RepeatingNumbersException) {
            _eventRepeatingNumbers.value = true
            return
        } catch (_: IllegalArgumentException) {
            // FIXME: fatal error?
            Log.i(TAG, "onTryTapped: IllegalArgumentException")
            return
        }

        // evaluate attempt, add new Attempt to ArrayList
        val newAttempt = gameController.evaluateAttempt(attemptValues)
        _attemptList.value?.add(newAttempt)
        _attemptList.value = _attemptList.value

        // in order to scroll the list to the last position
        _eventAttemptAddedAtPosition.value = _attemptList.value?.size

        // check if game is over
        if (gameController.isGameOver(newAttempt)) {
            Log.i(TAG, "isGameOver = true ")
            // blocking user input - hides pickers' container
            _pickersContainerVisible.value = View.INVISIBLE
            // to change GameFragment caption
            _gameIsOver.value = true
        }
    }

    /// called when RESET button tapped
    fun onResetTapped() {
        picker1.value = 1
        picker2.value = 2
        picker3.value = 3
        picker4.value = 4
        _attemptList.value = ArrayList()
        _pickersContainerVisible.value = View.VISIBLE
        _gameIsOver.value = false
        gameController.reset()
    }

    /// when event "RepeatingNumbers" complete resets variable value to default
    fun onRepeatingNumbersComplete() {
        _eventRepeatingNumbers.value = false
    }

    /// when event "AttemptAddedAtPosition" complete resets variable value to default
    fun onAttemptAddedAtPositionComplete() {
        _eventAttemptAddedAtPosition.value = 0
    }

}