package com.example.sqlitecrud1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    ArrayList<Contacto> lista;
    daoContacto dao;
    Contacto c;
    Activity a;

    int id = 0;

    public Adaptador(Activity a, ArrayList<Contacto> lista, daoContacto dao) {
        this.lista = lista;
        this.a = a;
        this.dao = dao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        c = lista.get(i);
        return c;
    }

    @Override
    public long getItemId(int i) {
        c = lista.get(i);
        return c.getId();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            LayoutInflater li = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item, null);
        }
        c = lista.get(posicion);
        TextView nombre = v.findViewById(R.id.nombre);
        TextView apellido = v.findViewById(R.id.apellido);
        TextView email = v.findViewById(R.id.email);
        TextView telefono = v.findViewById(R.id.telefono);
        TextView ciudad = v.findViewById(R.id.ciudad);
        Button editar = v.findViewById(R.id.btn_editar);
        Button eliminar = v.findViewById(R.id.btn_eliminar);
        nombre.setText(c.getNombre());
        apellido.setText(c.getApellido());
        email.setText(c.getEmail());
        telefono.setText(c.getTelefono());
        ciudad.setText(c.getCiudad());

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = posicion;
                final Dialog dialog = new Dialog(a);
                dialog.setTitle("Editar Registro");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo);
                dialog.show();
                final EditText nombreEditText = dialog.findViewById(R.id.et_nombre);
                final EditText apellidoEditText = dialog.findViewById(R.id.et_apellido);
                final EditText emailEditText = dialog.findViewById(R.id.et_email);
                final EditText telefonoEditText = dialog.findViewById(R.id.et_telefono);
                final EditText ciudadEditText = dialog.findViewById(R.id.et_ciudad);
                Button guardar = dialog.findViewById(R.id.btn_agregar);
                Button cancelar = dialog.findViewById(R.id.btn_cancelar);
                c = lista.get(pos);
                setId(c.getId());
                nombreEditText.setText(c.getNombre());
                apellidoEditText.setText(c.getApellido());
                emailEditText.setText(c.getEmail());
                telefonoEditText.setText(c.getTelefono());
                ciudadEditText.setText(c.getCiudad());

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            c = new Contacto(getId(), nombreEditText.getText().toString(),
                                    apellidoEditText.getText().toString(),
                                    emailEditText.getText().toString(),
                                    telefonoEditText.getText().toString(),
                                    ciudadEditText.getText().toString());
                            dao.editar(c);
                            lista = dao.verTodo();
                            notifyDataSetChanged();
                            dialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(a, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = posicion;
                c = lista.get(pos);
                setId(c.getId());
                AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage("¿Estás seguro de eliminar?");
                del.setCancelable(false);
                del.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dao.eliminar(getId());
                        lista = dao.verTodo();
                        notifyDataSetChanged();
                    }
                });
                del.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                del.show();
            }
        });
        return v;
    }
}







