package com.example.bookodemia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
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
            if(til_main_contrasena.isErrorEnabled || til_main_correo.isErrorEnabled ){
                Snackbar.make(it,"Por favor complete todos los campos",Snackbar.LENGTH_SHORT).show()
            }else{
                if (til_main_correo.editText?.text?.trim().toString() == "example@gmail.com" && til_main_contrasena.editText?.text?.trim().toString() == "123456"){
                    Snackbar.make(it,"Ingresando a Home",Snackbar.LENGTH_SHORT).show()
                    startActivity(Intent(this, Home::class.java))
                }else{
                    Snackbar.make(it,"Correo o Contraseña incorrectos",Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        btn_main_registrate.setOnClickListener {
            startActivity(Intent(this, Registrate::class.java))
        }

    }
}