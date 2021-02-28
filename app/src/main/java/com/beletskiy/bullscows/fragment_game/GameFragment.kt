package com.beletskiy.bullscows.fragment_game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.beletskiy.bullscows.R
import com.beletskiy.bullscows.TAG
import com.beletskiy.bullscows.databinding.FragmentGameBinding
import com.google.android.material.snackbar.Snackbar

/// Fragment with the game
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var gameViewModel: GameViewModel

    /// list of four NumberPickers
    private val pickerList by lazy {
        listOf(
            binding.numberPicker1,
            binding.numberPicker2,
            binding.numberPicker3,
            binding.numberPicker4,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container,false)
        binding = FragmentGameBinding.inflate(inflater)
        setupToolbar()

        // setup NumberPickers
        pickerList.forEach {
            it.minValue = 0
            it.maxValue = 9
            it.wrapSelectorWheel = true
            it.value = pickerList.indexOf(it) + 1
        }

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.gameViewModel = gameViewModel
        binding.lifecycleOwner = this

        val attemptAdapter = AttemptAdapter()
        binding.attemptRecyclerView.adapter = AttemptAdapter()
        gameViewModel.attemptList.observe(viewLifecycleOwner, {
            it?.let {
                attemptAdapter.submitList(it)
            }
        })

        // scroll to the last position in the list
        gameViewModel.eventAttemptAddedAtPosition.observe(viewLifecycleOwner, {
            if (it > 0) {
                binding.attemptRecyclerView.smoothScrollToPosition(it)
                gameViewModel.onAttemptAddedAtPositionComplete()
            }
        })

        // in case of repeating numbers - notify user
        gameViewModel.eventRepeatingNumbers.observe(viewLifecycleOwner, {
            if (it) {
                Snackbar.make(
                    binding.mainContainer,
                    R.string.repeating_numbers_warning,
                    Snackbar.LENGTH_SHORT
                ).show()
                gameViewModel.onRepeatingNumbersComplete()
            }
        })

        // when TRY button tapped, get all NumberPickers values and send to ViewModel
        binding.buttonTry.setOnClickListener {
            gameViewModel.onTryTapped(
                binding.numberPicker1.value,
                binding.numberPicker2.value,
                binding.numberPicker3.value,
                binding.numberPicker4.value,
            )
        }

        return binding.root
    }

    /// sets up Fragment's Toolbar
    private fun setupToolbar() {
        binding.buttonRules.setOnClickListener {
            this.findNavController().navigate(R.id.action_gameFragment_to_rulesFragment)
        }
        binding.buttonRestart.setOnClickListener {
            gameViewModel.onResetTapped()
        }
    }

}