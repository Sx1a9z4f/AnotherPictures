package git.oversadboy.anotherpictures.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import git.oversadboy.anotherpictures.R

class MainActivity : AppCompatActivity() {

    private lateinit var sheredPreferences: SharedPreferences

    companion object {
        lateinit var authToken: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sheredPreferences = getSharedPreferences("auth_token", Context.MODE_PRIVATE)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_collections
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        if (TextUtils.isEmpty(
                getSharedPreferences("auth_token", Context.MODE_PRIVATE)
                    .getString("token", null))
        ){
            startActivity(Intent(this,StartActivity::class.java))
        }
    }
}
