package com.example.projectmobile


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.menu_page.*


class MenuPage : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_page)

        auth = Firebase.auth

        val menutoolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        setSupportActionBar(menutoolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawer, menutoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        toolbar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_menu_black_24dp)
        toolbar?.setNavigationOnClickListener {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START)
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MessageFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_message)
        }
    }

    override fun onNavigationItemSelected(item : MenuItem) : Boolean {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        when (item.itemId)
        {
            R.id.nav_profile -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ProfileFragment()).commit()
            }
            R.id.nav_message -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MessageFragment()).commit()
            }
            R.id.nav_history -> {
                goToHistoryPage()
            }
            R.id.nav_twitch -> {
                goToTwitchPage()
            }
            R.id.nav_calculator -> {
                goToTipCalculator()
            }
            R.id.nav_exit -> {
                auth.signOut()
                finish()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true;
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private fun goToTipCalculator() {
        val intent = Intent(this, TipCalculatorPage::class.java)
        startActivity(intent)
    }

    private fun goToTwitchPage() {
        val intent = Intent(this, TwitchPage::class.java)
        startActivity(intent)
    }

    private fun goToHistoryPage() {
        val intent = Intent(this, HistoryPage::class.java)
        startActivity(intent)
    }
}
