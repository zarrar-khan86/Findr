package com.example.project

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardingFragment.newInstance(
                R.drawable.ic_onboarding_1,
                R.string.onboarding_title_1,
                R.string.onboarding_desc_1
            )
            1 -> OnboardingFragment.newInstance(
                R.drawable.ic_onboarding_2,
                R.string.onboarding_title_2,
                R.string.onboarding_desc_2
            )
            2 -> OnboardingFragment.newInstance(
                R.drawable.ic_onboarding_3,
                R.string.onboarding_title_3,
                R.string.onboarding_desc_3
            )
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}

