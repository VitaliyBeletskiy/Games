package com.beletskiy.bullscows.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.beletskiy.bullscows.R
import com.beletskiy.bullscows.databinding.FragmentGameBinding
import com.beletskiy.bullscows.game.IGameController
import com.google.android.material.snackbar.Snackbar

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory(IGameController.getInstance())
    }
    private val guessAdapter = GuessAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentGameBinding.inflate(inflater)
        binding.gameViewModel = gameViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        binding.guessRecyclerView.adapter = GuessAdapter()

        gameViewModel.guessList.observe(viewLifecycleOwner) {
            it?.let { list ->
                guessAdapter.submitList(list) {
                    // scroll to the last position in the list
                    if (list.isNotEmpty()) {
                        binding.guessRecyclerView.smoothScrollToPosition(list.size - 1)
                    }
                }
            }
        }

        // in case of duplicate numbers - notify user
        gameViewModel.eventDuplicateNumbers.observe(viewLifecycleOwner) {
            if (it) {
                Snackbar.make(
                    binding.mainContainer,
                    R.string.repeating_numbers_warning,
                    Snackbar.LENGTH_SHORT,
                ).show()
                gameViewModel.onDuplicateNumbersComplete()
            }
        }
    }

    private fun setupToolbar() {
        binding.toolbarLayout.buttonRules.setOnClickListener {
            this.findNavController().navigate(R.id.action_gameFragment_to_rulesFragment)
        }
        binding.toolbarLayout.buttonRestart.setOnClickListener {
            gameViewModel.onResetTapped()
        }
    }
}
