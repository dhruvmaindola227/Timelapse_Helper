package com.dhruv.timelapsehelper

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class TipsAndSuggestionsActivity : AppCompatActivity() {
    lateinit var drawerLayoutAbout:DrawerLayout
    lateinit var aboutLayout:RelativeLayout
    lateinit var toolbarAbout:Toolbar
    lateinit var navigationViewAbout:NavigationView
    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips_and_suggestions)

        drawerLayoutAbout=findViewById(R.id.drawerLayoutAbout)
        aboutLayout=findViewById(R.id.aboutLayout)
        toolbarAbout=findViewById(R.id.toolbarAbout)
        navigationViewAbout=findViewById(R.id.navigationViewAbout)

        toolbarAbout.setNavigationOnClickListener {
            drawerLayoutAbout.openDrawer(Gravity.LEFT)
        }

        navigationViewAbout.menu.findItem(R.id.aboutapp).isChecked = true

        navigationViewAbout.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.dashboard -> {
                    drawerLayoutAbout.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.intervaltable -> {
                    drawerLayoutAbout.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,IntervalTableActivity::class.java)
                    startActivity(intent)
                }
                R.id.shutterSpeed -> {
                    drawerLayoutAbout.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,ShutterSpeedActivity::class.java)
                    startActivity(intent)
                }
                R.id.aboutapp -> {
                    drawerLayoutAbout.closeDrawer(Gravity.LEFT)
                }
                R.id.newaboutapp -> {
                    drawerLayoutAbout.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,AboutApp::class.java)
                    startActivity(intent)
                }
            }

            return@setNavigationItemSelectedListener true
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}