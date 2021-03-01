package com.beletskiy.bullscows.fragment_game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.beletskiy.bullscows.R
import com.beletskiy.bullscows.databinding.FragmentGameBinding
import com.google.android.material.snackbar.Snackbar

/// Fragment with the game
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var gameViewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater)
        setupToolbar()

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.gameViewModel = gameViewModel
        binding.lifecycleOwner = this

        val attemptAdapter = AttemptAdapter()
        binding.attemptRecyclerView.adapter = AttemptAdapter()

        // observe the list of user's attempts
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