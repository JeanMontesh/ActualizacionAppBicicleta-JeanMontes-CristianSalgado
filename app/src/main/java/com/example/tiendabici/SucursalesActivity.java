package com.example.tiendabici;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class SucursalesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursales);

        // Carga la configuración del mapa usando las preferencias predeterminadas.
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        // Obtiene la referencia al componente MapView del layout.
        MapView mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);  // Establece la fuente de azulejos
        mapView.setBuiltInZoomControls(true);  // Activa los controles de zoom
        mapView.setMultiTouchControls(true);  // Habilita el control multitáctil

        // Coordenadas del IP Santo Tomás, Chile.
        double ipSantoTomasLatitud = -33.4270058; // Latitud
        double ipSantoTomasLongitud = -70.610773; // Longitud

        // Crear objetos GeoPoint para las coordenadas definidas.
        GeoPoint IPsantoTomasPoint = new GeoPoint(ipSantoTomasLatitud, ipSantoTomasLongitud);
        // Configura la vista inicial del mapa centrada en el IP Santo Tomas con un nivel de zoom de 15.
        mapView.getController().setZoom(15.0);
        // Centra el mapa en el punto de Santiago.
        mapView.getController().setCenter(IPsantoTomasPoint);

        // Crear un marcador para el IP Santo Tomás. y Creamos un marcador en el mapa
        Marker marcadorSantoTomas = new Marker(mapView);
        // Establece la posición del marcador en el punto de IP Santo Tomás.
        marcadorSantoTomas.setPosition(IPsantoTomasPoint);
        // Establece el ancla del marcador.
        marcadorSantoTomas.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        // Establece el título del marcador.
        marcadorSantoTomas.setTitle("Sucursal Providencia");
        // Establece una descripción para el marcador.
        marcadorSantoTomas.setSnippet("av. providencia 1605");

        // Agregar los marcadores al mapa.
        mapView.getOverlays().add(marcadorSantoTomas);



        // Coordenadas de ejemplo para una sucursal
        double sucursalLatitud = -33.4413817;
        double sucursalLongitud = -70.6474561;

        // Crear objetos GeoPoint para las coordenadas de la sucursal
        GeoPoint sucursalPoint = new GeoPoint(sucursalLatitud, sucursalLongitud);

        // Configurar la vista inicial del mapa y establecer el centro
        mapView.getController().setZoom(15.0);
        mapView.getController().setCenter(sucursalPoint);

        // Crear un marcador para la sucursal
        Marker marcadorSucursal = new Marker(mapView);
        marcadorSucursal.setPosition(sucursalPoint);
        marcadorSucursal.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marcadorSucursal.setTitle("Sucursal Agustinas");
        marcadorSucursal.setSnippet("Agustinas 8501");

        // Agregar el marcador al mapa
        mapView.getOverlays().add(marcadorSucursal);

        // Configurar el Spinner para cambiar el tipo de mapa
        Spinner mapTypeSpinner = findViewById(R.id.mapTypeSpinner);
        String[] mapTypes = {"Mapa Normal", "Mapa de Transporte", "Mapa Topografico"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mapTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapTypeSpinner.setAdapter(adapter);

        // Listener para detectar cambios en la selección del Spinner.
        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {   switch (position) {
                case 0:
                    mapView.setTileSource(TileSourceFactory.MAPNIK);
                    break;
                case 1:
                    mapView.setTileSource(new XYTileSource(
                            "PublicTransport",
                            0, 18, 256, ".png", new String[]{
                            "https://tile.memomaps.de/tilegen/"}));
                    break;
                case 2:
                    mapView.setTileSource(new XYTileSource(
                            "USGS_Satellite", 0, 18, 256, ".png", new String[]{
                            "https://a.tile.opentopomap.org/",
                            "https://b.tile.opentopomap.org/",
                            "https://c.tile.opentopomap.org/"}));
                    break;
            }
            }

            // No se hace nada cuando no se selecciona ningún elemento.
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
