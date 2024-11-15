package com.example.tiendabici;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tienda.db";
    private static final int DATABASE_VERSION = 2;  // Incrementado el número de versión

    private static final String TABLE_PRODUCTOS = "productos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_PRECIO = "precio";
    private static final String COLUMN_TIPO = "tipo";  // Nueva columna para el tipo

    private static final String CREATE_TABLE_PRODUCTOS =
            "CREATE TABLE " + TABLE_PRODUCTOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE + " TEXT NOT NULL, " +
                    COLUMN_PRECIO + " REAL NOT NULL, " +
                    COLUMN_TIPO + " TEXT NOT NULL);";  // Definido tipo como texto

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_PRODUCTOS);
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error al crear la tabla", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);
        onCreate(db);
    }

    public long insertarProducto(String nombre, double precio, String tipo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_PRECIO, precio);
        values.put(COLUMN_TIPO, tipo);  // Agregar tipo de bicicleta

        return db.insert(TABLE_PRODUCTOS, null, values);
    }

    public int actualizarProducto(int id, String nombre, double precio, String tipo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_PRECIO, precio);
        values.put(COLUMN_TIPO, tipo);

        return db.update(TABLE_PRODUCTOS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int eliminarProducto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PRODUCTOS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Cursor obtenerProductos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS, null);
    }
}
