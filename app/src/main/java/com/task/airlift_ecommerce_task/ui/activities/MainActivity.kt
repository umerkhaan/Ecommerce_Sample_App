package com.task.airlift_ecommerce_task.ui.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.task.airlift_ecommerce_task.R
import com.task.airlift_ecommerce_task.databinding.ActivityMainBinding
import com.task.airlift_ecommerce_task.ui.sharedViewModels.MainViewModel
import com.task.airlift_ecommerce_task.utils.NetworkUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    private val localBroadReceiver = LocalBroadReceiver()

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

    override fun onResume() {
        super.onResume()

        registerLocalBroadcast()
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterLocalBroadcast()
    }

    /////////////////////////////////////////////////////////////////////
    //////////               Methods related to Broadcast
    /////////////////////////////////////////////////////////////////////

    fun registerLocalBroadcast() {
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(localBroadReceiver, filter)
    }

    fun unregisterLocalBroadcast() {
        unregisterReceiver(localBroadReceiver)
    }

    inner class LocalBroadReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                mainViewModel.isNetworkAvailable.value =
                    NetworkUtil.isNetworkAvailable(this@MainActivity)
            }
        }
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
        if (!mainViewModel.getCartItemsCount().hasActiveObservers()) {
            mainViewModel.getCartItemsCount().observe(this) { count ->
                if (count > 0) {
                    binding.bottomNav.getOrCreateBadge(R.id.cartFragment).number = count
                }else{
                    binding.bottomNav.removeBadge(R.id.cartFragment)
                }
            }
        }
    }
}