package com.dhruv.timelapsehelper

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.target.Target
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var toolbar:Toolbar
    lateinit var navigationView: NavigationView
    lateinit var shootingInterval: TextInputLayout
    lateinit var fpsDropDown:TextInputLayout
    lateinit var numberPickerHr:NumberPicker
    lateinit var editEventDuration:TextInputEditText
    lateinit var numberPickerMin:NumberPicker
    lateinit var editClipLength:TextInputEditText
    lateinit var txtEventDuration:TextInputLayout
    lateinit var autocompleteShootingInterval:AutoCompleteTextView
    lateinit var btnCalculate:MaterialButton
    var myTimeHr = "0"
    var myTimeMin = "30"
    lateinit var txtNoOfPhotos:TextView
    lateinit var txtIntervalSelect:TextView
    lateinit var txtIntervalSelectCalculated:TextView
    lateinit var autoCompleteFps:AutoCompleteTextView
    lateinit var txtClipLength:TextInputLayout
    lateinit var noOfPhotos:TextView
    lateinit var framelayout:RelativeLayout
    //
    lateinit var imgFps:ImageView
    lateinit var imgClipLength:ImageView
    lateinit var imgEvtDur:ImageView
//    lateinit var alertBoxImage:ImageView

    @SuppressLint("SetTextI18n", "RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout=findViewById(R.id.drawerlayout)
        toolbar=findViewById(R.id.toolbar)
        navigationView=findViewById(R.id.navigationview)
        shootingInterval=findViewById(R.id.shootingInterval)
        fpsDropDown=findViewById(R.id.fpsDropDown)
        numberPickerHr=findViewById(R.id.numberPickerHr)
        editEventDuration=findViewById(R.id.editEventDuration)
        numberPickerMin=findViewById(R.id.numberPickerMin)
        editClipLength=findViewById(R.id.editClipLength)
        txtEventDuration=findViewById(R.id.txtEventDuration)
        autocompleteShootingInterval=findViewById(R.id.autocompleteShootingInterval)
        btnCalculate=findViewById(R.id.btnCalculate)
        txtNoOfPhotos=findViewById(R.id.txtNoOfPhotos)
        txtIntervalSelect=findViewById(R.id.txtIntervalSelect)
        txtIntervalSelectCalculated=findViewById(R.id.txtIntervalSelectCalculated)
        autoCompleteFps=findViewById(R.id.autoCompleteFps)
        txtClipLength=findViewById(R.id.txtClipLength)
        noOfPhotos=findViewById(R.id.noOfPhotos)
        framelayout=findViewById(R.id.framelayout)
//        setUpToolbar()
        imgFps = findViewById(R.id.imgFps)
        imgClipLength = findViewById(R.id.imgClipLength)
        imgEvtDur = findViewById(R.id.imgEvtDur)
        //
//        alertBoxImage = findViewById(R.id.alertBoxImage)
//        val imageViewTarget = DrawableImageViewTarget(alertBoxImage)
//        Glide.with(this@MainActivity).load(R.drawable.fps).into(imageViewTarget)

        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }

        navigationView.menu.findItem(R.id.dashboard).isChecked = true

        navigationView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.dashboard -> {
                    drawerLayout.closeDrawer(Gravity.LEFT)
                }
                R.id.intervaltable -> {
                    drawerLayout.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this, IntervalTableActivity::class.java)
                    startActivity(intent)
                }
                R.id.shutterSpeed -> {
                    drawerLayout.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this, ShutterSpeedActivity::class.java)
                    startActivity(intent)
                }
                R.id.aboutapp -> {
                    drawerLayout.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this, TipsAndSuggestionsActivity::class.java)
                    startActivity(intent)
                }
                R.id.newaboutapp -> {
                    drawerLayout.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,AboutApp::class.java)
                    startActivity(intent)
                }
            }

            return@setNavigationItemSelectedListener true
        }

        val items = listOf("Shooting Interval", "Clip Length", "Event Duration")

        val adapter = ArrayAdapter(applicationContext, R.layout.list_item, items)
        (shootingInterval.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        val items2 = listOf("24", "25", "30", "60")
        val adapter2 = ArrayAdapter(applicationContext, R.layout.list_item, items2)
        (fpsDropDown.editText as? AutoCompleteTextView)?.setAdapter(adapter2)

        numberPickerHr.minValue = 0
        numberPickerHr.maxValue = 24
        numberPickerHr.value = 0

        numberPickerMin.minValue = 0
        numberPickerMin.maxValue = 60
        numberPickerMin.value = 30

        editEventDuration.setText("$myTimeHr Hr $myTimeMin Min")

        numberPickerHr.setOnValueChangedListener { numberPicker, i, i2 ->
            myTimeHr = i2.toString()
            if(autocompleteShootingInterval.text.toString() == "Event Duration"){
                editEventDuration.setText("$myTimeHr Min $myTimeMin Sec")
            }else{
                editEventDuration.setText("$myTimeHr Hr $myTimeMin Min")
            }
        }

        numberPickerMin.setOnValueChangedListener { numberPicker, i, i2 ->
            myTimeMin = i2.toString()
            if(autocompleteShootingInterval.text.toString() == "Event Duration"){
                editEventDuration.setText("$myTimeHr Min $myTimeMin Sec")
            }else{
                editEventDuration.setText("$myTimeHr Hr $myTimeMin Min")
            }
        }

        autocompleteShootingInterval.setOnItemClickListener { adapterView, view, i, l ->
            if(i.toString() == "0"){
                txtClipLength.hint = "Clip Length (in seconds)"
                txtEventDuration.hint = "Event Duration"
                editEventDuration.setText("$myTimeHr Hr $myTimeMin Min")
                numberPickerHr.minValue = 0
                numberPickerHr.maxValue = 24
                numberPickerHr.value = 0

                numberPickerMin.minValue = 0
                numberPickerMin.maxValue = 60
                numberPickerMin.value = 30
            }
            if (i.toString() == "1"){
                txtClipLength.hint = "Interval (in seconds)"
                txtEventDuration.hint = "Event Duration"
                editEventDuration.setText("$myTimeHr Hr $myTimeMin Min")
                numberPickerHr.minValue = 0
                numberPickerHr.maxValue = 24
                numberPickerHr.value = 0

                numberPickerMin.minValue = 0
                numberPickerMin.maxValue = 60
                numberPickerMin.value = 30
            }
            if(i.toString() == "2"){
                txtClipLength.hint = "Clip Length (in seconds)"
                txtEventDuration.hint = "Interval"
                editEventDuration.setText("$myTimeHr Min $myTimeMin Sec")
                numberPickerHr.minValue = 0
                numberPickerHr.maxValue = 60
                numberPickerHr.value = 0

                numberPickerMin.minValue = 0
                numberPickerMin.maxValue = 60
                numberPickerMin.value = 30
            }
        }

        btnCalculate.setOnClickListener {
            val shootingInterval = autocompleteShootingInterval.text.toString().trim()
            calculate(shootingInterval)
        }

        imgFps.setOnClickListener {
          showPopup(R.string.fps,R.string.fps_desc,R.drawable.fps)
        }
        imgClipLength.setOnClickListener {
            if(autocompleteShootingInterval.text.toString() == "Shooting Interval"){
                showPopup(R.string.clip_length, R.string.clip_length_desc,R.drawable.clip_length)
            }
            if(autocompleteShootingInterval.text.toString() == "Clip Length"){
                showPopup(R.string.interval, R.string.interval_desc,R.drawable.interval)
            }
            if(autocompleteShootingInterval.text.toString() == "Event Duration"){
                showPopup(R.string.clip_length, R.string.clip_length_desc,R.drawable.clip_length)
            }
        }
        imgEvtDur.setOnClickListener {
            if(autocompleteShootingInterval.text.toString() == "Shooting Interval"){
                showPopup(R.string.event_duration, R.string.event_duration_desc,R.drawable.evnt_dur)
            }
            if(autocompleteShootingInterval.text.toString() == "Clip Length"){
                showPopup(R.string.event_duration, R.string.event_duration_desc,R.drawable.evnt_dur)
            }
            if(autocompleteShootingInterval.text.toString() == "Event Duration"){
                showPopup(R.string.interval, R.string.interval_desc,R.drawable.interval)
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun calculate(shootingInterval: String){
        if(autoCompleteFps.text.isNotBlank() && editClipLength.text!!.isNotBlank()){
            val fpsDropDown = autoCompleteFps.text.toString().trim().toInt()
            val editClipLengthh = editClipLength.text.toString().trim().toInt()
            val txtEventDuration = ((myTimeHr.toInt() * 60) + myTimeMin.toInt())*60
            val noOfPhoto = editClipLengthh * fpsDropDown
            if(shootingInterval == "Shooting Interval"){
                noOfPhotos.text = "$noOfPhoto Photos"
                txtIntervalSelect.text = shootingInterval
                val interval = if(txtEventDuration / noOfPhoto == 0) "1" else "${txtEventDuration / noOfPhoto}"
                txtIntervalSelectCalculated.text = "$interval seconds"
            }
            if(shootingInterval == "Clip Length"){
                noOfPhotos.text = "$noOfPhoto Photos"
                txtIntervalSelect.text = shootingInterval
                val interval = if(txtEventDuration / editClipLengthh == 0) "1" else "${txtEventDuration / editClipLengthh}"
                txtIntervalSelectCalculated.text = "$interval seconds"
            }
            if(shootingInterval == "Event Duration"){
                noOfPhotos.text = "$noOfPhoto Photos"
                txtIntervalSelect.text = shootingInterval
                val eventDuration = (myTimeHr.toInt() * 60) + myTimeMin.toInt()
                val interval = (noOfPhoto * eventDuration)
                val sec: Int = interval % 60
                val min: Int = interval / 60 % 60
                val hours: Int = interval / 60 / 60
                txtIntervalSelectCalculated.text = "$hours Hr $min Min $sec Sec"
            }
        }else{
            val snackBar = Snackbar.make(framelayout, "Values not selected!", Snackbar.LENGTH_LONG)
            snackBar.setAction("Ok") {
                snackBar.dismiss()
            }
            snackBar.show()
        }
    }

    fun showPopup(title: Int, msg: Int, image:Int){
        val factory = LayoutInflater.from(this@MainActivity)
        val view = factory.inflate(R.layout.custom_alert_box, null)
        val gifView = view.findViewById<ImageView>(R.id.alertBoxImage)
        val imageViewT = DrawableImageViewTarget(gifView)
        Glide.with(this@MainActivity).load(image).into<Target<Drawable>>(imageViewT)
        val customDialog: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        customDialog.setTitle(title)
        customDialog.setMessage(msg)
        customDialog.setPositiveButton("Ok", null)
        customDialog.setView(view)
        customDialog.show()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}