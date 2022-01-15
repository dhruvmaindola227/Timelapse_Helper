package com.dhruv.timelapsehelper

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.media.Image
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhruv.timelapsehelper.adapters.CameraListAdapter
import com.dhruv.timelapsehelper.models.CameraListModel
import okhttp3.*
import org.json.JSONArray
import java.io.InputStream


class CameraListActivity : AppCompatActivity() {

    lateinit var cameraListRecyclerView:RecyclerView
    lateinit var cameraLayoutManager:RecyclerView.LayoutManager
    lateinit var cameraListAdapter:CameraListAdapter
    var cameraList = arrayListOf<CameraListModel>()
    lateinit var cameraListToolbar:Toolbar
    lateinit var searchLayout: LinearLayout
    lateinit var txtSearchView:EditText
    lateinit var iconClose:ImageView
    lateinit var iconSearch:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_list)

        cameraListRecyclerView = findViewById(R.id.cameraListRecyclerView)
        cameraLayoutManager=LinearLayoutManager(this)
        cameraListToolbar=findViewById(R.id.cameraListToolbar)

        searchLayout=findViewById(R.id.searchLayout)
        txtSearchView=findViewById(R.id.txtSearchView)
        iconClose=findViewById(R.id.iconClose)
        iconSearch=findViewById(R.id.iconSearch)

        searchLayout.visibility = View.GONE

        cameraListToolbar.setNavigationOnClickListener {
            val intent = Intent(this, ShutterSpeedActivity::class.java)
            startActivity(intent)
        }
        buildList()

        iconSearch.setOnClickListener {
            searchLayout.visibility = View.VISIBLE
        }
        iconClose.setOnClickListener{
            searchLayout.visibility = View.GONE
            txtSearchView.setText("")
        }

        val handle = Handler()
        val postToServerRunnable = Runnable {
            val searchname = txtSearchView.text.toString().trim()
            searchCamera(searchname)
        }

        txtSearchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                handle.removeCallbacks(postToServerRunnable)
                handle.postDelayed(postToServerRunnable, 100)
            }
        })

    }

    fun buildList(){
        var json:String? = null
            val inputStream:InputStream = application.assets.open("camera.json")
            json = inputStream.bufferedReader().use { it.readText() }
            val jsonarr = JSONArray(json)
            print(jsonarr)
            for (i in 0 until (jsonarr.length())) {
                val jsonObj = jsonarr.getJSONObject(i)
                val camera = jsonObj.getString("camera")
                val sensorSize = jsonObj.getString("sensorSize")
                val resolution = jsonObj.getString("resolution")
                val pixelPitch = jsonObj.getString("pixelPitch")
                val year = jsonObj.getString("year")
                val camList = CameraListModel(
                        camera.toString(),
                        sensorSize.toString(),
                        resolution.toString(),
                        pixelPitch.toString(),
                        year.toInt(),
                )
                        cameraList.add(camList)
            }
        cameraListAdapter = CameraListAdapter(
                this,
                cameraList
        )
        cameraListRecyclerView.adapter = cameraListAdapter
        cameraListRecyclerView.layoutManager = cameraLayoutManager
    }

    fun searchCamera(name:String){
        var json:String? = null
        val inputStream:InputStream = application.assets.open("camera.json")
        json = inputStream.bufferedReader().use { it.readText() }
        val jsonarr = JSONArray(json)
        print(jsonarr)
        for (i in 0 until (jsonarr.length())) {
            val jsonObj = jsonarr.getJSONObject(i)
            if(name.contains(jsonObj.getString("camera"),ignoreCase = true)){
                val camera = jsonObj.getString("camera")
                val sensorSize = jsonObj.getString("sensorSize")
                val resolution = jsonObj.getString("resolution")
                val pixelPitch = jsonObj.getString("pixelPitch")
                val year = jsonObj.getString("year")
                val camList = CameraListModel(
                        camera.toString(),
                        sensorSize.toString(),
                        resolution.toString(),
                        pixelPitch.toString(),
                        year.toInt(),
                )
                cameraList.add(camList)
                cameraListAdapter = CameraListAdapter(
                        this,
                        cameraList
                )
                cameraListRecyclerView.adapter = cameraListAdapter
                cameraListRecyclerView.layoutManager = cameraLayoutManager
                cameraListAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onPause() {
        finish()
        super.onPause()
    }
}
