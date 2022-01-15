package com.dhruv.timelapsehelper.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhruv.timelapsehelper.R
import com.dhruv.timelapsehelper.ShutterSpeedActivity
import com.dhruv.timelapsehelper.models.CameraListModel
import com.google.android.material.card.MaterialCardView

class CameraListAdapter(val context:Context, val cameraList:ArrayList<CameraListModel>):RecyclerView.Adapter<CameraListAdapter.CameraListViewHolder>() {

    class CameraListViewHolder(val view: View):RecyclerView.ViewHolder(view){

        val camera:TextView = view.findViewById(R.id.txtCamera)
        val sensorSize:TextView = view.findViewById(R.id.sensorSize)
        val resolution:TextView = view.findViewById(R.id.resolution)
        val pixelPitch:TextView = view.findViewById(R.id.pixelPitch)
        val listCardView:MaterialCardView = view.findViewById(R.id.listCardView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CameraListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_camera_list_item,
            parent,
            false
        )
        return CameraListViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CameraListViewHolder, position: Int) {
        val cList = cameraList[position]
        holder.camera.text = "${cList.camera} ${cList.year}"
        holder.sensorSize.text = "Sensor: ${cList.sensorSize},"
        holder.resolution.text = "Reso: ${cList.resolution},"
        holder.pixelPitch.text = "Pxl Pi: ${cList.pixelPitch}"
        holder.listCardView.setOnClickListener {
            val intent = Intent(context,ShutterSpeedActivity::class.java)
            intent.putExtra("pixelPitch",cList.pixelPitch)
            intent.putExtra("cameraName",cList.camera)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return cameraList.size
    }
}