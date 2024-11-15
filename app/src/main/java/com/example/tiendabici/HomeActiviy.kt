package com.example.tiendabici

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnCerrarSesion: Button = findViewById(R.id.btnCerrarSesion)
        btnCerrarSesion.setOnClickListener {
            finish()  // Finaliza la actividad y cierra la sesión
        }

        val btnCrud: Button = findViewById(R.id.btnTiendaCRUD)
        btnCrud.setOnClickListener {
            val intent = Intent(this, TiendaCRUDActivity::class.java)
            startActivity(intent)  // Abre la actividad de CRUD de la tienda
        }

        val videoView: VideoView = findViewById(R.id.videoView)
        val videoUri: Uri = Uri.parse("android.resource://$packageName/raw/video")
        videoView.setVideoURI(videoUri)
        videoView.start()  // Reproduce el video en la vista de video

        val btnAbrirEnlace: Button = findViewById(R.id.btnAbrirEnlace)
        btnAbrirEnlace.setOnClickListener {
            val url = "https://flowco.cl/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)  // Abre el enlace en el navegador predeterminado
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

    // Sobrescribir el comportamiento del botón de retroceso
    override fun onBackPressed() {
        // No cerramos la actividad si venimos de un Intent con el navegador
        val intent = Intent(Intent.ACTION_MAIN)
        val currentActivity = this::class.java.simpleName

        // Si el Activity es el principal y el back es presionado, podemos cerrarlo
        if (currentActivity == "HomeActivity") {
            super.onBackPressed()  // Llamar al back normal si estamos en Home
        }
    }
}
