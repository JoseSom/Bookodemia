package com.example.bookodemia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookodemia.extra.estaEnLinea
import com.example.bookodemia.extra.iniciarSesion
import com.example.bookodemia.extra.mensajeEmergente
import com.example.bookodemia.model.dataclass.Errors
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.til_main_contrasena
import kotlinx.android.synthetic.main.activity_registrate.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class Registrate : AppCompatActivity() {
    private val TAG = Registrate::class.qualifiedName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrate)

        val usuario = til_registrate_usuario.editText?.text?.trim().toString()
        val correo = til_registrate_correo.editText?.text?.trim().toString()
        val contrasena = til_registrate_contrasena.editText?.text?.trim().toString()
        val confirmacionContrasena = til_registrate_confirmacion_contrasena.editText?.text?.trim().toString()


        til_registrate_usuario.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editText: Editable?) {
                if (editText.toString().trim().isEmpty()) {
                    til_registrate_usuario.setError("El campo es requerido")
                } else {
                    til_registrate_usuario.setErrorEnabled(false)
                    til_registrate_usuario.setError("")
                }
            }
        })

        til_registrate_correo.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editText: Editable?) {
                if (editText.toString().trim().isEmpty()) {
                    til_registrate_correo.setError("El campo es requerido")
                } else {
                    til_registrate_correo.setErrorEnabled(false)
                    til_registrate_correo.setError("")
                }
            }
        })

        til_registrate_contrasena.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editText: Editable?) {
                if (editText.toString().trim().isEmpty()) {
                    til_registrate_contrasena.setError("El campo es requerido")
                } else {
                    til_registrate_contrasena.setErrorEnabled(false)
                    til_registrate_contrasena.setError("")
                }
            }
        })

        til_registrate_confirmacion_contrasena.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editText: Editable?) {
                if (editText.toString().trim().isEmpty()) {
                    til_registrate_confirmacion_contrasena.setError("El campo es requerido")
                } else {
                    til_registrate_confirmacion_contrasena.setErrorEnabled(false)
                    til_registrate_confirmacion_contrasena.setError("")
                }
            }
        })

        btn_registrate_crearcuenta.setOnClickListener {
            if(til_registrate_usuario.isErrorEnabled || til_registrate_correo.isErrorEnabled || til_registrate_contrasena.isErrorEnabled || til_registrate_confirmacion_contrasena.isErrorEnabled  ){
                Snackbar.make(it,"Por favor complete todos los campos", Snackbar.LENGTH_SHORT).show()
            }else{
                if (validarContrasena() && validarCorreo() && validarUsuario() && validarConfirmacionContrasena()){
                    realizarPeticion()
                }else{
                    Snackbar.make(it,"Verifique los datos capturados", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun realizarPeticion() {
        if (estaEnLinea(applicationContext)) {
            val json = JSONObject()
            json.put("name", til_registrate_usuario.editText?.text.toString())
            json.put("email", til_registrate_correo.editText?.text.toString())
            json.put("password", til_registrate_contrasena.editText?.text.toString())
            json.put("password_confirmation", til_registrate_confirmacion_contrasena.editText?.text.toString())
            json.put("device_name", "User's phone")

            val queue = Volley.newRequestQueue(applicationContext)
            val request = object : JsonObjectRequest(
                Request.Method.POST,
                getString(R.string.url_servidor) + getString(R.string.api_registro),
                json,
                { response ->
                    Log.d(TAG, response.toString())
                    val jsonObject = JSONObject(response.toString())
                    iniciarSesion(applicationContext, jsonObject)
                    val intent = Intent(this, Home::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                },
                { error ->
                    Log.e(TAG, error.toString())
                    val jsonObject = JSONObject(String(error.networkResponse.data, Charsets.UTF_8))
                    val errors = Json.decodeFromString<Errors>(jsonObject.toString())
                    for (error in errors.errors){
                        mensajeEmergente(this, error.detail)
                    }
                    Log.e(TAG, error.networkResponse.toString())
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
            mensajeEmergente(this, getString(R.string.error_internet))
        }
    }


    private fun validarUsuario(): Boolean {
        return if (til_registrate_usuario.editText?.text.toString().isEmpty()) {
            til_registrate_usuario.error = getString(R.string.error_input_isempty)
            false
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(til_registrate_usuario.editText?.text.toString())
                    .matches()
            ) {
                til_registrate_usuario.isErrorEnabled = false
                true
            } else {
                til_registrate_usuario.error = getString(R.string.error_email_noformat)
                false
            }
        }
    }

    private fun validarCorreo(): Boolean {
        return if (til_registrate_correo.editText?.text.toString().isEmpty()) {
            til_registrate_correo.error = getString(R.string.error_input_isempty)
            false
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(til_registrate_correo.editText?.text.toString())
                    .matches()
            ) {
                til_registrate_correo.isErrorEnabled = false
                true
            } else {
                til_registrate_correo.error = getString(R.string.error_email_noformat)
                false
            }
        }
    }

    private fun validarContrasena(): Boolean {
        return if (til_registrate_contrasena.editText?.text.toString().isEmpty()) {
            til_registrate_contrasena.error = getString(R.string.error_input_isempty)
            false
        } else {
            til_registrate_contrasena.isErrorEnabled = false
            true
        }
    }

    private fun validarConfirmacionContrasena(): Boolean {
        return if (til_registrate_confirmacion_contrasena.editText?.text.toString().isEmpty()) {
            til_registrate_confirmacion_contrasena.error = getString(R.string.error_input_isempty)
            false
        } else {
            til_registrate_confirmacion_contrasena.isErrorEnabled = false
            true
        }
    }
}