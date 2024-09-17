package com.example.examenfinal;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.examenfinal.Interfaz.CountryApi;
import com.example.examenfinal.Models.Country;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class UserDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ImageView imageViewUser, imageViewFlag;
    private TextView textViewName, textViewEmail, textViewAge, textViewPhone, textViewCell, textViewNationality, textViewId, textViewCountry;

    private double latitude = 0.0;
    private double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        imageViewUser = findViewById(R.id.imageViewUserDetails);
        imageViewFlag = findViewById(R.id.imageViewFlag);
        textViewName = findViewById(R.id.textViewNameDetails);
        textViewEmail = findViewById(R.id.textViewEmailDetails);
        textViewAge = findViewById(R.id.textViewAgeDetails);
        textViewPhone = findViewById(R.id.textViewPhoneDetails);
        textViewCell = findViewById(R.id.textViewCellDetails);
        textViewNationality = findViewById(R.id.textViewNationalityDetails);
        textViewId = findViewById(R.id.textViewIdDetails);
        textViewCountry = findViewById(R.id.textViewCountryDetails);

        // Recibir los datos del intent
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        int age = getIntent().getIntExtra("age", 0);
        String phone = getIntent().getStringExtra("phone");
        String cell = getIntent().getStringExtra("cell");
        String nationality = getIntent().getStringExtra("nationality");
        String idName = getIntent().getStringExtra("idName");
        String idValue = getIntent().getStringExtra("idValue");
        String country = getIntent().getStringExtra("country");
        String imageUrl = getIntent().getStringExtra("imageUrl");

        // Obtener la latitud y longitud (asume que se envían desde el intent)
        latitude = getIntent().getDoubleExtra("latitude", 0.0);
        longitude = getIntent().getDoubleExtra("longitude", 0.0);

        // Mostrar los datos del usuario
        textViewName.setText(name);
        textViewEmail.setText(email);
        textViewAge.setText("Age: " + age);
        textViewPhone.setText("Phone: " + phone);
        textViewCell.setText("Cell: " + cell);
        textViewNationality.setText("Nationality: " + nationality);
        textViewId.setText("ID: " + idName + " " + idValue);
        textViewCountry.setText("Country: " + country);

        // Cargar la imagen del usuario
        Glide.with(this)
                .load(imageUrl)
                .into(imageViewUser);

        // Hacer la llamada a la API para obtener la bandera del país
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CountryApi countryApi = retrofit.create(CountryApi.class);

        // Llamada a la API con el nombre del país
        Call<List<Country>> call = countryApi.getCountryInfo(country);
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Country countryInfo = response.body().get(0);

                    // Cargar la bandera usando Glide
                    Glide.with(UserDetailsActivity.this)
                            .load(countryInfo.flags.png)  // URL de la bandera
                            .into(imageViewFlag);
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                // Manejar errores en la llamada API
                t.printStackTrace();
            }
        });

        // Inicializar el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Verifica que la latitud y longitud sean válidas
        if (latitude != 0.0 && longitude != 0.0) {
            LatLng userLocation = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(userLocation).title("User Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12));
        } else {
            // Si no hay ubicación, se puede mostrar un mensaje o una ubicación predeterminada
            LatLng defaultLocation = new LatLng(0.0, 0.0);  // Ejemplo de ubicación predeterminada
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 2));
        }
    }
}
