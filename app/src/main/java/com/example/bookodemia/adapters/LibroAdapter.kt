package com.example.bookodemia.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bookodemia.Detalles
import com.example.bookodemia.Home
import com.example.bookodemia.R
import com.example.bookodemia.Registrate
import com.example.bookodemia.model.DatosLibro
import com.google.android.material.card.MaterialCardView

class LibroAdapter(val libro: MutableList<DatosLibro>): RecyclerView.Adapter<LibroAdapter.LibroHolder>() {

    class LibroHolder(val view: View): RecyclerView.ViewHolder(view){
        val imageViewKoder: ImageView = view.findViewById(R.id.iv_card_libro)


        fun render(libro: DatosLibro){
            // Carga de imagenes
            Glide.with(view).load(libro.image).error(R.drawable.libro_1).diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageViewKoder)
        }

    }

    fun insertarLibro(datosLibro: DatosLibro) {
        this.libro.add(datosLibro)
        notifyItemInserted(itemCount)
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