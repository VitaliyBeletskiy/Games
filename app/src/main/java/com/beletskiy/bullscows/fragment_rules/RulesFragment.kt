package com.beletskiy.bullscows.fragment_rules

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.beletskiy.bullscows.R
import com.beletskiy.bullscows.databinding.FragmentRulesBinding

class RulesFragment : Fragment() {

    private var _binding: FragmentRulesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRulesBinding.inflate(inflater, container, false)
        binding.rulesToolbar.setNavigationIcon(R.drawable.ic__back)
        binding.rulesToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRulesText()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /// reads Rules text from string resources and adds it to TextView
    private fun setRulesText() {

        val textView = binding.textViewRules
        val spannableText = SpannableString(resources.getString(R.string.rules_text))

        textView.text = spannableText
            .addImage("img1", R.drawable.ic_bull)
            .addImage("img2", R.drawable.ic_cow)
    }

    /// replaces imgTag inside SpannableString with drawable with id = imgId
    private fun SpannableString.addImage(imgTag: String, imgId: Int): SpannableString {
        val startIndex = this.indexOf(imgTag)
        val endIndex = startIndex + 4
        val drawable = ContextCompat.getDrawable(requireContext(), imgId)
        drawable!!.setBounds(0, 0, 44, 44)
        val imageSpan = ImageSpan(drawable, ImageSpan.ALIGN_BASELINE)

        this.setSpan(
            imageSpan,
            startIndex,
            endIndex,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        return this
    }
}
