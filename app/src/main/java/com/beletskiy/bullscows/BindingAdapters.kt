package com.beletskiy.bullscows.fragment_game

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beletskiy.bullscows.R
import com.beletskiy.bullscows.game.Attempt

@BindingAdapter("attemptImage")
fun ImageView.setAttemptImage(attemptValue: Int?) {
    attemptValue?.let {
        setImageResource(
            when (it) {
                0 -> R.drawable.ic_0
                1 -> R.drawable.ic_1
                2 -> R.drawable.ic_2
                3 -> R.drawable.ic_3
                4 -> R.drawable.ic_4
                5 -> R.drawable.ic_5
                6 -> R.drawable.ic_6
                7 -> R.drawable.ic_7
                8 -> R.drawable.ic_8
                9 -> R.drawable.ic_9
                else -> R.drawable.ic_0
            }
        )
    }
}

@BindingAdapter("resultImage")
fun ImageView.setResultImage(result: Attempt.Result?) {
    result?.let {
        setImageResource(
            when (it) {
                Attempt.Result.BULL -> R.drawable.ic_bull
                Attempt.Result.COW -> R.drawable.ic_cow
                Attempt.Result.NOTHING -> R.drawable.ic_nothing
            }
        )
    }
}

@BindingAdapter("attemptListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: ArrayList<Attempt>?) {
    val adapter = recyclerView.adapter as AttemptAdapter
    adapter.submitList(data)
}

@BindingAdapter("caption")
fun setGameFragmentCaption(textView: TextView, isGameOver: Boolean?) {
    if (null != isGameOver && isGameOver) {
        textView.setText(R.string.caption_when_game_is_over)
    } else {
        textView.setText(R.string.caption_game_is_not_over)
    }
}