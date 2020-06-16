package com.amoskorir.kmlparser


import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.amoskorir.kmlparser.kmlutils.KMLParser
import com.amoskorir.kmlparser.kmlutils.models.PlaceMarker
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val res: Resources = resources
        val ingm: InputStream = res.openRawResource(R.raw.a)
        val l=KMLParser().parse(ingm)
        showL(l)
    }

    private fun showL(l: List<PlaceMarker>) {
    recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=AdapterK(this,l)
    }



}