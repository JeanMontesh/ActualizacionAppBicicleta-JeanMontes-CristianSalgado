package com.example.tiendabici

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Botón para cerrar sesión
        val btnCerrarSesion: Button = findViewById(R.id.btnCerrarSesion)
        btnCerrarSesion.setOnClickListener {
            finish()  // Finaliza la actividad y cierra la sesión
        }

        // Botón para acceder a la actividad de CRUD de la tienda
        val btnCrud: Button = findViewById(R.id.btnTiendaCRUD)
        btnCrud.setOnClickListener {
            val intent = Intent(this, TiendaCRUDActivity::class.java)
            startActivity(intent)  // Abre la actividad de CRUD de la tienda
        }

        // Botón para abrir un enlace en el navegador
        val btnAbrirEnlace: Button = findViewById(R.id.btnAbrirEnlace)
        btnAbrirEnlace.setOnClickListener {
            val url = "https://flowco.cl/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)  // Abre el enlace en el navegador predeterminado
        }

        // Botón para ver las sucursales y abrir la actividad SucursalesActivity
        val btnVerSucursales: Button = findViewById(R.id.btnVerSucursales)
        btnVerSucursales.setOnClickListener {
            val intent = Intent(this, SucursalesActivity::class.java)
            startActivity(intent)  // Abre la actividad de Sucursales
        }

        // Botón para ir al catálogo de productos (nueva adición)
        val btnVerCatalogo: Button = findViewById(R.id.btnVerCatalogo)
        btnVerCatalogo.setOnClickListener {
            val intent = Intent(this, CatalogoActivity::class.java)
            startActivity(intent)  // Abre la actividad del catálogo
        }


        // Inicializa el MediaPlayer para reproducir un sonido
        mediaPlayer = MediaPlayer.create(this, R.raw.sonido3)
        mediaPlayer.start()

        // Liberar el MediaPlayer cuando haya terminado de reproducir
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()  // Pausa la música al poner la actividad en segundo plano
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()  // Reanuda la música cuando la actividad regresa al primer plano
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()  // Libera recursos del MediaPlayer cuando la actividad se destruye
    }
}
