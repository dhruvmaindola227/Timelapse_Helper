package com.dhruv.timelapsehelper

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class AboutApp : AppCompatActivity() {

    lateinit var aboutToolbar:Toolbar
    lateinit var navigationviewaboutapp:NavigationView
    lateinit var drawerlayoutaboutapp:DrawerLayout

    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)

        aboutToolbar = findViewById(R.id.aboutToolbar)
        navigationviewaboutapp = findViewById(R.id.navigationviewaboutapp)
        drawerlayoutaboutapp = findViewById(R.id.drawerlayoutaboutapp)

        aboutToolbar.setNavigationOnClickListener {
            drawerlayoutaboutapp.openDrawer(Gravity.LEFT)
        }

        navigationviewaboutapp.menu.findItem(R.id.newaboutapp).isChecked = true

        navigationviewaboutapp.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.dashboard -> {
                    drawerlayoutaboutapp.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.intervaltable -> {
                    drawerlayoutaboutapp.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,IntervalTableActivity::class.java)
                    startActivity(intent)
                }
                R.id.shutterSpeed -> {
                    drawerlayoutaboutapp.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,ShutterSpeedActivity::class.java)
                    startActivity(intent)
                }
                R.id.aboutapp -> {
                    drawerlayoutaboutapp.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,TipsAndSuggestionsActivity::class.java)
                    startActivity(intent)
                }
                R.id.newaboutapp -> {
                    drawerlayoutaboutapp.closeDrawer(Gravity.LEFT)
                }
            }

            return@setNavigationItemSelectedListener true
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

}