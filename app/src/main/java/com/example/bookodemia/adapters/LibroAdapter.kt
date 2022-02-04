package com.example.bookodemia.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bookodemia.Detalles
import com.example.bookodemia.MainActivity
import com.example.bookodemia.R
import com.example.bookodemia.extra.eliminarSesion
import com.example.bookodemia.extra.mensajeEmergente
import com.example.bookodemia.extra.obtenerDeSesion
import com.example.bookodemia.model.books.Book
import com.example.bookodemia.model.books.Books
import com.example.bookodemia.model.dataclass.Errors
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class LibroAdapter(val libro: MutableList<Book>, val activity: Activity): RecyclerView.Adapter<LibroAdapter.LibroHolder>() {

    class LibroHolder(val view: View): RecyclerView.ViewHolder(view){
        val imageViewLibro: ImageView = view.findViewById(R.id.iv_card_libro)
        val buttonViewLibro: FloatingActionButton = view.findViewById(R.id.fab_card_detalles)
        val tv_card_titulo: TextView = view.findViewById(R.id.tv_card_titulo)
        val tv_card_autor: TextView = view.findViewById(R.id.tv_card_autor)
        val tv_card_categoria: TextView = view.findViewById(R.id.tv_card_categoria)

        fun render(libro: Book){
            tv_card_titulo.text = libro.attributes.title
            // Carga de imagenes
            Glide.with(view).load(libro.image).error(R.drawable.libro_1).diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageViewLibro)
            //Accion en el boton
            buttonViewLibro.setOnClickListener {
                mostrarDetalleLibro(view.context,libro)
            }
        }

        fun mostrarDetalleLibro(context: Context, libro: Book) {
            val bundle = Bundle()
            bundle.putSerializable("book", libro)
            val intent = Intent(context.applicationContext,Detalles::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LibroHolder(layoutInflater.inflate(R.layout.item_cardview_libro, parent, false))
    }

    override fun onBindViewHolder(holder: LibroHolder, position: Int) {
        holder.render(libro[position])
    }

    override fun getItemCount(): Int = libro.size
}