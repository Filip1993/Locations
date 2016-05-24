package com.filipkesteli.locations;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnGPS;
    private Button btnNetwork;
    private TextView tvReport;

    private LocationManager locationManager;
    private boolean locationFetched;

    private static final long MIN_TIME = 0;
    private static final float MIN_DISTANCE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        setupListeners();

        //postavi trenutnu lokaciju:
        initLocationManager();
    }

    private void initLocationManager() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    private void initWidgets() {
        btnGPS = (Button) findViewById(R.id.btnGPS);
        btnNetwork = (Button) findViewById(R.id.btnNetwork);
        tvReport = (TextView) findViewById(R.id.tvReport);
    }

    private void setupListeners() {
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locate(LocationManager.GPS_PROVIDER);
            }
        });
        btnNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locate(LocationManager.NETWORK_PROVIDER);
            }
        });
    }

    //Metoda koja prima String i nalazi lokaciju
    private void locate(String networkProvider) {
        //registrirat cemo listener koji ce slusati
        //svakih 20 sekundi mi javi
        //svakih 5 metara mi javi

        //mozemo jos jedan boolean -> ti vec slusas

        //ti si kliknuo i ti trazis lokaciju
        locationFetched = false;
        //IMPLEMENTACIJA listenera kao argument metodi:
        locationManager.requestLocationUpdates(networkProvider, MIN_TIME, MIN_DISTANCE,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if (!locationFetched) {
                            double lat = location.getLatitude();
                            double lng = location.getLongitude();
                            String report = "LAT:" + lat + "\n" + "LNG" + lng;
                            tvReport.setText(report);
                            locationFetched = true;
                            //this je igrac
                            locationManager.removeUpdates(this);
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                }

        );
    }
}
