package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApiService {
    private static final String API_KEY = "YOUR_OPENWEATHERMAP_API_KEY";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather";

    public String getWeather(String city) throws IOException {
        String apiUrl = String.format("%s?q=%s&appid=%s", API_URL, city, API_KEY);

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return response.toString();
        } else {
            // Handle error response
            System.out.println("Error: " + responseCode);
            return null;
        }
    }
}
