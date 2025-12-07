package com.example.project

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnSkip: Button
    private lateinit var btnNext: Button
    private lateinit var tvPageCounter: TextView
    private lateinit var indicator1: View
    private lateinit var indicator2: View
    private lateinit var indicator3: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Hide action bar
        supportActionBar?.hide()

        viewPager = findViewById(R.id.viewPager)
        btnSkip = findViewById(R.id.btnSkip)
        btnNext = findViewById(R.id.btnNext)
        tvPageCounter = findViewById(R.id.tvPageCounter)
        indicator1 = findViewById(R.id.indicator1)
        indicator2 = findViewById(R.id.indicator2)
        indicator3 = findViewById(R.id.indicator3)

        // Setup ViewPager with adapter
        val adapter = OnboardingAdapter(this)
        viewPager.adapter = adapter

        // Skip button - go directly to home
        btnSkip.setOnClickListener {
            navigateToHome()
        }

        // Next button - go to next page or home
        btnNext.setOnClickListener {
            if (viewPager.currentItem < 2) {
                viewPager.currentItem += 1
            } else {
                navigateToHome()
            }
        }

        // Update button text, page counter, and indicators based on current page
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                // Update button text
                if (position == 2) {
                    btnNext.text = getString(R.string.get_started)
                } else {
                    btnNext.text = getString(R.string.next)
                }

                // Update page counter
                tvPageCounter.text = "${position + 1} of 3"

                // Update indicators
                updateIndicators(position)
            }
        })
    }

    private fun updateIndicators(position: Int) {
        val activeDrawable = R.drawable.indicator_active
        val inactiveDrawable = R.drawable.indicator_inactive

        indicator1.setBackgroundResource(if (position == 0) activeDrawable else inactiveDrawable)
        indicator2.setBackgroundResource(if (position == 1) activeDrawable else inactiveDrawable)
        indicator3.setBackgroundResource(if (position == 2) activeDrawable else inactiveDrawable)
    }

    private fun navigateToHome() {
        // TODO: Replace with actual home activity when created
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

