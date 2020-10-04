package com.villejuif.fdjfrontparissportifs

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    private lateinit var appConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = this.findNavController(R.id.myNavHostFragment)

        appConfiguration = AppBarConfiguration.Builder(R.id.mainFragment).build()

        NavigationUI.setupActionBarWithNavController(this, navController)

        setupPermissions()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        navController.navigate(R.id.action_detailsFragment_to_mainFragment)
        return NavigationUI.navigateUp(navController, appConfiguration)
    }

    private fun setupPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.INTERNET
                ), 1
            )
        }
    }

}