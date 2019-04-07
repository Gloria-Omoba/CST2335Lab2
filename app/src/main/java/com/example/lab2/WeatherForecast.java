package com.example.lab2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    private ImageView icon;
    private TextView temp, minTemp, maxTemp, uvRating;
    private ProgressBar progBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        ForecastQuery networkThread = new ForecastQuery();
        networkThread.execute("http://api.openweathermap.org/data/2.5/weather?q=hanoi,vn&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");

        icon = (ImageView) findViewById(R.id.weather);
        temp = (TextView) findViewById(R.id.currentTemp);
        minTemp = (TextView) findViewById(R.id.minTemp);
        maxTemp = (TextView) findViewById(R.id.maxTemp);
        uvRating = (TextView) findViewById(R.id.uvRating);
        progBar = (ProgressBar) findViewById(R.id.progressBar);
        progBar.setVisibility(View.VISIBLE);
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        private String uv, min, max, currentTemp, weatherIcon;
        private String iconURL = "http://openweathermap.org/img/w/";
        Bitmap image;

        @Override
        protected String doInBackground(String... args) {
            try {

                //get the url String
                String myUrl = args[0];

                //given a url string,create a network connection and get an input stream
                URL url = new URL(myUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(1000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                InputStream in = connection.getInputStream();

                //create/instantiate the parser using the generated input stream
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(in, "UTF-8"); //inStream comes from line 46

                //loop over the XML
                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() == XmlPullParser.START_TAG) {

                        String name =  parser.getName();
                        if (name.equals("temperature")) {
                            currentTemp = parser.getAttributeValue(null, "value");
                            Log.d("ForeCast", "Current Temperature: " + currentTemp);
                            publishProgress(25);

                            Thread.sleep(500);
                            min = parser.getAttributeValue(null, "min");
                            Log.d("ForeCast", "Minimum Temperature: " + currentTemp);
                            publishProgress(50);

                            Thread.sleep(500);
                            max = parser.getAttributeValue(null, "max");
                            Log.d("ForeCastQuery", "Maximum Temperature: " + currentTemp);
                            publishProgress(75);
                        } else if (name.equals("weather")) {
                            weatherIcon = parser.getAttributeValue(null, "icon");
                            String iconName = weatherIcon + ".png";

                            if (fileExistance(iconName)) {
                                FileInputStream inStream = null;
                                try {
                                    inStream = new FileInputStream(getBaseContext().getFileStreamPath(iconName));
                                    Log.d("ForeCast", "Image found \"" + iconName + "\" in local");

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                image = BitmapFactory.decodeStream(inStream);

                            }else {
                                Log.e("ForeCast", "icon name \""+iconName+"\" does not exist in local directory");

                                Log.e("ForeCast", "Downloading image from the internet");

                                //get image
                                image  = HTTPUtils.getImage(iconURL+iconName);
                                Log.e("ForeCast", "Saving image ");
                                FileOutputStream outputStream = null;
                                try {
                                    outputStream = openFileOutput(iconName, Context.MODE_PRIVATE);
                                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                } catch (Exception e) {
                                    Log.e("AsyncTask", "Download failed");
                                }

                            }

                            publishProgress(100);
                        }


                    }
                    parser.next();
                }

                // JSON rreading of UV factor
                URL UVRating = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
                HttpURLConnection UV = (HttpURLConnection) UVRating.openConnection();
                in = UV.getInputStream();

                //JSON object
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString();


                JSONObject jObject = new JSONObject(result);
                uv = String.valueOf(jObject.getDouble("value"));
                Log.e("ForeCast", "Found UV: " +uv);


            } catch (Exception e) {
               Log.e("ForeCast", e.getMessage());
            }

            return "Task Complete";

        }


        protected boolean fileExistance(String fName) {
            File file = getBaseContext().getFileStreamPath(fName);
            return file.exists();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progBar.setVisibility(View.VISIBLE);
            progBar.setProgress(values[0]);
//
        }

        @Override
        protected void onPostExecute(String s) {
            temp.setText("Current temperature: " + currentTemp + "°C");
            minTemp.setText("Min temperature: " + min + "°C");
            maxTemp.setText("Max temperature: " + max + "°C");
            uvRating.setText("UV Rating: " + uv);
            icon.setImageBitmap(image);
            progBar.setVisibility(View.INVISIBLE);
        }
    }

    private static class HTTPUtils {
        public static Bitmap getImage(URL url) {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());

                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        public  static Bitmap getImage(String urlString) {

            try {
                URL url = new URL(urlString);

                return getImage(url);

            } catch (MalformedURLException e) {
                return null;
            }
        }

    }
}


