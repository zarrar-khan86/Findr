package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class OnboardingFragment : Fragment() {

    companion object {
        private const val ARG_IMAGE = "image"
        private const val ARG_TITLE = "title"
        private const val ARG_DESC = "description"

        fun newInstance(imageRes: Int, titleRes: Int, descRes: Int): OnboardingFragment {
            val fragment = OnboardingFragment()
            val args = Bundle()
            args.putInt(ARG_IMAGE, imageRes)
            args.putInt(ARG_TITLE, titleRes)
            args.putInt(ARG_DESC, descRes)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)

        val imageView = view.findViewById<ImageView>(R.id.ivOnboarding)
        val titleView = view.findViewById<TextView>(R.id.tvOnboardingTitle)
        val descView = view.findViewById<TextView>(R.id.tvOnboardingDesc)

        arguments?.let {
            imageView.setImageResource(it.getInt(ARG_IMAGE))
            titleView.setText(it.getInt(ARG_TITLE))
            descView.setText(it.getInt(ARG_DESC))
        }

        return view
    }
}

