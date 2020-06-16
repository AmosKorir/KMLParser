package com.amoskorir.kmlparser

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amoskorir.kmlparser.kmlutils.models.PlaceMarker

class AdapterK(var context: Context, var placeMarker: List<PlaceMarker>) :
    RecyclerView.Adapter<AdapterK.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v=LayoutInflater.from(context).inflate(R.layout.row,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return placeMarker.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}