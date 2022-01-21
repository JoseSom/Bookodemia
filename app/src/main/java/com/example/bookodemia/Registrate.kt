package com.example.bookodemia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.til_main_contrasena
import kotlinx.android.synthetic.main.activity_registrate.*

class Registrate : AppCompatActivity() {
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
                if ((usuario.isNullOrBlank() || usuario.isNullOrEmpty()) ||
                    (correo.isNullOrBlank() || correo.isNullOrEmpty()) ||
                    (contrasena.isNullOrBlank() || contrasena.isNullOrEmpty()) ||
                    (confirmacionContrasena.isNullOrBlank() || confirmacionContrasena.isNullOrEmpty()) &&
                    (contrasena == confirmacionContrasena)){
                    Snackbar.make(it,"Usuario registrado correctamente", Snackbar.LENGTH_SHORT).show()
                }else{
                    Snackbar.make(it,"Verifique los datos capturados", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}