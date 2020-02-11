package com.example.u2t1_androidverano;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientesUpdate extends AppCompatActivity {
    private EditText nombre;
    private EditText apellido;
    private EditText sexo;
    private EditText telefono;
    private EditText direccion;
    HttpURLConnection conexion;
    //private int isexo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_de_clientes);

        nombre = (EditText) findViewById(R.id.nombre);
        direccion = (EditText) findViewById(R.id.direccion);
        telefono = (EditText) findViewById(R.id.telefono);
        apellido = (EditText) findViewById(R.id.apellido);
       // sexo = (EditText) findViewById(R.id.sexo);
        // isexo=(sexo.getSelectedItem().toString().equals("Masculino"))?0:1;
    }

    public void Editar(View view) {
        try {
            JSONObject postData = new JSONObject();
            postData.put("nombre", nombre.getText().toString());
            postData.put("apellido", apellido.getText().toString());
            postData.put("sexo", sexo.getText().toString());
            postData.put("telefono", telefono.getText().toString());
            postData.put("direccion", direccion.getText().toString());
            String myurl= this.getString(R.string.dominio)+this.getString(R.string.actualizarclientepost);
            Log.i("respuesta",myurl);
            URL url = new URL(myurl);
            conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);
            conexion.setDoInput(true);
            conexion.setChunkedStreamingMode(0);
            OutputStream out = new BufferedOutputStream(conexion.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    out, "UTF-8"));
            writer.write(postData.toString());
            writer.flush();
            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                if (!linea.equals("OK\\n")) Log.e("mierror","Error en servicio Web");
                else
                { Toast.makeText(this, "Actualización Correctamente realizada", Toast.LENGTH_SHORT).show();
                    finish();
                    Log.e("mierror","No hay error");}
            } else {Log.e("mierror", conexion.getResponseMessage());}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conexion != null) {
                conexion.disconnect();
            }
        }
    }
}

