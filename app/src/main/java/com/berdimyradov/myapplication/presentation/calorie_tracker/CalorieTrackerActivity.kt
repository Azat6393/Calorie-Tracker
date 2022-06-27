package com.berdimyradov.myapplication.presentation.calorie_tracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.berdimyradov.myapplication.R
import com.berdimyradov.myapplication.databinding.ActivityCalorieTrackerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalorieTrackerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalorieTrackerBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCalorieTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
            navController = navHostFragment.findNavController()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}