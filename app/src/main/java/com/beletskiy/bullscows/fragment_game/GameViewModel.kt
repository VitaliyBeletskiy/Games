package com.beletskiy.bullscows.fragment_game

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beletskiy.bullscows.R
import com.beletskiy.bullscows.TAG
import com.beletskiy.bullscows.game.Attempt
import com.beletskiy.bullscows.game.GameController
import com.beletskiy.bullscows.utils.RepeatingNumbersException
import java.lang.IllegalArgumentException

class GameViewModel : ViewModel() {

    private val gameController = GameController()

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
        _gameIsOver.value =  false
        _attemptList.value = ArrayList()
    }

    /// called when TRY button tapped. Takes values of four NumberPickers
    fun onTryTapped(val1: Int, val2: Int, val3: Int, val4: Int) {
        Log.i(TAG, "Pickers(1-4): $val1 $val2 $val3 $val4")
        Log.i(TAG, "onTryTapped: _attemptList.size = ${_attemptList.value?.size}")

        // validate user input
        val attemptValues: List<Int> = try {
            gameController.isUserInputValid(val1, val2, val3, val4)
        } catch (_: RepeatingNumbersException) {
            _eventRepeatingNumbers.value = true
            return
        } catch (_: IllegalArgumentException) {
            // TODO: fatal error?
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
            _gameIsOver.value =  true
        }
    }

    /// called when RESET button tapped
    fun onResetTapped() {
        _attemptList.value = ArrayList()
        _pickersContainerVisible.value = View.VISIBLE
        _gameIsOver.value =  false
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