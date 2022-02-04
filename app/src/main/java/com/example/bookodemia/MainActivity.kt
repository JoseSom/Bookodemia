package com.example.bookodemia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookodemia.extra.*
import com.example.bookodemia.model.dataclass.Errors
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.qualifiedName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        til_main_correo.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editText: Editable?) {
                if (editText.toString().trim().isEmpty()) {
                    til_main_correo.setError("El campo es requerido")
                } else {
                    til_main_correo.setErrorEnabled(false)
                    til_main_correo.setError("")
                }
            }
        })

        til_main_contrasena.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editText: Editable?) {
                if (editText.toString().trim().isEmpty()) {
                    til_main_contrasena.setError("El campo es requerido")
                } else {
                    til_main_contrasena.setErrorEnabled(false)
                    til_main_contrasena.setError("")
                }
            }
        })

        btn_main_login.setOnClickListener {
            if (til_main_contrasena.isErrorEnabled || til_main_correo.isErrorEnabled) {
                Snackbar.make(it, "Por favor complete todos los campos", Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                if (validarCorreo() && validarContrasena()) {
                    realizarPeticion()
                }
            }
        }

        btn_main_registrate.setOnClickListener {
            startActivity(Intent(this, Registrate::class.java))
            finish()
        }

    }

    private fun lanzarActivity() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }

    fun realizarPeticion() {
        if (estaEnLinea(applicationContext)) {
            btn_main_login.visibility = View.GONE
            pb_main.visibility = View.VISIBLE
            val queue = Volley.newRequestQueue(applicationContext)
            val json = JSONObject()
            json.put("email", til_main_correo.editText?.text.toString())
            json.put("password", til_main_contrasena.editText?.text.toString())
            json.put("device_name", "User's phone")

            val request = object : JsonObjectRequest(Request.Method.POST,
                getString(R.string.url_servidor) + getString(R.string.api_login),
                json,
                { response ->
                    Log.d(TAG, response.toString())
                    val jsonObject = JSONObject(response.toString())
                    iniciarSesion(applicationContext, jsonObject)
                    if (validarSesion(applicationContext)) {
                        lanzarActivity()
                    }
                },
                { error ->
                    btn_main_login.visibility = View.VISIBLE
                    pb_main.visibility = View.GONE
                    val jsonObject = JSONObject(String(error.networkResponse.data, Charsets.UTF_8))
                    val errors = Json.decodeFromString<Errors>(jsonObject.toString())
                    for (error in errors.errors) {
                        mensajeEmergente(this, error.detail)
                    }
                    Log.e(TAG, error.networkResponse.toString())
                    Log.e(TAG, error.toString())
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    headers["Content-type"] = "application/json"
                    return headers
                }
            }
            queue.add(request)
        } else {
            mensajeEmergente(activity = this, mensaje = getString(R.string.error_internet))
        }
    }

    private fun validarCorreo(): Boolean {
        return if (til_main_correo.editText.toString().isEmpty()) {
            til_main_correo.error = getString(R.string.error_input_isempty)
            false
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(til_main_correo.editText?.text.toString())
                    .matches()
            ) {
                til_main_correo.isErrorEnabled = false
                true
            } else {
                til_main_correo.error = getString(R.string.error_email_noformat)
                false
            }
        }
    }

    private fun validarContrasena(): Boolean {
        return if (til_main_contrasena.editText?.text.toString().isEmpty()) {
            til_main_contrasena.error = getString(R.string.error_input_isempty)
            false
        } else {
            til_main_contrasena.isErrorEnabled = false
            true
        }
    }
}