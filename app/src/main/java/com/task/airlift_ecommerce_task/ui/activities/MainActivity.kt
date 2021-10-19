package com.task.airlift_ecommerce_task.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.task.airlift_ecommerce_task.R
import com.task.airlift_ecommerce_task.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    /////////////////////////////////////////////////////////////////////
    //////////               Activity Lifecycle Methods
    /////////////////////////////////////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupViews()
        setupViewListeners()
        setupLiveDataObservers()
    }

    /////////////////////////////////////////////////////////////////////
    //////////                 UI related methods
    /////////////////////////////////////////////////////////////////////

    private fun setupViews() {
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

    }

    private fun setupViewListeners() {

    }

    private fun setupLiveDataObservers() {

    }
}