package com.example.cartera;

import android.content.ContentValues;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;

import com.example.cartera.BaseDatos.FeedReaderContract;
import com.example.cartera.BaseDatos.FeedReaderDbHelper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class ActNuevoCliente extends AppCompatActivity {
    private SQLiteDatabase mDatabase;

    private EditText mEditTextNombre;
    private  EditText mEditTextDireccion;
    private  EditText mEditTextEmail;
    private  EditText mEditTextTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_nuevo_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        mEditTextNombre = findViewById(R.id.edtNombre);
        mEditTextDireccion = findViewById(R.id.edtDireccion);
        mEditTextEmail = findViewById(R.id.edtEmail);
        mEditTextTelefono = findViewById(R.id.edtTelefono);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_nuevo_cliente,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.action_ok:
                if (bCamposCorrectos()){
                    try {
                        String name = mEditTextNombre.getText().toString();
                        String direccion = mEditTextDireccion.getText().toString();
                        String email = mEditTextEmail.getText().toString();
                        String telefono = mEditTextTelefono.getText().toString();

                        ContentValues cv = new ContentValues();

                        cv.put(FeedReaderContract.FeedEntry.COLUMN_NOMBRE,name);
                        cv.put(FeedReaderContract.FeedEntry.COLUMN_DIRECCION,direccion);
                        cv.put(FeedReaderContract.FeedEntry.COLUMN_EMAIL,email);
                        cv.put(FeedReaderContract.FeedEntry.COLUMN_TELEFONO,telefono);

                        mDatabase.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, cv);
                        mDatabase.close();
                        finish();
                    }catch (Exception ex){
                        AlertDialog.Builder dlg=new AlertDialog.Builder(this);
                        dlg.setTitle("Aviso");
                        dlg.setMessage(ex.getMessage());
                        dlg.setNeutralButton("OK",null);
                        dlg.show();
                    }
                }
                else {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                    dlg.setTitle("Existen campos vacios");
                    dlg.setNeutralButton("OK",null);
                    dlg.show();
                }

                break;
            case R.id.action_cancelar:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean bCamposCorrectos(){
        boolean res=true;
        if (mEditTextNombre.getText().toString().trim().isEmpty()){
            mEditTextNombre.requestFocus();
            res=false;
        }
        if (mEditTextDireccion.getText().toString().trim().isEmpty()){
            mEditTextDireccion.requestFocus();
            res=false;
        }
        if (mEditTextEmail.getText().toString().trim().isEmpty()){
            mEditTextEmail.requestFocus();
            res=false;
        }
        if (mEditTextTelefono.getText().toString().trim().isEmpty()){
            mEditTextTelefono.requestFocus();
            res=false;
        }
        return res;
    }
}