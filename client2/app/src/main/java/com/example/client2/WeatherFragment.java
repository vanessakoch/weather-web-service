package com.example.client2;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherFragment extends Fragment {
    static TextView txtDateHour;
    static TextView txtTemperature;
    static TextView txtHumidity;
    static TextView txtLuminosity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        txtDateHour = (TextView) view.findViewById(R.id.txtDateHour);
        txtTemperature = (TextView) view.findViewById(R.id.txtTemperature);
        txtHumidity = (TextView) view.findViewById(R.id.txtHumidity);
        txtLuminosity = (TextView) view.findViewById(R.id.txtLuminosity);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    get();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return view;
    }

    public static void get() {
        String baseUrl = "http://10.0.2.2:3000/last";

        try {
            URL url = new URL(baseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Falha : HTTP código : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder resultado = new StringBuilder();

            String output;
            while ((output = br.readLine()) != null) {
                resultado.append(output).append('\n');
            }

            Gson gson = new Gson();
            Weather weather = gson.fromJson(String.valueOf(resultado), Weather.class);

            txtDateHour.setText(weather.getCreatedAt().getDate() + " " + weather.getCreatedAt().getHour());
            txtTemperature.setText(weather.getTemperatura() + " °C");
            txtHumidity.setText(weather.getUmidade() + " %");
            txtLuminosity.setText(weather.getLuminosidade()+ "");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}