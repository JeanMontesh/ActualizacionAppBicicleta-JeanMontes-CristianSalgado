package com.example.tiendabici;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class TiendaCRUDActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText etNombre, etPrecio, etId;
    private Button btnInsertar, btnActualizar, btnEliminar;
    private Spinner spinnerTipoBici;
    private ListView listViewProductos;
    private ArrayList<String> productosLista;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda_crud);

        // Inicializando el objeto de base de datos
        databaseHelper = new DatabaseHelper(this);

        // Asociando los elementos con el XML
        etNombre = findViewById(R.id.input_nombre);
        etPrecio = findViewById(R.id.input_precio);
        etId = findViewById(R.id.input_id);
        btnInsertar = findViewById(R.id.btn_agregar);
        btnActualizar = findViewById(R.id.btn_modificar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        spinnerTipoBici = findViewById(R.id.spinner_tipo_bici);
        listViewProductos = findViewById(R.id.lista_bicicletas);

        // Inicializar el ArrayList para los productos
        productosLista = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productosLista);
        listViewProductos.setAdapter(adapter);

        // Llenar el spinner con los tipos de bicicleta
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.tipo_bicicleta, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoBici.setAdapter(spinnerAdapter);

        // Estableciendo los OnClickListeners para los botones
        btnInsertar.setOnClickListener(v -> insertarProducto());
        btnActualizar.setOnClickListener(v -> actualizarProducto());
        btnEliminar.setOnClickListener(v -> eliminarProducto());

        // Obtener los productos para mostrar en el ListView
        cargarProductos();
    }

    private void insertarProducto() {
        // Validar los campos antes de insertar
        if (etNombre.getText().toString().isEmpty() || etPrecio.getText().toString().isEmpty() ||
                spinnerTipoBici.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String nombre = etNombre.getText().toString();
        double precio = 0;
        try {
            precio = Double.parseDouble(etPrecio.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese un precio válido", Toast.LENGTH_SHORT).show();
            return;
        }
        String tipoBici = spinnerTipoBici.getSelectedItem().toString();
        long resultado = databaseHelper.insertarProducto(nombre, precio, tipoBici);

        if (resultado != -1) {
            Toast.makeText(this, "Producto insertado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            cargarProductos();  // Recargar los productos después de insertar
        } else {
            Toast.makeText(this, "Error al insertar producto", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarProducto() {
        // Validar que el ID no esté vacío
        if (etId.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un ID", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(etId.getText().toString());
        if (etNombre.getText().toString().isEmpty() || etPrecio.getText().toString().isEmpty() ||
                spinnerTipoBici.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String nombre = etNombre.getText().toString();
        double precio = 0;
        try {
            precio = Double.parseDouble(etPrecio.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese un precio válido", Toast.LENGTH_SHORT).show();
            return;
        }
        String tipoBici = spinnerTipoBici.getSelectedItem().toString();
        int resultado = databaseHelper.actualizarProducto(id, nombre, precio, tipoBici);

        if (resultado > 0) {
            Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            cargarProductos();  // Recargar los productos después de actualizar
        } else {
            Toast.makeText(this, "Error al actualizar producto", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarProducto() {
        // Validar que el ID no esté vacío
        if (etId.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un ID", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(etId.getText().toString());
        int resultado = databaseHelper.eliminarProducto(id);

        if (resultado > 0) {
            Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            cargarProductos();  // Recargar los productos después de eliminar
        } else {
            Toast.makeText(this, "Error al eliminar producto", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        etId.setText("");
        etNombre.setText("");
        etPrecio.setText("");
    }

    private void cargarProductos() {
        Cursor cursor = databaseHelper.obtenerProductos();
        productosLista.clear();  // Limpiar la lista antes de agregar los nuevos datos

        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(this, "No hay productos", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            String producto = "ID: " + cursor.getInt(0) +
                    " | Nombre: " + cursor.getString(1) +
                    " | Precio: " + cursor.getDouble(2) +
                    " | Tipo: " + cursor.getString(3);  // Mostrar tipo de bicicleta
            productosLista.add(producto);
        }
        adapter.notifyDataSetChanged();  // Actualizar el ListView
    }
}
