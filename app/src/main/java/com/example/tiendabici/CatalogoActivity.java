package com.example.tiendabici;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CatalogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        // Configuración del VideoView
        VideoView videoView = findViewById(R.id.videoView);

        // Construir el URI del video
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video3);

        try {
            // Establecer el video en el VideoView
            videoView.setVideoURI(videoUri);

            // Añadir controles de reproducción
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            // Iniciar reproducción automática
            videoView.start();
        } catch (Exception e) {
            // Manejar errores
            Toast.makeText(this, "Error al reproducir el video: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
