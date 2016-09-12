package com.pavan.pavansweatherapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pavan on 7/22/2016.
 */
public class DownloadTasks extends AsyncTask<String, Void, String> {

    private String result = "";
    URL url;
    HttpURLConnection httpURLConnection = null;

    @Override
    protected String doInBackground(String... urls) {

        try {
            url = new URL(urls[0]);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            int data = reader.read();
            while (data != -1){
                char current = (char)data;
                result += current;
                data = reader.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject weatherInfo = new JSONObject(jsonObject.getString("main"));
//            JSONObject forecast = new JSONObject(jsonObject.getString("weather"));
//            String description = forecast.getString("description");
//            JSONArray jsonArray = new JSONArray("weather");

//            JSONObject weatherData = new JSONObject(jsonObject.getString("weather"));
//            String weather = String.valueOf(jsonArray.get(2));
            Double temperature = Double.parseDouble(weatherInfo.getString("temp"));
            int temperatureInteger = (int) (temperature - 273.15);
            String placeName = jsonObject.getString("name");
            MainActivity.place.setText(placeName);
            MainActivity.temp.setText(" "+String.valueOf(temperatureInteger)+" degree celcius");
//            MainActivity.fc.setText(description);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
