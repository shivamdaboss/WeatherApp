package com.example.shivamthelordalmighty.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {


    URL url;
    URLConnection urlConnection;
    InputStream is;
    InputStreamReader isr;
    BufferedReader br;
    String text;
    String info = "";
    String ZIP_CODE;
    EditText zipCodeInput;
    Button zipCodeInputButton;
    TextView[] highsTexts;
    TextView[] lowsTexts;
    TextView[] timesTexts;
    ImageView[] images;
    ImageView currentImage;
    TextView currentTimeText;
    TextView currentDateText;
    TextView currentTempText;
    TextView quoteText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zipCodeInput = findViewById(R.id.editText2);
        zipCodeInputButton = findViewById(R.id.button);
        highsTexts = new TextView[5];
        lowsTexts = new TextView[5];
        timesTexts = new TextView[5];
        images = new ImageView[5];

        highsTexts[0] = findViewById(R.id.hOne);
        highsTexts[1] = findViewById(R.id.h2);
        highsTexts[2] = findViewById(R.id.h3);
        highsTexts[3] = findViewById(R.id.h4);
        highsTexts[4] = findViewById(R.id.h5);

        lowsTexts[0] = findViewById(R.id.l1);
        lowsTexts[1] = findViewById(R.id.l2);
        lowsTexts[2] = findViewById(R.id.l3);
        lowsTexts[3] = findViewById(R.id.l4);
        lowsTexts[4] = findViewById(R.id.l5);

        timesTexts[0] = findViewById(R.id.tOne);
        timesTexts[1] = findViewById(R.id.tTwo);
        timesTexts[2] = findViewById(R.id.tThree);
        timesTexts[3] = findViewById(R.id.tFour);
        timesTexts[4] = findViewById(R.id.tFive);

        images[0] = findViewById(R.id.i1);
        images[1] = findViewById(R.id.i2);
        images[2] = findViewById(R.id.i3);
        images[3] = findViewById(R.id.i4);
        images[4] = findViewById(R.id.i5);

        currentTimeText = findViewById(R.id.currentTime);
        currentDateText = findViewById(R.id.currentDate);
        currentTempText = findViewById(R.id.currentTemp);
        quoteText = findViewById(R.id.quote);
        currentImage = findViewById(R.id.currentImage);




        zipCodeInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncThread().execute(zipCodeInput.getText() + "");
            }
        });

    }

    public class AsyncThread extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String ... params) {
            ZIP_CODE = params[0];

            try {
                info = "";
                url = new URL("https://api.openweathermap.org/data/2.5/forecast?zip=" + ZIP_CODE + "&appid=f8216c9ffe6c03cae41f05cbf44de3eb");
                urlConnection = url.openConnection();
                is = urlConnection.getInputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                while((text = br.readLine()) != null){
                    info += text;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("TAG", info + "");
            JSONObject weatherObject = null;
            try {
                weatherObject = new JSONObject(info + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {


                DecimalFormat tempFormat = new DecimalFormat("0.0");
                currentTempText.setText(tempFormat.format(KtoF(weatherObject.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp"))) + "°F");

                //times

                currentDateText.setText(new java.text.SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date (Long.valueOf(weatherObject.getJSONArray("list").getJSONObject(0).getInt("dt"))*1000)));

                currentTimeText.setText(new java.text.SimpleDateFormat("hh:mm aa").format(new java.util.Date (Long.valueOf(weatherObject.getJSONArray("list").getJSONObject(0).getInt("dt"))*1000)));

                timesTexts[0].setText(new java.text.SimpleDateFormat("hh:mm aa").format(new java.util.Date (Long.valueOf(weatherObject.getJSONArray("list").getJSONObject(0).getInt("dt"))*1000)));
                timesTexts[1].setText(new java.text.SimpleDateFormat("hh:mm aa").format(new java.util.Date (Long.valueOf(weatherObject.getJSONArray("list").getJSONObject(1).getInt("dt"))*1000)));
                timesTexts[2].setText(new java.text.SimpleDateFormat("hh:mm aa").format(new java.util.Date (Long.valueOf(weatherObject.getJSONArray("list").getJSONObject(2).getInt("dt"))*1000)));
                timesTexts[3].setText(new java.text.SimpleDateFormat("hh:mm aa").format(new java.util.Date (Long.valueOf(weatherObject.getJSONArray("list").getJSONObject(3).getInt("dt"))*1000)));
                timesTexts[4].setText(new java.text.SimpleDateFormat("hh:mm aa").format(new java.util.Date (Long.valueOf(weatherObject.getJSONArray("list").getJSONObject(4).getInt("dt"))*1000)));

                //highs
                highsTexts[0].setText("High " + tempFormat.format(KtoF(weatherObject.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp_max"))) + "°F");
                highsTexts[1].setText("High " + tempFormat.format(KtoF(weatherObject.getJSONArray("list").getJSONObject(1).getJSONObject("main").getDouble("temp_max"))) + "°F");
                highsTexts[2].setText("High " + tempFormat.format(KtoF(weatherObject.getJSONArray("list").getJSONObject(2).getJSONObject("main").getDouble("temp_max"))) + "°F");
                highsTexts[3].setText("High " + tempFormat.format(KtoF(weatherObject.getJSONArray("list").getJSONObject(3).getJSONObject("main").getDouble("temp_max"))) + "°F");
                highsTexts[4].setText("High " + tempFormat.format(KtoF(weatherObject.getJSONArray("list").getJSONObject(4).getJSONObject("main").getDouble("temp_max"))) + "°F");


                //lows
                lowsTexts[0].setText("Low " + tempFormat.format(KtoF(weatherObject.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp_min"))) + "°F");
                lowsTexts[1].setText("Low " + tempFormat.format(KtoF(weatherObject.getJSONArray("list").getJSONObject(1).getJSONObject("main").getDouble("temp_min"))) + "°F");
                lowsTexts[2].setText("Low " + tempFormat.format(KtoF(weatherObject.getJSONArray("list").getJSONObject(2).getJSONObject("main").getDouble("temp_min"))) + "°F");
                lowsTexts[3].setText("Low " + tempFormat.format(KtoF(weatherObject.getJSONArray("list").getJSONObject(3).getJSONObject("main").getDouble("temp_min"))) + "°F");
                lowsTexts[4].setText("Low " + tempFormat.format(KtoF(weatherObject.getJSONArray("list").getJSONObject(4).getJSONObject("main").getDouble("temp_min"))) + "°F");

                //images
                processWeather(weatherObject.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description"), currentImage, true);
                processWeather(weatherObject.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description"), images[0], false);
                processWeather(weatherObject.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("description"), images[1], false);
                processWeather(weatherObject.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("description"), images[2], false);
                processWeather(weatherObject.getJSONArray("list").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("description"), images[3], false);
                processWeather(weatherObject.getJSONArray("list").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("description"), images[4], false);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public double KtoF(double k){
        return (k - 273.15) * (9/5) + 32;
    }


    public void processWeather(String weather, ImageView image, boolean current) {


        if (weather.contains("sun") || weather.contains("clear")) {
            image.setImageResource(R.drawable.sun);
            if(current) {
                int x = ((int) (Math.random() * 2) + 1);
                if (x == 1) {
                    quoteText.setText("\"I find your lack of clouds disturbing.\"");
                } else {
                    quoteText.setText("\"You can’t stop the change, any more than you can stop the suns from setting\"");
                }
            }

        }

        else if (weather.contains("thunder")) {
            image.setImageResource(R.drawable.thunder);

            if(current){
                int x = ((int)(Math.random() * 2) + 1);
                if(x == 1){
                    quoteText.setText("\"I’m one with the thunder. The thunder is with me.\"");
                }
                else {
                    quoteText.setText("\"So this is how liberty dies. With thunderous applause.\"");
                }
            }
        }

        else if (weather.contains("snow")) {
            image.setImageResource(R.drawable.snow);
            if(current){
                int x = ((int)(Math.random() * 2) + 1);
                if(x == 1){
                    quoteText.setText("\"Oh, my dear snow. How I’ve missed you.\"");
                }
                else {
                    quoteText.setText("\"There is always a bigger snow storm\"");
                }
            }
        }

        else if (weather.contains("rain")) {
            image.setImageResource(R.drawable.rain);
            if(current){
                int x = ((int)(Math.random() * 2) + 1);
                if(x == 1){
                    quoteText.setText("\"Always in motion is the rain\"");
                }
                else {
                    quoteText.setText("\"Return of the Rain\"");
                }
            }

        }

        else if (weather.contains("partly cloudy") || weather.contains("partly")){
            image.setImageResource(R.drawable.clouds);

            if(current){
                quoteText.setText("The clouds’ll do");
            }
        }

        else if (weather.contains("cloud")){
            image.setImageResource(R.drawable.real_cloudy);
            if(current){
                quoteText.setText("Clouds! Unlimited clouds!");
            }
        }
        else{
            image.setImageResource(R.drawable.real_cloudy);
            if(current){
                quoteText.setText("Clouds! Unlimited clouds!");
            }
        }

    }
}
