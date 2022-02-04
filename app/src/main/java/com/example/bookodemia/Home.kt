package com.example.bookodemia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.bookodemia.adapters.LibroAdapter
import com.example.bookodemia.extra.*
import com.example.bookodemia.model.books.Book
import com.example.bookodemia.model.books.Books
import com.example.bookodemia.model.dataclass.Errors
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_cardview_libro.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class Home : AppCompatActivity() {
    private val TAG = Home::class.qualifiedName

    private lateinit var rv_home_libros: RecyclerView
    private lateinit var pb_home_rv_books: ProgressBar
    private var parent_view: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        parent_view = findViewById(android.R.id.content)
        rv_home_libros = findViewById(R.id.rv_home_libros)
        pb_home_rv_books = findViewById(R.id.pb_home_rv_books)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.firstFragment -> return@setOnNavigationItemSelectedListener true
                R.id.secondFragment -> {
                    val queue = Volley.newRequestQueue(applicationContext)
                    val request = object : StringRequest(Request.Method.POST,
                        getString(R.string.url_servidor) + getString(R.string.api_logout),
                        { response ->
                            Log.d(TAG, response.toString())
                            eliminarSesion(applicationContext)
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        },
                        { error ->
                            Log.e(TAG, "Entro en error")
                            Log.e(TAG, error.toString())
                        }) {
                        override fun getHeaders(): MutableMap<String, String> {
                            val headers = HashMap<String, String>()
                            headers["Authorization"] =
                                "Bearer ${obtenerDeSesion(applicationContext, "token")}"
                            return headers
                        }
                    }
                    queue.add(request)
                }
                R.id.thirtFragment -> return@setOnNavigationItemSelectedListener true
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        if (estaEnLinea(applicationContext)) {
            realizarPeticion()
        } else {
            mensajeEmergente(this, getString(R.string.error_internet))
        }
    }

    fun realizarPeticion() {
        val queue = Volley.newRequestQueue(applicationContext)
        val request = object : JsonObjectRequest(Request.Method.GET,
            getString(R.string.url_servidor) + getString(R.string.api_libros),
            null,
            { response ->
                Log.d(TAG,response.toString())
                rv_home_libros.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                rv_home_libros.setHasFixedSize(true)
                val books = Json.decodeFromString<Books>(response.toString())
                val adapter = LibroAdapter(books.data, this)
                rv_home_libros.adapter = adapter
                adapter.notifyDataSetChanged()
                pb_home_rv_books.visibility = View.GONE
                rv_home_libros.visibility = View.VISIBLE
            },
            { error ->
                Log.e(TAG, error.toString())
                if(error.networkResponse.statusCode == 401){
                    eliminarSesion(applicationContext)
                    val intent = Intent(this, MainActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                }else{
                    pb_home_rv_books.visibility = View.VISIBLE
                    rv_home_libros.visibility = View.GONE
                    val jsonObject = JSONObject(String(error.networkResponse.data, Charsets.UTF_8))
                    val errors = Json.decodeFromString<Errors>(jsonObject.toString())
                    for (error in errors.errors){
                        mensajeEmergente(this, error.detail)
                    }
                    Log.e(TAG, error.networkResponse.toString())
                }
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] =
                    "Bearer ${obtenerDeSesion(applicationContext, "token")}"
                headers["Accept"] = "application/json"
                headers["Content-type"] = "application/json"
                return headers
            }
        }
        queue.add(request)
    }

}