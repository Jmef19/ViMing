package com.example.tfc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    String url = "http://www.omdbapi.com/?t=${input}&apikey=${your_key}";

    Button btn;
    ImageView image;
    EditText nombrePelicula;
    TextView plot, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombrePelicula = findViewById(R.id.userinput);
        image = findViewById(R.id.image);
        plot = findViewById(R.id.plot);
        name = findViewById(R.id.name);
        btn = findViewById(R.id.search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String nombre = nombrePelicula.getText().toString();
                    Toast.makeText(getApplicationContext(), "PULSADO: " + nombre, Toast.LENGTH_SHORT).show();
                    cogerDatos(nombre);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void cogerDatos(String nombre) throws IOException {

        URL testURL = new URL("http://www.omdbapi.com/?t=" + nombre + "&apikey=e71596b0");

        URLConnection connection = testURL.openConnection();

        Toast.makeText(getApplicationContext(), "CONEXION", Toast.LENGTH_SHORT).show();

        Toast.makeText(getApplicationContext(), "BIEN", Toast.LENGTH_SHORT).show();
        // Success
        // Further processing here
        InputStream responseBody = connection.getInputStream();
        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
        JsonReader jsonReader = new JsonReader(responseBodyReader);
        jsonReader.beginObject(); // Start processing the JSON object
        while (jsonReader.hasNext()) { // Loop through all keys
            String key = jsonReader.nextName(); // Fetch the next key
            Toast.makeText(getApplicationContext(), "key:" + key, Toast.LENGTH_SHORT).show();
            if (key.equals("Title")) { // Check if desired key
                // Fetch the value as a String
                String value = jsonReader.nextString();
                name.setText(value);
            }
            if (key.equals("Plot")) { // Check if desired key
                // Fetch the value as a String
                String value = jsonReader.nextString();
                plot.setText(value);
            }
            if (key.equals("Poster")) { // Check if desired key
                // Fetch the value as a String
                String value = jsonReader.nextString();
                Glide.with(this).load(value).into(image);
            } else {
                jsonReader.skipValue(); // Skip values of other keys
            }
        }


    }
}