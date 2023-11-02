package com.example.sqlitecrud1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class daoContacto {
    private SQLiteDatabase bd;
    private Context ct;

    private static final String nombreBD = "BDContactos";
    private static final String tabla = "create table if not exists contacto(id integer primary key autoincrement, nombre text, apellido text, email text, telefono text, ciudad text)";

    public daoContacto(Context context) {
        this.ct = context;
        bd = context.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE, null);
        bd.execSQL(tabla);
    }

    public boolean insertar(Contacto c) {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", c.getNombre());
        contenedor.put("apellido", c.getApellido());
        contenedor.put("email", c.getEmail());
        contenedor.put("telefono", c.getTelefono());
        contenedor.put("ciudad", c.getCiudad());
        return bd.insert("contacto", null, contenedor) != -1;
    }

    public boolean eliminar(int id) {
        return bd.delete("contacto", "id=" + id, null) > 0;
    }

    public boolean editar(Contacto c) {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", c.getNombre());
        contenedor.put("apellido", c.getApellido());
        contenedor.put("email", c.getEmail());
        contenedor.put("telefono", c.getTelefono());
        contenedor.put("ciudad", c.getCiudad());
        return bd.update("contacto", contenedor, "id=" + c.getId(), null) > 0;
    }

    public ArrayList<Contacto> verTodo() {
        ArrayList<Contacto> lista = new ArrayList<>();
        Cursor cursor = bd.rawQuery("select * from contacto", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                lista.add(new Contacto(cursor.getInt(0),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),
                        cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        cursor.close(); // Cerrar el cursor cuando hayas terminado con él.
        return lista;
    }

    public Contacto verUno(int id) {
        Cursor cursor = bd.rawQuery("select * from contacto where id=" + id, null);
        if (cursor != null && cursor.moveToFirst()) {
            return new Contacto(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4),
                    cursor.getString(5));
        }
        return null;
    }
}
