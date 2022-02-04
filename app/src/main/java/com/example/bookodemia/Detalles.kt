package com.example.bookodemia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.bookodemia.model.books.Book
import org.w3c.dom.Text

class Detalles : AppCompatActivity() {
    private val TAG = Detalles::class.qualifiedName
    lateinit var tv_detalles_titulo: TextView
    lateinit var tv_detalles_autor: TextView
    lateinit var tv_detalles_categoria: TextView
    lateinit var tv_detalles_cardview_2: TextView
    lateinit var tv_detalles_nombre_autor: TextView
    lateinit var tv_detalles_informacion_autor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)
        init()
        intent.extras?.let {book ->
            val book = book.getSerializable("book") as Book
            Log.d(TAG, book.attributes.title)
            tv_detalles_titulo.text = book.attributes.title

        }
    }

    fun init(){
         tv_detalles_titulo = findViewById(R.id.tv_detalles_titulo)
         tv_detalles_autor= findViewById(R.id.tv_detalles_autor)
         tv_detalles_categoria= findViewById(R.id.tv_detalles_categoria)
         tv_detalles_cardview_2= findViewById(R.id.tv_detalles_cardview_2)
         tv_detalles_nombre_autor= findViewById(R.id.tv_detalles_nombre_autor)
         tv_detalles_informacion_autor= findViewById(R.id.tv_detalles_informacion_autor)
    }
}