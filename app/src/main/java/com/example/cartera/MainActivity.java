package com.example.cartera;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.AdapterView.OnItemLongClickListener;

import com.example.cartera.BaseDatos.FeedReaderContract.*;
import com.example.cartera.BaseDatos.FeedReaderDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.BaseColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;

    private ListView lstDatos;
    private SearchView searchView;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> clientes;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        FloatingActionButton fab =  (FloatingActionButton) findViewById(R.id.fab1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this,ActNuevoCliente.class);
                startActivityForResult(it,0);
            }
        });

        mostrar();

        lstDatos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int i, long id) {
                final int posicion=i;

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MainActivity.this);
                dialogo1.setTitle("Alerta");
                dialogo1.setMessage("¿ Desea eliminar a este cliente ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogo1, int id) {
                        String text = (String) lstDatos.getItemAtPosition(posicion);
                        text = text.split(" ")[0];
                        removeItem(text);
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();

                return false;
            }
        });
    }

    private void mostrar(){
        lstDatos = findViewById(R.id.lstDatos);
        clientes = new ArrayList<String>();
        searchView =  findViewById(R.id.searchView1);

        try {
            String[] projection = {
                    BaseColumns._ID,
                    FeedEntry.COLUMN_NOMBRE,
                    FeedEntry.COLUMN_TELEFONO
            };

            String selection = FeedEntry.COLUMN_NOMBRE + " = ?";
            String[] selectionArgs = {"Nombre" };

            String sortOrder =
                    FeedEntry.COLUMN_TELEFONO + " Telefono";

            Cursor cursor = mDatabase.query(
                  FeedEntry.TABLE_NAME,
                  projection,
                  null,
                  null,
                  null,
                  null,
                  null
            );

            String sNombre;
            String sTelefono;
            Long id;

            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    sNombre = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NOMBRE));
                    sTelefono = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_TELEFONO));
                    id = cursor.getLong(cursor.getColumnIndex(FeedEntry._ID));
                    clientes.add(id + " : " + sNombre + " : " + sTelefono);
                }while (cursor.moveToNext());
            }

            adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,clientes);
            lstDatos.setAdapter(adaptador);
            buscar();

        }catch (Exception ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("Ok",null);
            dlg.show();
        }
    }

    private void removeItem(String id){
        mDatabase.delete(FeedEntry.TABLE_NAME,
                FeedEntry._ID + "=" + id, null);
        mostrar();
    }

    private void buscar(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                MainActivity.this.adaptador.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MainActivity.this.adaptador.getFilter().filter(newText);
                return false;
            }
        });


        /*
        lstDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,"You Click" + adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
            }
        });*/



        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(clientes.contains(query)){
                    adaptador.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });*/
    }


    @Override
    protected void onActivityResult(int requesCode, int resultCode, @Nullable Intent data) {
        mostrar();
        super.onActivityResult(requesCode, resultCode, data);
    }
}