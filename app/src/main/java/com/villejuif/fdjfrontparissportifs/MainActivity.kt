package com.villejuif.fdjfrontparissportifs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.villejuif.fdjfrontparissportifs.R

class MainActivity : AppCompatActivity() {

    private lateinit var appConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = this.findNavController(R.id.myNavHostFragment)

        appConfiguration = AppBarConfiguration.Builder(R.id.mainFragment).build()


        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        navController.navigate(R.id.action_detailsFragment_to_mainFragment)
        return NavigationUI.navigateUp(navController, appConfiguration)
    }

}