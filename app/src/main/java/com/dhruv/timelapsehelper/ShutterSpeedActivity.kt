package com.dhruv.timelapsehelper

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.target.Target
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShutterSpeedActivity : AppCompatActivity() {
    lateinit var drawerlayoutshutter:DrawerLayout
    lateinit var toolbarShutter:Toolbar
    lateinit var navigationviewShutter:NavigationView
    lateinit var fullFrameLayout:TextInputLayout
    lateinit var editTextFullFrame:AutoCompleteTextView
    lateinit var focalLengthLayout:TextInputLayout
    lateinit var editFocalLength:TextInputEditText
    lateinit var equiFocalLengthLayout:TextInputLayout
    lateinit var editEquiFocalLength:TextInputEditText
    lateinit var apertureLayout:TextInputLayout
    lateinit var editAperture:AutoCompleteTextView
    lateinit var btnShutterCalculate:MaterialButton
    lateinit var shutterLayout:RelativeLayout
    lateinit var txtNpfSeconds:TextView
    lateinit var txt500Rule:TextView
    var pixelPitch = "4.0 µm"
    var cameraName = "Nikon Coolpix A900"
    //
    lateinit var imgFocalLength:ImageView
    lateinit var imgEquiFocalLength:ImageView
    lateinit var imgAperture:ImageView
    lateinit var imgSensorType:ImageView

    @SuppressLint("RtlHardcoded", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shutter_speed)

        drawerlayoutshutter=findViewById(R.id.drawerlayoutshutter)
        toolbarShutter=findViewById(R.id.toolbarShutter)
        navigationviewShutter=findViewById(R.id.navigationviewShutter)
        fullFrameLayout=findViewById(R.id.fullFrameLayout)
        editTextFullFrame=findViewById(R.id.editTextFullFrame)
        //
        focalLengthLayout=findViewById(R.id.focalLengthLayout)
        editFocalLength=findViewById(R.id.editFocalLength)
        //
        equiFocalLengthLayout=findViewById(R.id.equiFocalLengthLayout)
        editEquiFocalLength=findViewById(R.id.editEquiFocalLength)
        //
        apertureLayout=findViewById(R.id.apertureLayout)
        editAperture=findViewById(R.id.editAperture)
        //
        btnShutterCalculate=findViewById(R.id.btnShutterCalculate)
        //
        shutterLayout=findViewById(R.id.shutterLayout)
        //
        txtNpfSeconds=findViewById(R.id.txtNpfSeconds)
        txt500Rule=findViewById(R.id.txt500Rule)
        //
        imgFocalLength = findViewById(R.id.imgFocalLength)
        imgEquiFocalLength = findViewById(R.id.imgEquiFocalLength)
        imgAperture = findViewById(R.id.imgAperture)
        imgSensorType = findViewById(R.id.imgSensorType)


        toolbarShutter.setNavigationOnClickListener {
            drawerlayoutshutter.openDrawer(Gravity.LEFT)
        }

        navigationviewShutter.menu.findItem(R.id.shutterSpeed).isChecked = true

        val items = listOf("Full Frame x1", "APS-C x1.6", "Medium Format x0.5", "Micro 4/3rd x2")

        val adapter = ArrayAdapter(applicationContext, R.layout.list_item, items)
        (fullFrameLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        val items2 = listOf("f/1.2", "f/1.4", "f/1.8", "f/2.0", "f/2.2", "f/2.5", "f/2.8", "f/3.2", "f/3.5", "f/4.0", "f/5.6")

        val adapter2 = ArrayAdapter(applicationContext, R.layout.list_item, items2)
        (apertureLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter2)

        navigationviewShutter.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.dashboard -> {
                    drawerlayoutshutter.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.intervaltable -> {
                    drawerlayoutshutter.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,IntervalTableActivity::class.java)
                    startActivity(intent)
                }
                R.id.shutterSpeed -> {
                    drawerlayoutshutter.closeDrawer(Gravity.LEFT)
                }
                R.id.aboutapp -> {
                    drawerlayoutshutter.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,TipsAndSuggestionsActivity::class.java)
                    startActivity(intent)
                }
                R.id.newaboutapp -> {
                    drawerlayoutshutter.closeDrawer(Gravity.LEFT)
                    val intent = Intent(this,AboutApp::class.java)
                    startActivity(intent)
                }
            }

            return@setNavigationItemSelectedListener true
        }

        editFocalLength.doOnTextChanged { text, start, before, count ->
            if(editTextFullFrame.editableText.toString() == "Full Frame x1"){
                if(editFocalLength.editableText.toString().isNotBlank()){
                    editEquiFocalLength.setText("${editFocalLength.editableText.toString().toFloat() * 1}")
                }
            }
            if(editTextFullFrame.editableText.toString() == "APS-C x1.6"){
                if(editFocalLength.editableText.toString().isNotBlank()){
                    editEquiFocalLength.setText("%.2f".format(editFocalLength.editableText.toString().toFloat() * 1.6))
                }
            }
            if(editTextFullFrame.editableText.toString() == "Medium Format x0.5"){
                if(editFocalLength.editableText.toString().isNotBlank()){
                    editEquiFocalLength.setText("%.2f".format(editFocalLength.editableText.toString().toFloat() * 0.5))
                }
            }
            if(editTextFullFrame.editableText.toString() == "Micro 4/3rd x2"){
                if(editFocalLength.editableText.toString().isNotBlank()){
                    editEquiFocalLength.setText("${editFocalLength.editableText.toString().toFloat() * 2}")
                }
            }
        }

        editTextFullFrame.setOnItemClickListener { adapterView, view, i, l ->
            if(i.toString() == "0"){
                if(editFocalLength.editableText.toString().isNotBlank()){
                    editEquiFocalLength.setText("${editFocalLength.editableText.toString().toFloat() * 1}")
                }
            }
            if(i.toString()=="1"){
                if(editFocalLength.editableText.toString().isNotBlank()){
                    editEquiFocalLength.setText("%.2f".format(editFocalLength.editableText.toString().toFloat() * 1.6))
                }
            }
            if(i.toString()=="2"){
                if(editFocalLength.editableText.toString().isNotBlank()){
                    editEquiFocalLength.setText("%.2f".format(editFocalLength.editableText.toString().toFloat() * 0.5))
                }
            }
            if(i.toString()=="3"){
                if(editFocalLength.editableText.toString().isNotBlank()){
                    editEquiFocalLength.setText("${editFocalLength.editableText.toString().toFloat() * 2}")
                }
            }
        }

        btnShutterCalculate.setOnClickListener {
            if(editFocalLength.text!!.isNotBlank() && editEquiFocalLength.text!!.isNotBlank()){
                val equiFocalLen = editEquiFocalLength.text.toString().trim().toFloat()
                val aperture = editAperture.text.toString().trim().removeRange(0,2).toFloat()

                Log.v("aperture",aperture.toString())
                pixelPitch = pixelPitch.removeRange(3,pixelPitch.length)
                val npfValue = ((35 * aperture) + (30 * pixelPitch.toFloat())) / equiFocalLen
                val rule500 = 500 / equiFocalLen

                txtNpfSeconds.text = "%.1f".format(npfValue)
                txt500Rule.text = "%.1f".format(rule500)

            }else{
                val snackBar = Snackbar.make(shutterLayout,"Values not selected!", Snackbar.LENGTH_LONG)
                snackBar.setAction("Ok") {
                    snackBar.dismiss()
                }
                snackBar.show()
            }
        }

        imgFocalLength.setOnClickListener {
           showPopup(R.string.focal_length,R.string.focal_length_desc,R.drawable.focal_length)
        }
        imgEquiFocalLength.setOnClickListener {
            showPopup(R.string.equi_focal_length,R.string.equi_f_length_desc,R.drawable.eq_focal_length)
        }
        imgAperture.setOnClickListener {
            showPopup(R.string.aperture,R.string.aperture_desc,R.drawable.aper)
        }
        imgSensorType.setOnClickListener {
            showPopup(R.string.sensor,R.string.sensor_desc,R.drawable.sensor)
        }

    }

    fun showPopup(title: Int, msg: Int, image:Int){
        val factory = LayoutInflater.from(this@ShutterSpeedActivity)
        val view = factory.inflate(R.layout.custom_alert_box, null)
        val gifView = view.findViewById<ImageView>(R.id.alertBoxImage)
        val imageViewT = DrawableImageViewTarget(gifView)
        Glide.with(this@ShutterSpeedActivity).load(image).into<Target<Drawable>>(imageViewT)
        val customDialog: AlertDialog.Builder = AlertDialog.Builder(this@ShutterSpeedActivity)
        customDialog.setTitle(title)
        customDialog.setMessage(msg)
        customDialog.setPositiveButton("Ok", null)
        customDialog.setView(view)
        customDialog.show()
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