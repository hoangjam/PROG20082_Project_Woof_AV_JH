package com.example.prog20082_project_av_jh.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.prog20082_project_av_jh.HalfSwipeFragment
import com.example.prog20082_project_av_jh.MatchesFragment
import com.example.prog20082_project_av_jh.R
import com.example.prog20082_project_av_jh.ui.profile.ProfileFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.swipe_half_fragment.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var navController: NavController

    private lateinit var header: LinearLayout

    private lateinit var navHost : NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        //added from https://proandroiddev.com/easy-approach-to-navigation-drawer-7fe87d8fd7e7
        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_swipe_half, R.id.nav_profile, R.id.nav_matches
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //to access header in nav bar
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val headerView: View = navigationView.getHeaderView(0)

        header = headerView.findViewById(R.id.nav_header)
        header.setOnClickListener(this)

        navHost = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)!!
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                header.id -> {
//                    navController.navigate()
                    Log.e("FROM MAIN ACTIVITY:+++", "header CLICKED!!! :))))))")
//                    changeFragment(ProfileFragment())
                    navController.navigate(R.id.nav_profile)
                    onBackPressed()
                }
            }
        }
    }

    fun changeFragment (fragment: Fragment){
        //check current fragment, alter action based on that
        //
        //this.navController.navigate(R.id.action_nav_swipe_half_to_nav_profile)

        this.navController.navigate(R.id.nav_profile)

//        val childFragment = navHost.childFragmentManager.getFragment()
//
//        if (childFragment is HalfSwipeFragment){
//
//        }else if (childFragment is MatchesFragment){
//
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
//                drawer_layout.openDrawer(GravityCompat.START)
//                return true
            }
//
//            R.id.nav_logout -> {
//                Log.e("FROM MAIN ACTIVITY::::: ++++++", "logout CLICKED!!! :))))))")
//                this.finishAffinity()
//                val signInIntent = Intent(this, LandingActivity::class.java)
//                startActivity(signInIntent)
//            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}