package com.example.bookodemia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookodemia.adapters.LibroAdapter
import com.example.bookodemia.model.DatosLibro
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_cardview_libro.*

class Home : AppCompatActivity() {

    val listLibros: MutableList<DatosLibro> = mutableListOf()
    var adapterLibro = LibroAdapter(listLibros)
    var parent_view: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        parent_view = findViewById(android.R.id.content)
        initRecyclerLibros()

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.firstFragment->return@setOnNavigationItemSelectedListener true
                R.id.secondFragment->startActivity(Intent(this, Detalles::class.java))
                R.id.thirtFragment->return@setOnNavigationItemSelectedListener true

            }
            true
        }
    }

    private fun initRecyclerLibros() {
        listLibros.add(
            DatosLibro(
                "",
                "Titulo",
                "By Autor",
                "Categoria"
            )
        )
        listLibros.add(
            DatosLibro(
                "",
                "Titulo",
                "By Autor",
                "Categoria"
            )
        )
        listLibros.add(
            DatosLibro(
                "",
                "Titulo",
                "By Autor",
                "Categoria"
            )
        )
        listLibros.add(
            DatosLibro(
                "",
                "Titulo",
                "By Autor",
                "Categoria"
            )
        )
        listLibros.add(
            DatosLibro(
                "",
                "Titulo",
                "By Autor",
                "Categoria"
            )
        )
        listLibros.add(
            DatosLibro(
                "",
                "Titulo",
                "By Autor",
                "Categoria"
            )
        )

        rv_home_libros.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_home_libros.setHasFixedSize(true)
        adapterLibro = LibroAdapter(listLibros)
        rv_home_libros.adapter = adapterLibro
    }

}