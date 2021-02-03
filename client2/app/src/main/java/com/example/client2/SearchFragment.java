package com.example.client2;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.ikovac.timepickerwithseconds.MyTimePickerDialog;
import com.ikovac.timepickerwithseconds.TimePicker;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SearchFragment extends Fragment {
    Button btnDatePicker;
    Button btnTimePicker;
    Button btnSearch;
    TextView txtDate;
    TextView txtTime;

    Calendar calendar = Calendar.getInstance();
    int hour, minute, second;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        btnDatePicker = (Button) view.findViewById(R.id.btnDatePicker);
        btnTimePicker = (Button) view.findViewById(R.id.btnTimePicker);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        txtTime = (TextView) view.findViewById(R.id.txtTime);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
                second = calendar.get(Calendar.SECOND);

                MyTimePickerDialog time = new MyTimePickerDialog(getContext(), new MyTimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds) {
                        updateTime(hourOfDay, minute, seconds);
                    }
            }, hour, minute, second, true);
            time.show();
        }});


        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        searchByDateHour();
                    }
                }).start();
            }
        });

        return view;
    }

    private void updateDate() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtDate.setText(sdf.format(calendar.getTime()));
    }

    private void updateTime(int hour, int minute, int second) {
        String formated[] = {String.valueOf(hour), String.valueOf(minute), String.valueOf(second)};

        for(int i = 0; i < formated.length; i++) {
            if(formated[i].length() == 1) {
                formated[i] = (0 + formated[i]);
            }
        }

        txtTime.setText(formated[0] + ":" + formated[1] + ":" + formated[2]);
    }

    public void searchByDateHour() {
        if(!txtTime.getText().equals("") || !txtDate.getText().equals("")) {
            String baseUrl = "http://10.0.2.2:3000/search?date=" + txtDate.getText() + "&hour=" + txtTime.getText();

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

                if(weather.getCreatedAt() == null) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(),
                                    "Este registro não existe", Toast.LENGTH_LONG).show();                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            onClickShow(weather);
                        }
                    });
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickShow(Weather weather) {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.show_dialog, null);
        final ImageView imgClima = (ImageView) view.findViewById(R.id.imgClima);
        final TextView txtTemperatura = (TextView) view.findViewById(R.id.txtTemperatura);
        final TextView txtUmidade = (TextView) view.findViewById(R.id.txtUmidade);
        final TextView txtLuminosidade = (TextView) view.findViewById(R.id.txtLuminosidade);
        final Button btnOk = (Button) view.findViewById(R.id.btnOk);

        if(weather.getTemperatura() > 13) {
            imgClima.setImageResource(R.drawable.sun);
        } else {
            imgClima.setImageResource(R.drawable.cold);
        }

        txtTemperatura.setText("Temperatura: " + weather.getTemperatura() + " °C");
        txtUmidade.setText("Umidade: " + weather.getUmidade() + " %");
        txtLuminosidade.setText("Luminosidade: " + weather.getLuminosidade());

        mBuilder.setView(view);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }

}
