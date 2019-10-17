package com.wa82bj.check_mvvm.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.wa82bj.check_mvvm.BaseActivity
import com.wa82bj.check_mvvm.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )
        setBottomNavMenu(navController)
        setSlideNavMenu(navController)
        setUpActionBar(navController)

    }

    private fun setBottomNavMenu (navController: NavController){

        bottom_nav?.let {
            NavigationUI.setupWithNavController(it,navController)
        }
    }

    private fun setSlideNavMenu (navController: NavController){

        nav_view?.let {
            NavigationUI.setupWithNavController(it,navController)
        }
    }

    private fun setUpActionBar (navController: NavController){

        NavigationUI.setupActionBarWithNavController(this,navController,drawer_layout)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )
        val navigated = NavigationUI.onNavDestinationSelected(item!!,navController)
        return navigated || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.nav_host_fragment),drawer_layout)
    }


}
