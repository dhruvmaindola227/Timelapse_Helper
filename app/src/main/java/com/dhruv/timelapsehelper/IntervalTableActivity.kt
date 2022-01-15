package com.dhruv.timelapsehelper

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class IntervalTableActivity : AppCompatActivity() {

    lateinit var navigationView: NavigationView
    lateinit var toolbarInterval:Toolbar
    lateinit var drawerlayoutinterval:DrawerLayout

    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interval_table)
        navigationView=findViewById(R.id.navigationview)
        drawerlayoutinterval=findViewById(R.id.drawerlayoutinterval)
        toolbarInterval=findViewById(R.id.toolbarInterval)
        navigationView.menu.findItem(R.id.intervaltable).isChecked = true

        toolbarInterval.setNavigationOnClickListener {
            drawerlayoutinterval.openDrawer(Gravity.LEFT)
        }

        navigationView.menu.findItem(R.id.intervaltable).isChecked = true

        navigationView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.dashboard -> {
                    drawerlayoutinterval.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.intervaltable -> {
                    drawerlayoutinterval.closeDrawer(Gravity.LEFT)
                }
                R.id.shutterSpeed -> {
                    drawerlayoutinterval.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,ShutterSpeedActivity::class.java)
                    startActivity(intent)
                }
                R.id.aboutapp -> {
                    drawerlayoutinterval.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,TipsAndSuggestionsActivity::class.java)
                    startActivity(intent)
                }
                R.id.newaboutapp -> {
                    drawerlayoutinterval.closeDrawer(Gravity.LEFT)
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